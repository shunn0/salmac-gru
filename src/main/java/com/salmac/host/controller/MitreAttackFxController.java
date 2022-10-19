package com.salmac.host.controller;

import com.salmac.host.dto.ServerTechniqueDTO;
import com.salmac.host.dto.TechniqueDTO;
import com.salmac.host.entity.AttackCategory;
import com.salmac.host.entity.TechniqueEntity;
import com.salmac.host.routine.exception.ResourceNotFoundException;
import com.salmac.host.service.MitreAttackService;
import com.salmac.host.service.ServerScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attackfx")
@CrossOrigin(origins = "http://localhost:3000")
public class MitreAttackFxController {

    @Autowired
    MitreAttackService attakService;

    @Autowired
    ServerScriptService serverScriptService;

    @GetMapping("/all")
    public ResponseEntity getTechniqueList() {
        List<TechniqueDTO> serverEntities = attakService.getListOfTechnique();
        if (serverEntities != null) {
            return ResponseEntity.ok().body(serverEntities);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bycat")
    public ResponseEntity getTechniqueListByCategory(AttackCategory category) {
        List<TechniqueDTO> listOfTechniqueByCategory = attakService.getListOfTechniqueByCategory(category);
        if (listOfTechniqueByCategory != null) {
            return ResponseEntity.ok().body(listOfTechniqueByCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{techniqueId}")
    public ResponseEntity getTechnique(@PathVariable("techniqueId") Long techniqueId) {
        Optional<TechniqueEntity> techniqueEntity = attakService.getTechniqueById(techniqueId);
        if (techniqueEntity.isPresent()) {
            return ResponseEntity.ok().body(new TechniqueDTO(techniqueEntity.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("")
    public void addNewTechnique(TechniqueDTO techniqueDTO) {
        attakService.saveTechnique(techniqueDTO, null);
    }

    @PutMapping("/{techniqueId}")
    public ResponseEntity updateTechnique(@PathVariable("techniqueId") Long techniqueId, TechniqueDTO techniqueDTO) {
        Optional<TechniqueEntity> entity = attakService.getTechniqueById(techniqueId);
        if (!entity.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        attakService.saveTechnique(techniqueDTO, techniqueId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{techniqueId}")
    public ResponseEntity deleteTechnique(@PathVariable("techniqueId") Long techniqueId) {
        return attakService.removeTechnique(techniqueId);
    }

    @GetMapping("/category")
    public ResponseEntity getCategoryList() {
        List<AttackCategory.Category> categories = AttackCategory.getCategoryMap();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/attack")
    public ResponseEntity getServerTechniqueDetail(Long serverId, Long techniqueId) {
        ServerTechniqueDTO serverTechniqueDTO = serverScriptService.getTechniqueById(serverId, techniqueId);
        if (serverTechniqueDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(serverTechniqueDTO);
        }
    }

    @PostMapping("/attack")
    public ResponseEntity performAttack(Long serverId, Long techniqueId) {
        //ServerTechniqueDTO serverTechniqueDTO = serverScriptService.getTechniqueById(serverId, techniqueId);
        ServerTechniqueDTO serverTechniqueDTO = serverScriptService.performAttack(serverId, techniqueId);
        if (serverTechniqueDTO == null) {
            serverTechniqueDTO = serverScriptService.getTechniqueById(serverId, techniqueId);
        }
        if (serverTechniqueDTO == null) {
            return ResponseEntity.notFound().build();
        } else {

            //serverTechniqueDTO.setLastOutputText("Vulnerabilities found");
            //serverTechniqueDTO.setPositive(true);
            //serverTechniqueDTO.setLastExecutionTime(LocalDateTime.now());
            return ResponseEntity.ok().body(serverTechniqueDTO);
        }
    }

    @PostMapping("/attack/remediate")
    public ResponseEntity remediateAttack(Long serverId, Long techniqueId) {
        ServerTechniqueDTO serverTechniqueDTO = serverScriptService.remediateAttack(serverId, techniqueId);
        if (serverTechniqueDTO == null) {
            serverTechniqueDTO = serverScriptService.getTechniqueById(serverId, techniqueId);
        }
        if (serverTechniqueDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            //serverTechniqueDTO.setLastOutputText("Vulnerabilities found");
            //serverTechniqueDTO.setPositive(true);
            //serverTechniqueDTO.setLastExecutionTime(LocalDateTime.now());
            //serverTechniqueDTO.setRemediated(true);
            return ResponseEntity.ok().body(serverTechniqueDTO);
        }
        //return serverScriptService.performAttack(serverId, techniqueId);
    }
}
