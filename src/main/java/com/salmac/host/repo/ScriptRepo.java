package com.salmac.host.repo;

import com.salmac.host.entity.ScriptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScriptRepo extends JpaRepository<ScriptEntity,Long> {

    List<ScriptEntity> getScriptEntitiesByTargetOS(String OS);
}
