package vpp.vac.mindigo.util.logger;

public class Logger {
	
	private String prefix;
	
	public Logger(String prefix) {
		this.prefix = prefix;
	}
	
	public void print(String x) {
		System.out.println(prefix + " " + x);
	}
	
	public void print(int x) {
		System.out.println(prefix + " " + x);
	}
	
	public void print(double x) {
		System.out.println(prefix + " " + x);
	}
	
	public void print(long x) {
		System.out.println(prefix + " " + x);
	}
	
	public void print(char x) {
		System.out.println(prefix + " " + x);
	}
	
	public void print(boolean x) {
		System.out.println(prefix + " " + x);
	}
	
	public void print(float x) {
		System.out.println(prefix + " " + x);
	}

}
