package com.salmac.host.dto;

import com.salmac.host.entity.ScriptEntity;
import com.salmac.host.entity.ScriptType;
import com.salmac.host.entity.Status;

import java.time.LocalDateTime;

public class ScriptDTO {
    private Long id;
    private String name;
    private String targetOS;
    private ScriptType scriptType;
    private Status status;
    private LocalDateTime lastUpdateTime;

    public ScriptDTO(ScriptEntity scriptEntity){
        this.id = scriptEntity.getId();
        this.name = scriptEntity.getName();
        this.targetOS = scriptEntity.getTargetOS();
        this.scriptType = scriptEntity.getScriptType();
        this.status = scriptEntity.getStatus();
        this.lastUpdateTime = scriptEntity.getLastUpdateTime();
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

    public String getTargetOS() {
        return targetOS;
    }

    public void setTargetOS(String targetOS) {
        this.targetOS = targetOS;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public void setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
