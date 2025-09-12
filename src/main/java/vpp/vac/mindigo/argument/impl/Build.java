package vpp.vac.mindigo.argument.impl;

import java.io.File;

import vpp.vac.mindigo.argument.Argument;
import vpp.vac.mindigo.compiler.MindigoConfig;
import vpp.vac.mindigo.main.Main;

public class Build extends Argument {

	public Build() {
		this.name = "build";
		this.Description = "Builds the project";
	}

	public void onCall() {
		try {
			File file = new File("mindigo.json");
			MindigoConfig config = new MindigoConfig(file.toPath());
			vpp.vac.mindigo.compiler.Compiler.build(config.getMainClass());
		} catch (Exception e) {
			System.err.print(e);
		}

	}

}
