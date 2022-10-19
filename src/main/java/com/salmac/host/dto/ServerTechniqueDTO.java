package com.salmac.host.dto;

import com.salmac.host.entity.Status;
import com.salmac.host.entity.TechniqueEntity;

import java.time.LocalDateTime;

public class ServerTechniqueDTO {
    private Long techniqueId;
    private String name;
    private String code;
    private String category;
    private Long scriptId;
    private String scriptName;
    private Long remedyScriptId;
    private String remedyScriptName;
    private Long serverId;
    private String serverName;
    private String lastOutputText;
    private LocalDateTime lastExecutionTime;
    private boolean isRemediated;
    private Boolean isPositive;
    private Status status;

    public ServerTechniqueDTO(TechniqueEntity techniqueEntity){
        this.techniqueId = techniqueEntity.getId();
        this.name = techniqueEntity.getName();
        this.code = techniqueEntity.getCode();
        this.category = techniqueEntity.getCategory().category;
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

    public Long getTechniqueId() {
        return techniqueId;
    }

    public void setTechniqueId(Long techniqueId) {
        this.techniqueId = techniqueId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getLastOutputText() {
        return lastOutputText;
    }

    public void setLastOutputText(String lastOutputText) {
        this.lastOutputText = lastOutputText;
    }

    public LocalDateTime getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(LocalDateTime lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }

    public boolean isRemediated() {
        return isRemediated;
    }

    public void setRemediated(boolean remediated) {
        isRemediated = remediated;
    }

    public Boolean getPositive() {
        return isPositive;
    }

    public void setPositive(Boolean positive) {
        isPositive = positive;
    }
}
