package vpp.vac.mindigo.compiler;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;

import vpp.vac.mindigo.main.Main;
import vpp.vac.mindigo.util.Colors;

public class MindigoConfig {

	private JsonObject jsonObject;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public MindigoConfig(Path configPath) throws IOException {
		File file = configPath.toFile();

		if (!file.exists()) {
			Main.log.print(Colors.RED + "Config file not found");
			Main.log.print("Attempt \"mindigo init\" or create a new mindigo.json file");
		}

		try (Reader reader = Files.newBufferedReader(configPath)) {
			JsonElement element = JsonParser.parseReader(reader);
			if (!element.isJsonObject()) {
				throw new IllegalArgumentException(Colors.RED + "Config file must contain a JSON object.");
			}
			jsonObject = element.getAsJsonObject();
		}
	}

	public String getProjectName() {
		return jsonObject.has("projectName") ? jsonObject.get("projectName").getAsString() : "UnknownProject";
	}

	public String getDescription() {
		return jsonObject.has("description") ? jsonObject.get("description").getAsString() : "";
	}

	public String getMainClass() {
		return jsonObject.has("mainClass") ? jsonObject.get("mainClass").getAsString() : "Main";
	}

	public String getVersion() {
		return jsonObject.has("version") ? jsonObject.get("version").getAsString() : "0.0.1";
	}

	public String get(String key) {
		return jsonObject.has(key) ? jsonObject.get(key).getAsString() : null;
	}
}
