package com.salmac.host.routine;

public class OSNAME {
	public static String OS = System.getProperty("os.name").toLowerCase();
	public static String OS_NAME = "";
	public static String OS_WINDOWS = "WINDOWS";
	public static String OS_UNIX = "UNIX";
	public static String OS_MAC = "MACOS";
	public static String OS_SOLARIS = "SOLARIS";
	
	
	public static void setOSName(){
    	if (isWindows()) {
            OS_NAME = OS_WINDOWS;
        } else if (isMac()) {
        	OS_NAME = OS_MAC;
        } else if (isUnix()) {
        	OS_NAME = OS_UNIX;
        } else if (isSolaris()) {
        	OS_NAME = OS_SOLARIS;
        } else {
            OS_NAME = "UNDEFINED";
        }
    }
	
	public static boolean isWindows() {
        return OS.contains("win");
    }
 
    public static boolean isMac() {
        return OS.contains("mac");
    }
 
    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }
 
    public static boolean isSolaris() {
        return OS.contains("sunos");
    }
    
}
