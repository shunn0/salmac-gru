package com.salmac.host.service;

import com.salmac.host.dto.ServerTechniqueDTO;
import com.salmac.host.dto.TechniqueDTO;
import com.salmac.host.entity.*;
import com.salmac.host.repo.MitreAttackRepo;
import com.salmac.host.repo.ServerRepo;
import com.salmac.host.repo.ServerScriptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ServerScriptService {
    @Autowired
    ScriptService scriptService;

    @Autowired
    MitreAttackRepo attackRepo;

    @Autowired
    ServerRepo serverRepo;

    @Autowired
    ServerScriptRepo serverScriptRepo;

    public ServerTechniqueDTO getTechniqueById(Long serverId, Long techniqueId) {
        Optional<ServerEntity> se = serverRepo.findById(serverId);
        Optional<TechniqueEntity> te = attackRepo.findById(techniqueId);
        if (!se.isPresent() || !te.isPresent()) {
            return null;
        }
        return getServerTechniqueDTO(serverId, techniqueId, se.get(), te.get());
    }

    private ServerTechniqueDTO getServerTechniqueDTO(Long serverId, Long techniqueId, ServerEntity se, TechniqueEntity te) {
        ServerTechniqueDTO dto = new ServerTechniqueDTO(te);
        dto.setServerId(se.getId());
        dto.setServerName(se.getName());

        List<ServerScriptEntity> mainScriptExecList = getEntities(serverId, techniqueId, dto.getScriptId());
        if (!mainScriptExecList.isEmpty()) {
            dto.setLastExecutionTime(mainScriptExecList.get(0).getLastExecutionTime());
            dto.setLastOutputText(mainScriptExecList.get(0).getOutputText());
            dto.setPositive(mainScriptExecList.get(0).getIsPositive());
        }

        List<ServerScriptEntity> remedyScriptExecList = getEntities(serverId, techniqueId, dto.getRemedyScriptId());
        if (!remedyScriptExecList.isEmpty()) {
            dto.setRemediated(remedyScriptExecList.get(0).getIsRemedy());
        }

        return dto;
    }

    public ServerTechniqueDTO performAttack(Long serverId, Long techniqueId) {
        Optional<TechniqueEntity> te = attackRepo.findById(techniqueId);
        Optional<ServerEntity> se = serverRepo.findById(serverId);
        if (!se.isPresent() || !te.isPresent() || te.get().getScriptEntity() == null) {
            return null;
        }

//        List<ServerScriptEntity> remedyScriptExecList = getEntities(serverId, techniqueId, te.get().getRemedyScriptEntity());
//        if (!remedyScriptExecList.isEmpty()) {// already remediated
//            return getServerTechniqueDTO(serverId, techniqueId, se.get(), te.get());
//        }

        ServerTechniqueDTO dto = new ServerTechniqueDTO(te.get());
        dto.setServerId(se.get().getId());
        dto.setServerName(se.get().getName());


        ServerScriptEntity ssEntity = new ServerScriptEntity();
        ssEntity.setServerId(dto.getServerId());
        ssEntity.setScriptId(dto.getScriptId());
        ssEntity.setTechniqueId(dto.getTechniqueId());
        ssEntity.setIsRemedy(false);
        ssEntity.setLastExecutionTime(LocalDateTime.now());

        //Dummy code
        int rand = getRand();
        if (rand % 2 == 0) {
            ssEntity.setIsPositive(true);
            ssEntity.setOutputText("type=AVC msg=audit(" + rand + ".123.234): apparmor=\"ALLOWED\" operation=\"open\" " +
                    "profile=\"/usr/bin/crontab\" name=\"/etc/passwd\" pid=2342 comm=\"crontab\" request_mask=\"r\"" +
                    "denied_mask=\"r\" fsuid=1000 ouid=0");
        } else {
            ssEntity.setIsPositive(false);
            ssEntity.setOutputText(null);
        }

        try {
            serverScriptRepo.save(ssEntity);
        } catch (Exception e){
            return null;
        }


        dto.setLastExecutionTime(ssEntity.getLastExecutionTime());
        dto.setPositive(ssEntity.getIsPositive());
        dto.setLastOutputText(ssEntity.getOutputText());
        dto.setRemediated(false);


        return dto;
    }

    public ServerTechniqueDTO remediateAttack(Long serverId, Long techniqueId) {
        Optional<ServerEntity> se = serverRepo.findById(serverId);
        Optional<TechniqueEntity> te = attackRepo.findById(techniqueId);
        if (!se.isPresent() || !te.isPresent()
                || te.get().getScriptEntity() == null
                || te.get().getRemedyScriptEntity() == null) {
            return null;
        }

//        List<ServerScriptEntity> remedyScriptExecList = getEntities(serverId, techniqueId, te.get().getRemedyScriptEntity());
//        if (!remedyScriptExecList.isEmpty()) {// already remediated
//            return getServerTechniqueDTO(serverId, techniqueId, se.get(), te.get());
//        }

        ServerTechniqueDTO dto = new ServerTechniqueDTO(te.get());
        dto.setServerId(se.get().getId());
        dto.setServerName(se.get().getName());

        List<ServerScriptEntity> mainScriptExecList = getEntities(serverId, techniqueId, dto.getScriptId());
        if (!mainScriptExecList.isEmpty() && mainScriptExecList.get(0).getIsPositive()) {
            dto.setLastExecutionTime(mainScriptExecList.get(0).getLastExecutionTime());
            dto.setLastOutputText(mainScriptExecList.get(0).getOutputText());
            dto.setPositive(mainScriptExecList.get(0).getIsPositive());
        } else {
            //if attach script has not run yet or according to last run result was not positive,
            // then no need to run remedy scrip
            return null;
        }

        dto.setRemediated(true);

        ServerScriptEntity ssEntity = new ServerScriptEntity();
        ssEntity.setServerId(dto.getServerId());
        ssEntity.setScriptId(dto.getRemedyScriptId());
        ssEntity.setTechniqueId(dto.getTechniqueId());
        ssEntity.setIsRemedy(true);
        ssEntity.setLastExecutionTime(LocalDateTime.now());
        ssEntity.setIsPositive(false);
        ssEntity.setOutputText(null);
        try {
            serverScriptRepo.save(ssEntity);
        } catch (Exception e){
            return null;
        }

        return dto;
    }

    private List<ServerScriptEntity> getEntities(Long serverId, Long techniqueId, Long scriptId) {
        if (scriptId == null) {
            return new ArrayList<ServerScriptEntity>();
        }
        return serverScriptRepo.getServerScriptEntitiesByServerIdAndTechniqueIdAndScriptIdOrderByLastExecutionTime
                (serverId, techniqueId, scriptId);
    }

    private List<ServerScriptEntity> getEntities(Long serverId, Long techniqueId, ScriptEntity scriptEntity) {
        if (scriptEntity == null) {
            return new ArrayList<ServerScriptEntity>();
        }
        return serverScriptRepo.getServerScriptEntitiesByServerIdAndTechniqueIdAndScriptIdOrderByLastExecutionTime
                (serverId, techniqueId, scriptEntity.getId());
    }

    private int getRand() {
        Random random = new Random();
        return random.nextInt();
    }
}
