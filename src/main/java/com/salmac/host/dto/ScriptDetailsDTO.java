package com.salmac.host.dto;

import com.salmac.host.entity.ScriptEntity;

import java.time.LocalDateTime;

public class ScriptDetailsDTO {

    private Long id;
    private String name;
    private String content;
    private String targetOS;
    private String purpose;
    private String scriptType;
    private String status;
    private LocalDateTime lastUpdateTime;

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
}
