package vpp.vac.mindigo.argument.impl;

import vpp.vac.mindigo.argument.Argument;
import vpp.vac.mindigo.argument.ArgumentManager;
import vpp.vac.mindigo.main.Main;

public class Version extends Argument {

	public Version() {
		this.name = "version";
		this.Description = "Displays the current version";
	}
	
	
	public void onCall() {
		System.out.println("Mindigo version [v" + Main.VERSION + "] \n Made by vaclavak");
		System.out.println("github.com/vaclavak \n github.com/vaclavak/Mindigo");
	}

}
