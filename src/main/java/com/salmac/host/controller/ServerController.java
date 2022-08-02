package com.salmac.host.controller;

import com.salmac.host.dto.ScriptDTO;
import com.salmac.host.dto.ScriptDetailsDTO;
import com.salmac.host.entity.ScriptEntity;
import com.salmac.host.entity.ServerEntity;
import com.salmac.host.files.FileStorageService;
import com.salmac.host.routine.exception.ResourceNotFoundException;
import com.salmac.host.service.ScriptService;
import com.salmac.host.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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

    @Autowired
    private FileStorageService fileStorageService;

    //@PostMapping("/heartbeat")
    @RequestMapping(value = "/heartbeat", method = {RequestMethod.POST})
    public ResponseEntity heartbeat(String agentIp, String agentPort, String agentName, String agentOS) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String ip = request.getRemoteAddr();
        System.out.println("Received request from " + ip);
        if(!ip.isEmpty()){
            serverService.heartBeat(ip+":"+agentPort, agentName, agentOS);
        } else {
            serverService.heartBeat(agentIp+":"+agentPort, agentName, agentOS);
        }
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

    @GetMapping("/script")
    public ResponseEntity getScriptList(Integer pageNumber, Integer pageSize){
        try{
            //Page<ScriptEntity> courseEntities = scriptService.getListOfScripts(pageNumber,pageSize);
            List<ScriptDTO> scriptEntities = scriptService.getListOfScripts(pageNumber,pageSize);
            if(!scriptEntities.isEmpty()){
                return ResponseEntity.ok().body(scriptEntities);
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
            return ResponseEntity.ok().body(new ScriptDetailsDTO(scriptOptional.get()) );
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/script/byos/{os}")
    public ResponseEntity getScriptByOS(@PathVariable("os") String OS) {
        List<ScriptDTO> scriptEntities = scriptService.getListOfScriptsByOS(OS);
        if(!scriptEntities.isEmpty()){
            return ResponseEntity.ok().body(scriptEntities);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/script")
    public void addScript(ScriptDetailsDTO scriptDetailsDTO) {
        //System.out.println("Inside addScript");
        scriptService.addScript(scriptDetailsDTO.toScriptEntity());
        //scriptService.addScript(scriptEntity);
    }

    @PostMapping("/script/file")
    public void addScriptWithFile(ScriptDetailsDTO scriptDetailsDTO, @RequestParam MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        ScriptEntity scriptEntity = scriptDetailsDTO.toScriptEntity();
        scriptEntity.setPath(fileName);
        scriptEntity.setLastUpdateTime(LocalDateTime.now());
        scriptService.addScript(scriptEntity);
    }

    @PutMapping("/script/{scriptId}")
    public ResponseEntity updateScript(@PathVariable("scriptId") Long scriptId, ScriptDetailsDTO scriptDetailsDTO) {
        try {
            ScriptEntity scriptEntity = scriptDetailsDTO.toScriptEntity();
            scriptService.updateScript(scriptId, scriptEntity);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/script/file/{scriptId}")
    public ResponseEntity updateScriptFiles(@PathVariable("scriptId") Long scriptId, @RequestParam MultipartFile file){
        try {
            Optional<ScriptEntity> scriptOptional = scriptService.getScriptById(scriptId);
            if (scriptOptional.isPresent()) {
                String fileName = fileStorageService.storeFile(file);
                ScriptEntity scriptEntity = scriptOptional.get();
                if(!scriptEntity.getPath().equals(fileName)){
                    fileStorageService.deleteFile(fileName);
                    scriptEntity.setPath(fileName);
                    scriptService.updateScript(scriptId, scriptEntity);
                }
            }
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
