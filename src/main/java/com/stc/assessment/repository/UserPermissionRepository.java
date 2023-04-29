package com.stc.assessment.repository;

import com.stc.assessment.model.entity.UserPermission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserPermissionRepository extends CrudRepository<UserPermission, Long> {
    Optional<List<UserPermission>> findByUserEmail(String userEmail);
}