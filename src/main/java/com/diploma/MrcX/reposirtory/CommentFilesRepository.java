package com.diploma.MrcX.reposirtory;

import com.diploma.MrcX.model.entity.CommentFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentFilesRepository extends JpaRepository<CommentFiles, Long> {
    @Query("select c from CommentFiles c where c.order.id = ?1")
    List<CommentFiles> findByOrder_Id(UUID id);
}