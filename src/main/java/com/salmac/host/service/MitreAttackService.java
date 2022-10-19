package com.salmac.host.service;

import com.salmac.host.dto.TechniqueDTO;
import com.salmac.host.entity.AttackCategory;
import com.salmac.host.entity.ScriptEntity;
import com.salmac.host.entity.TechniqueEntity;
import com.salmac.host.repo.MitreAttackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MitreAttackService {

    @Autowired
    ScriptService scriptService;

    @Autowired
    MitreAttackRepo repo;
    public List<TechniqueDTO> getListOfTechnique() {
        List<TechniqueEntity> list = repo.findAll();
        if(!list.isEmpty()){
            return list.stream().map(e -> new TechniqueDTO(e)).collect(Collectors.toList());
        } else{
            return null;
        }
    }

    public List<TechniqueDTO> getListOfTechniqueByCategory(AttackCategory category) {
        List<TechniqueEntity> list = repo.getTechniqueEntitiesByCategory(category);
        if(!list.isEmpty()){
            return list.stream().map(e -> new TechniqueDTO(e)).collect(Collectors.toList());
        } else{
            return null;
        }
    }

    public Optional<TechniqueEntity> getTechniqueById(Long techniqueId){
        return repo.findById(techniqueId);
    }

    public void saveTechnique(TechniqueDTO techniqueDTO, Long techniqueId){
        TechniqueEntity techniqueEntity = techniqueDTOtoEntity(techniqueDTO);
        if(techniqueId != null){
            techniqueEntity.setId(techniqueId);
        }
        repo.save(techniqueEntity);
    }

    public ResponseEntity removeTechnique(Long techniqueId){
        Optional<TechniqueEntity> entity = repo.findById(techniqueId);
        if(entity.isPresent()){
            repo.delete(entity.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public TechniqueEntity techniqueDTOtoEntity(TechniqueDTO dto){
        TechniqueEntity techniqueEntity = new TechniqueEntity();
        techniqueEntity.setName(dto.getName());
        techniqueEntity.setCode(dto.getCode());
        techniqueEntity.setCategory(dto.getCategory());
        techniqueEntity.setScriptEntity(getScriptEntity(dto.getScriptId()));
        techniqueEntity.setRemedyScriptEntity(getScriptEntity(dto.getRemedyScriptId()));
        techniqueEntity.setStatus(dto.getStatus());
        return techniqueEntity;
    }

    private ScriptEntity getScriptEntity(Long id){
        if(id == null || id < 0){
            return null;
        }
        Optional<ScriptEntity> scriptEntityOpt = scriptService.getScriptById(id);
        if(scriptEntityOpt.isPresent()){
            return scriptEntityOpt.get();
        } else {
            return null;
        }

    }
}
