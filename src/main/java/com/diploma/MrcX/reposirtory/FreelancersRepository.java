package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Category;
import com.diploma.MrcX.model.entity.Freelancers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FreelancersRepository extends JpaRepository<Freelancers, String> {
    @Transactional
    @Modifying
    @Query("update Freelancers f set f.category = ?1 where f.id = ?2")
    int updateCategoryById(Category category, String id);
}