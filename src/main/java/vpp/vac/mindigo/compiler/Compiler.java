package vpp.vac.mindigo.compiler;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.util.jar.*;
import javax.tools.*;

public class Compiler {

	private static File file = new File("mindigo.json");

	public static void build(String mainClass) throws Exception {
		MindigoConfig config = new MindigoConfig(file.toPath());
		Path currentDir = Paths.get("").toAbsolutePath();
		Path srcDir = currentDir.resolve(config.getSrcFolder());
		Path buildDir = currentDir.resolve("build");
		Path libsDir = currentDir.resolve(config.getLibsFolder());

		if (!Files.exists(srcDir)) {
			System.err.println("No 'src' directory found in: " + currentDir);
			return;
		}

		if (!Files.exists(buildDir)) {
			Files.createDirectory(buildDir);
		}

		List<File> javaFiles;
		try (Stream<Path> walk = Files.walk(srcDir)) {
			javaFiles = walk.filter(p -> p.toString().endsWith(".java")).map(Path::toFile).collect(Collectors.toList());
		}

		if (javaFiles.isEmpty()) {
			System.err.println("No Java source files found.");
			return;
		}

		List<String> jarDeps = new ArrayList<>();
		if (Files.exists(libsDir)) {
			try (Stream<Path> walk = Files.walk(libsDir)) {
				jarDeps = walk.filter(p -> p.toString().endsWith(".jar")).map(Path::toString)
						.collect(Collectors.toList());
			}
		}

		System.out.println("Compiling source files...");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null) {
			System.err.println("No system Java compiler found. (Are you running a JRE instead of a JDK?)");
			return;
		}

		List<String> options = new ArrayList<>();
		options.add("-d");
		options.add(buildDir.toString());

		if (!jarDeps.isEmpty()) {
			options.add("-cp");
			options.add(String.join(File.pathSeparator, jarDeps));
		}

		int result = compiler.run(null, null, null,
				Stream.concat(options.stream(), javaFiles.stream().map(File::getPath)).toArray(String[]::new));

		if (result != 0) {
			System.err.println("Compilation failed.");
			return;
		}

		Path jarFile = currentDir.resolve(config.getProjectName() + " " + config.getVersion() + ".jar");

		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, mainClass);

		try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile.toFile()), manifest)) {
			Files.walk(buildDir).filter(Files::isRegularFile).forEach(path -> {
				String entryName = buildDir.relativize(path).toString().replace("\\", "/");
				try (InputStream is = Files.newInputStream(path)) {
					JarEntry entry = new JarEntry(entryName);
					jos.putNextEntry(entry);
					byte[] buffer = new byte[1024];
					int bytesRead;
					while ((bytesRead = is.read(buffer)) != -1) {
						jos.write(buffer, 0, bytesRead);
					}
					jos.closeEntry();
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			});

			for (String depJar : jarDeps) {
				try (JarInputStream jis = new JarInputStream(new FileInputStream(depJar))) {
					JarEntry entry;
					while ((entry = jis.getNextJarEntry()) != null) {
						if (entry.isDirectory() || entry.getName().startsWith("META-INF/"))
							continue;
						jos.putNextEntry(new JarEntry(entry.getName()));
						byte[] buffer = new byte[1024];
						int bytesRead;
						while ((bytesRead = jis.read(buffer)) != -1) {
							jos.write(buffer, 0, bytesRead);
						}
						jos.closeEntry();
					}
				}
			}
		}

		System.out.println("[BUILD SUCCESSFUL]");
		System.out.println("Runnable JAR created: " + jarFile);
	}
}
