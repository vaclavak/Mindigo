package vpp.vac.mindigo.compiler;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.util.jar.*;
import javax.tools.*;

import vpp.vac.mindigo.util.Colors;

public class Compiler {

	public static void build(String mainClass) throws Exception {
		Path currentDir = Paths.get("").toAbsolutePath();
		Path srcDir = currentDir.resolve("src");
		Path buildDir = currentDir.resolve("build");

		if (!Files.exists(srcDir)) {
			System.err.println(Colors.RED + "No 'src' directory found in: " + currentDir);
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
			System.err.println(Colors.RED + "No Java source files found.");
			return;
		}

		System.out.println(Colors.BLUE + "Compiling source files...");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null) {
			System.err.println(Colors.RED +  "No system Java compiler found. (Are you running a JRE instead of a JDK?)");
			return;
		}

		List<String> options = Arrays.asList("-d", buildDir.toString());
		int result = compiler.run(null, null, null,
				Stream.concat(options.stream(), javaFiles.stream().map(File::getPath)).toArray(String[]::new));

		if (result != 0) {
			System.err.println(Colors.RED + "Compilation failed.");
			return;
		}

		File file = new File("mindigo.json");
		MindigoConfig config = new MindigoConfig(file.toPath());
		Path jarFile = currentDir.resolve(config.getProjectName() + " " + config.getVersion());
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
		}

		System.out.println("[" + Colors.GREEN + "BUILD SUCCESSFUL" + Colors.RESET + "]");
		System.out.println(Colors.BLUE + "Runnable JAR created: " + jarFile);
	}
}
