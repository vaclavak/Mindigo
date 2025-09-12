package vpp.vac.mindigo.argument.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import vpp.vac.mindigo.argument.Argument;
import vpp.vac.mindigo.compiler.MindigoConfig;

public class MindigoInit extends Argument {

	public MindigoInit() {
		this.name = "init";
		this.Description = "Initializes a new mindigo project, and generates a mindigo.json file";
	}

	public void onCall() {
		try {
			File file = new File("mindigo.json");

			if (!file.exists()) {

				JsonObject defaultJson = new JsonObject();
				defaultJson.addProperty("projectName", "Default project");
				defaultJson.addProperty("description", "Default description");
				defaultJson.addProperty("mainClass", "com.example.Main");
				defaultJson.addProperty("version", "0.0.1");

				try (FileWriter writer = new FileWriter(file)) {
					new GsonBuilder().setPrettyPrinting().create().toJson(defaultJson, writer);
					System.out.println("mindigo.json created with default values");
				}
			}

			MindigoConfig config = new MindigoConfig(file.toPath());
			System.out.println("Config loaded. Project: " + config.getProjectName());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
