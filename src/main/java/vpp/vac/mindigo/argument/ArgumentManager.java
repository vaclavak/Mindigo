package vpp.vac.mindigo.argument;

import java.util.ArrayList;

import vpp.vac.mindigo.argument.impl.Build;
import vpp.vac.mindigo.argument.impl.Help;
import vpp.vac.mindigo.argument.impl.MindigoInit;

public class ArgumentManager {
	
	public static ArrayList<Argument> Arguments = new ArrayList<Argument>();
	
	static {
		Arguments.add(new Help());
		Arguments.add(new Build());
		Arguments.add(new MindigoInit());
	}

}
