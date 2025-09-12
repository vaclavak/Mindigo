package vpp.vac.mindigo.argument.impl;

import vpp.vac.mindigo.argument.Argument;
import vpp.vac.mindigo.argument.ArgumentManager;

public class Help extends Argument {

	public Help() {
		this.name = "help";
		this.Description = "Displays a list of all commands";
	}
	
	
	public void onCall() {
		for(Argument a : ArgumentManager.Arguments) {
			System.out.println(a.getName() + " | " + a.getDescription());
		}
	}

}
