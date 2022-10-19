package com.salmac.host.controller;

import java.util.ArrayList;
import java.util.List;

import com.salmac.host.consumer.RestCaller;
import com.salmac.host.engine.ProcessBuilderExecutor;
import com.salmac.host.routine.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.salmac.host.files.FileStorageService;

@CrossOrigin(origins = "http://localhost:3000")
//@RestController
public class Controller {
	
	@Autowired
	ProcessBuilderExecutor executor;

	@Autowired
    private FileStorageService fileStorageService;

	@Autowired
	RestCaller restCaller;
	
	//@CrossOrigin(origins = "http://10.0.2.15:3000")
	@GetMapping("/runcmd")
	public String runcmdGet() {
		return "Hello";
	}

	//@CrossOrigin(origins = "http://10.0.2.15:3000")
	@PostMapping("/runcmd")
	public List<String> runcmd(@RequestBody String cmd) {
		if (!Utils.isEmptyString(cmd)) {
			cmd = executor.prepareCMD(cmd).replaceAll("\\+"," ");

			//System.out.println(cmds.trim());
			return executor.runMultipleCmd(cmd);
		} else {
			return new ArrayList<>();
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/runmulticmd")
	public List<String> runmulticmd(@RequestBody String cmds) {
		System.out.println("Request recieved");
		if (!Utils.isEmptyString(cmds)) {
			cmds = executor.prepareCMD(cmds).replaceAll("\\+"," ");
			//System.out.println(cmds.trim());
			return executor.runMultipleCmd(cmds);
		} else {
			return new ArrayList<>();
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/runmulticmd/v2")
	public ResponseEntity runmulticmd2( String cmds, String serverIp) {
		System.out.println("Request recieved");
		if (!Utils.isEmptyString(cmds)) {
			cmds = executor.prepareCMD(cmds);
			//System.out.println(cmds.trim());
			if(serverIp.isEmpty()) {
				List<String> res = executor.runMultipleCmd(cmds);
				return ResponseEntity.ok().body(res);
			} else{
				return restCaller.postMultiCMDAtTargetEngine(serverIp,cmds);
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/runscript")
    public List<String> uploadFile(@RequestParam MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        return executor.runScript(fileName);
    }

}
