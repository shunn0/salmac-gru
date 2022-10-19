package com.salmac.host.repo;

import com.salmac.host.entity.ServerScriptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerScriptRepo extends JpaRepository<ServerScriptEntity,Long> {
    List<ServerScriptEntity> getServerScriptEntitiesByServerIdAndTechniqueIdAndScriptIdOrderByLastExecutionTime
            (Long serverId, Long techniqueId, Long scriptId);
}
