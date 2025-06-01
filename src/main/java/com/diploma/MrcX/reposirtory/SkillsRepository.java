package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillsRepository extends JpaRepository<Skills, UUID> {
    Skills findByNameIgnoreCase(String name);
}