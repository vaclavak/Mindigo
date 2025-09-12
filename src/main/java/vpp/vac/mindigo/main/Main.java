package vpp.vac.mindigo.main;

import vpp.vac.mindigo.argument.Argument;
import vpp.vac.mindigo.argument.ArgumentManager;
import vpp.vac.mindigo.util.Colors;
import vpp.vac.mindigo.util.logger.Logger;

public class Main {

	public static final String PREFIX = "[Mindigo]";
	public static final String VERSION = "1.0.0";
	public static String[] argz;
	public static Logger log;

	public static void main(String[] args) {
		argz = args;
		log = new Logger(PREFIX);
		if (args.length > 0) {
			for (Argument a : ArgumentManager.Arguments) {
				if (args[0].equalsIgnoreCase(a.getName())) {
					a.onCall();
				}
			}
		} else {
			log.print(Colors.RED + "No arguments provided, use \"help\" for a list of all arguments");
		}

	}

}
