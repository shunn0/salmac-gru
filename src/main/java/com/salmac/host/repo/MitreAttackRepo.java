package com.salmac.host.repo;

import com.salmac.host.entity.AttackCategory;
import com.salmac.host.entity.TechniqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MitreAttackRepo extends JpaRepository<TechniqueEntity,Long> {
    List<TechniqueEntity> getTechniqueEntitiesByCategory(AttackCategory category);
}
