package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FilesRepository extends JpaRepository<Files, Long> {
    @Query("select f from Files f where f.order.id = ?1")
    List<Files> findByOrder_Id(UUID id);
}