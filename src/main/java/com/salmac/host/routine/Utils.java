package com.salmac.host.routine;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Utils {

	public static HashMap<String, LocalDateTime> serverLastHeartbeatMap = new HashMap<>();

	public static void updateServerStatusScheduler(){

	}

	public static void replaceAll(StringBuilder builder, String from, String to) {
	    int index = builder.indexOf(from);
	    while (index != -1) {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
	}

	public static boolean isEmptyString(String string) {
		return string == null || string.isEmpty();
	}
}
