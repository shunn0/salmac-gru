package com.salmac.host.controller;

import com.salmac.host.entity.ScriptEntity;
import com.salmac.host.entity.ServerEntity;
import com.salmac.host.routine.exception.ResourceNotFoundException;
import com.salmac.host.service.ScriptService;
import com.salmac.host.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/server")
@CrossOrigin(origins = "http://localhost:3000")
public class ServerController {

    @Autowired
    ServerService serverService;

    @Autowired
    ScriptService scriptService;

    //@PostMapping("/heartbeat")
    @RequestMapping(value = "/heartbeat", method = {RequestMethod.POST})
    public ResponseEntity heartbeat(String targetEngineAddress, String targetEngineName, String targetEngineOS) {
        System.out.println("Received request from " + targetEngineAddress);
        serverService.heartBeat(targetEngineAddress, targetEngineName, targetEngineOS);
        ResponseEntity re = ResponseEntity.ok().build();
        System.out.println(re.toString());
        return re;
    }

    @GetMapping("/all")
    public ResponseEntity getServerList() {
        List<ServerEntity> serverEntities = serverService.getListOfServer();
        if (!serverEntities.isEmpty()) {
            return ResponseEntity.ok().body(serverEntities);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/script}")
    public ResponseEntity getScriptList(Integer pageNumber, Integer pageSize){
        try{
            Page<ScriptEntity> courseEntities = scriptService.getListOfScripts(pageNumber,pageSize);
            if(courseEntities.isEmpty()){
                return ResponseEntity.ok().body(courseEntities);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/script/{scriptId}")
    public ResponseEntity getScript(@PathVariable("scriptId") Long scriptId) {
        Optional<ScriptEntity> scriptOptional = scriptService.getScriptById(scriptId);
        if (scriptOptional.isPresent()) {
            System.out.println("present");
            return ResponseEntity.ok().body(scriptOptional.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/script")
    public void addScript(ScriptEntity scriptEntity) {
        scriptService.addScript(scriptEntity);
    }

    @PutMapping("/script/{scriptId}")
    public ResponseEntity updateScript(@PathVariable("scriptId") Long scriptId, ScriptEntity scriptEntity) {
        try {
            scriptService.updateScript(scriptId, scriptEntity);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/script/{scriptId}")
    public ResponseEntity deleteScript(@PathVariable("scriptId") Long scriptId) {
        try {
            scriptService.removeScript(scriptId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
