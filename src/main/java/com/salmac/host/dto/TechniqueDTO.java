package com.salmac.host.dto;

import com.salmac.host.entity.AttackCategory;
import com.salmac.host.entity.Status;
import com.salmac.host.entity.TechniqueEntity;

import java.io.Serializable;

public class TechniqueDTO implements Serializable {
    private Long id;
    private String name;
    private String code;
    private AttackCategory category;
    private Long scriptId;
    private String scriptName;
    private Long remedyScriptId;
    private String remedyScriptName;
    private Status status;

    public TechniqueDTO(){

    }
    public TechniqueDTO(TechniqueEntity techniqueEntity){
        this.id = techniqueEntity.getId();
        this.name = techniqueEntity.getName();
        this.code = techniqueEntity.getCode();
        this.category = techniqueEntity.getCategory();
        if(techniqueEntity.getScriptEntity() != null) {
            this.scriptId = techniqueEntity.getScriptEntity().getId();
            this.scriptName = techniqueEntity.getScriptEntity().getName();
        }
        if(techniqueEntity.getRemedyScriptEntity() != null) {
            this.remedyScriptId = techniqueEntity.getRemedyScriptEntity().getId();
            this.remedyScriptName = techniqueEntity.getRemedyScriptEntity().getName();
        }
        this.status = techniqueEntity.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AttackCategory getCategory() {
        return category;
    }

    public void setCategory(AttackCategory category) {
        this.category = category;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getRemedyScriptName() {
        return remedyScriptName;
    }

    public void setRemedyScriptName(String remedyScriptName) {
        this.remedyScriptName = remedyScriptName;
    }

    public Status getStatus() {
        return status;
    }

    public Long getScriptId() {
        return scriptId;
    }

    public void setScriptId(Long scriptId) {
        this.scriptId = scriptId;
    }

    public Long getRemedyScriptId() {
        return remedyScriptId;
    }

    public void setRemedyScriptId(Long remedyScriptId) {
        this.remedyScriptId = remedyScriptId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
