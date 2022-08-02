package com.salmac.host.dto;

import com.salmac.host.entity.ScriptEntity;
import com.salmac.host.entity.ScriptType;
import com.salmac.host.entity.Status;

import java.io.Serializable;
import java.time.LocalDateTime;


public class ScriptDetailsDTO implements Serializable {

    private Long id;
    private String name;
    private String content;
    private String targetOS;
    private String purpose;
    private ScriptType scriptType;
    private Status status;
    private LocalDateTime lastUpdateTime;

    public ScriptDetailsDTO(){

    }
    public ScriptDetailsDTO(ScriptEntity scriptEntity){
        this.id = scriptEntity.getId();
        this.name = scriptEntity.getName();
        this.content = scriptEntity.getContent();
        this.targetOS = scriptEntity.getTargetOS();
        this.purpose = scriptEntity.getPurpose();
        this.scriptType = scriptEntity.getScriptType();
        this.status = scriptEntity.getStatus();
        this.lastUpdateTime = scriptEntity.getLastUpdateTime();
    }

    public ScriptEntity toScriptEntity(){
        ScriptEntity scriptEntity = new ScriptEntity();
        scriptEntity.setId(this.id);
        scriptEntity.setName(this.name);
        scriptEntity.setContent(this.content);
        scriptEntity.setTargetOS(this.targetOS);
        scriptEntity.setPurpose(this.purpose);
        scriptEntity.setScriptType(this.scriptType);
        scriptEntity.setStatus(this.status);
        scriptEntity.setLastUpdateTime(this.lastUpdateTime);

        return scriptEntity;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetOS() {
        return targetOS;
    }

    public void setTargetOS(String targetOS) {
        this.targetOS = targetOS;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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
