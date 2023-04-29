package com.stc.assessment.repository;

import com.stc.assessment.model.entity.File;
import com.stc.assessment.model.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FileRepository extends CrudRepository<File, Long> {
    Optional<File> findByItem(Item item);

    @Query(nativeQuery = true, value = "SELECT f.* FROM item i JOIN file_data f ON f.item_id = i.id \n" +
            "JOIN user_permission p ON p.group_id = i.permission_group_id \n" +
            "WHERE i.id = :itemId AND p.user_email like :userEmail")
    Optional<File> findFileByItemIdAndUserEmail(Long itemId, String userEmail);

}