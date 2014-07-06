package fr.vincentteam.imageviewer;

public class Utils {
	private Utils() { }
	
	private enum Os {
		WINDOWS("windows"),
		SOLARIS("solaris"),
		MACOSX("maxosx"),
		LINUX("linux"),
		;
		
		private String dname;
		
		private Os(String dname) {
			this.dname = dname;
		}
		
		public String getDName() {
			return dname;
		}
	}
	
	private static Os os;
	
	public static String osAsString() {
		if (os == null) {
			String x = System.getProperty("os.name").toLowerCase();
			if (x.indexOf("win") >= 0)
				os = Os.WINDOWS;
			else if (x.indexOf("nix") >= 0 || x.indexOf("nux") >= 0 || x.indexOf("aix") >= 0)
				os = Os.LINUX;
			else if (x.indexOf("mac") >= 0)
				os = Os.MACOSX;
			else if (x.indexOf("sunos") >= 0)
				os = Os.SOLARIS;
			else
				throw new RuntimeException("Unknown operating system '" + x + "'");
		}
		return os.getDName();
	}

}
