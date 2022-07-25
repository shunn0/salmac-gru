package com.salmac.host.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.salmac.host.routine.OSNAME;

@Component
public class ProcessBuilderExecutor {
	
    public List<String> run(String cmd) {
    	if(OSNAME.isUnix()) {
    		return runForUnix(cmd);
    	} else if(OSNAME.isWindows()) {
    		return runForWindows(cmd);
    	} else if(OSNAME.isMac()) {
    		return runForMac(cmd);
    	} else if(OSNAME.isSolaris()) {
    		return runForSolaris(cmd);
    	} else {
    		List<String> responseList = new ArrayList<String>();
    		responseList.add("Undefined OS detected");
    		return responseList;
    	}        
    }
    
    public List<String> runMultipleCmd(String cmds) {
    	return run(cmds);
    }

    public List<String> runScript(String fileName){
    	return run(fileName);
    }
    
    private List<String> runForUnix(String cmd){
    	ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File("/home/shunn0"));
    	processBuilder.command("bash", "-c", cmd);
        
        List<String> responseList = new ArrayList<String>();

        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
            	responseList.add(line);
            }

            int exitCode = process.waitFor();
            responseList.add("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
            responseList.add("500:Internal server error : " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            responseList.add("500:Internal server error : " + e.getMessage());
        }
        return responseList;
    }

    private List<String> runForWindows(String cmd){
    	ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", cmd);
        
        List<String> responseList = new ArrayList<String>();

        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
            	responseList.add(line);
            }

            int exitCode = process.waitFor();
            responseList.add("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
            responseList.add("500:Internal server error : " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            responseList.add("500:Internal server error : " + e.getMessage());
        }
        return responseList;
    }
    
    private List<String> runForMac(String cmd){
    	ProcessBuilder processBuilder = new ProcessBuilder();
    	processBuilder.command("bash", "-c", cmd);
        
        List<String> responseList = new ArrayList<String>();

        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
            	responseList.add(line);
            }

            int exitCode = process.waitFor();
            responseList.add("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
            responseList.add("500:Internal server error : " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            responseList.add("500:Internal server error : " + e.getMessage());
        }
        return responseList;
    }
    
    private List<String> runForSolaris(String cmd){
    	ProcessBuilder processBuilder = new ProcessBuilder();
    	processBuilder.command("bash", "-c", cmd);
        
        List<String> responseList = new ArrayList<String>();

        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
            	responseList.add(line);
            }

            int exitCode = process.waitFor();
            responseList.add("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
            responseList.add("500:Internal server error : " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            responseList.add("500:Internal server error : " + e.getMessage());
        }
        return responseList;
    }


    /***
     * react is appending = at the end of the command. remove that =
     * Please check @str is not null before call
     * @param str
     * @return
     */
    public String prepareCMD(String str) {
        if(str.endsWith("=")){
            str = str.substring(0, str.length() - 1);
        }
        String s = str.trim()
                .replaceAll("___", " ")
                .replaceAll("%0A", " && ")
                .replaceAll("\\+"," ");
//        if(OSNAME.isWindows()){
//            s = s.replaceAll("%0A", " && ");
//        } else {
//            s = s.replaceAll("%0A", " | ")
//        }
        return s;
    }
}