package com.salmac.host.controller;

import com.salmac.host.dto.ScriptDTO;
import com.salmac.host.dto.ScriptDetailsDTO;
import com.salmac.host.entity.ScriptEntity;
import com.salmac.host.entity.ServerEntity;
import com.salmac.host.files.FileStorageService;
import com.salmac.host.routine.Utils;
import com.salmac.host.routine.exception.ResourceNotFoundException;
import com.salmac.host.service.ScriptService;
import com.salmac.host.service.ServerService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        String ip = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        ip = request.getRemoteAddr() + ":" + agentPort;
        /*if(!Utils.containAlpha(agentIp)) {

        } else {
            ip = agentIp + ":" + agentPort;
        }*/
        System.out.println("Agent IP: "+agentIp +" | Agent Port: "+agentPort +" | Received request from " + ip);
        //trigger a heartbeat
        serverService.heartBeat(ip , agentName, agentOS);

        ResponseEntity re = ResponseEntity.ok().build();
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
    public ResponseEntity getScriptList(Integer pageNumber, Integer pageSize) {
        try {
            //Page<ScriptEntity> courseEntities = scriptService.getListOfScripts(pageNumber,pageSize);
            List<ScriptDTO> scriptEntities = scriptService.getListOfScripts(pageNumber, pageSize);
            if (!scriptEntities.isEmpty()) {
                return ResponseEntity.ok().body(scriptEntities);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/script/{scriptId}")
    public ResponseEntity getScript(@PathVariable("scriptId") Long scriptId) {
        Optional<ScriptEntity> scriptOptional = scriptService.getScriptById(scriptId);
        if (scriptOptional.isPresent()) {
            return ResponseEntity.ok().body(new ScriptDetailsDTO(scriptOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/scriptname/{scriptId}")
    public ResponseEntity getScriptName(@PathVariable("scriptId") Long scriptId) {
        Optional<ScriptEntity> scriptOptional = scriptService.getScriptById(scriptId);
        if (scriptOptional.isPresent()) {
            System.out.println("present");
            return ResponseEntity.ok().body(scriptOptional.get().getName());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/script/byos/{os}")
    public ResponseEntity getScriptByOS(@PathVariable("os") String OS) {
        List<ScriptDTO> scriptEntities = scriptService.getListOfScriptsByOS(OS);
        if (!scriptEntities.isEmpty()) {
            return ResponseEntity.ok().body(scriptEntities);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*@PostMapping("/script")
    public void addScript(ScriptDetailsDTO scriptDetailsDTO) {
        //System.out.println("Inside addScript");
        scriptService.addScript(scriptDetailsDTO.toScriptEntity());
        //scriptService.addScript(scriptEntity);
    }*/

    @PostMapping("/script")
    public void addScriptWithFile(ScriptDetailsDTO scriptDetailsDTO, @RequestParam MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        ScriptEntity scriptEntity = scriptDetailsDTO.toScriptEntity();
        scriptEntity.setPath(fileName);
        scriptEntity.setLastUpdateTime(LocalDateTime.now());
        scriptService.addScript(scriptEntity);
    }

    @PutMapping("/script/{scriptId}")
    public ResponseEntity updateScript(@PathVariable("scriptId") Long scriptId, ScriptDetailsDTO scriptDetailsDTO, MultipartFile file) {
        try {
            Optional<ScriptEntity> scriptOptional = scriptService.getScriptById(scriptId);
            ScriptEntity scriptEntity = scriptDetailsDTO.toScriptEntity();
            if(file != null){
                String fileName = fileStorageService.storeFile(file);
                if (!scriptEntity.getPath().equals(fileName)) {
                    fileStorageService.deleteFile(fileName);
                    scriptEntity.setPath(fileName);
                }
            }
            scriptService.updateScript(scriptId, scriptEntity);

            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /*@PutMapping("/script/file/{scriptId}")
    public ResponseEntity updateScriptFiles(@PathVariable("scriptId") Long scriptId, @RequestParam MultipartFile file) {
        try {
            Optional<ScriptEntity> scriptOptional = scriptService.getScriptById(scriptId);
            if (scriptOptional.isPresent()) {
                String fileName = fileStorageService.storeFile(file);
                ScriptEntity scriptEntity = scriptOptional.get();
                if (!scriptEntity.getPath().equals(fileName)) {
                    fileStorageService.deleteFile(fileName);
                    scriptEntity.setPath(fileName);
                    scriptService.updateScript(scriptId, scriptEntity);
                }
            }
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }*/


    @DeleteMapping("/script/{scriptId}")
    public ResponseEntity deleteScript(@PathVariable("scriptId") Long scriptId) {
        try {
            scriptService.removeScript(scriptId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/downloadscript/{scriptId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("scriptId") Long scriptId, HttpServletRequest request) {
        String fileName = null;
        Optional<ScriptEntity> scriptOptional = scriptService.getScriptById(scriptId);
        if (scriptOptional.isPresent()) {
            fileName = scriptOptional.get().getPath();
        } else {
            return ResponseEntity.notFound().build();
        }
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
