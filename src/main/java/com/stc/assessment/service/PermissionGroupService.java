package com.stc.assessment.service;

import com.stc.assessment.exception.AppGenericException;
import com.stc.assessment.model.entity.PermissionGroup;
import com.stc.assessment.repository.PermissionGroupRepository;
import com.stc.assessment.repository.UserPermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionGroupService {
    private static final Logger LOG = LoggerFactory.getLogger(PermissionGroupService.class);
    @Autowired
    PermissionGroupRepository permissionGroupRepository;
    @Autowired
    UserPermissionRepository userPermissionRepository;

    public PermissionGroup createPermissionGroupIfNotExists(PermissionGroup permissionGroup) {
        if (permissionGroup == null) {
            throw new AppGenericException("Permission group is not found in your request!");
        }
        Optional<PermissionGroup> alreadySavedGroup = permissionGroupRepository.findById(permissionGroup.getId());
        if (alreadySavedGroup.isPresent())
            return alreadySavedGroup.get();

        LOG.info("Permission Group doesn't exist!");

        validatePermissionGroup(permissionGroup);
        PermissionGroup savedGroup = permissionGroupRepository.save(permissionGroup);
        permissionGroup.getUserPermissions().forEach(userPermission -> userPermission.setPermissionGroup(savedGroup));
        userPermissionRepository.saveAll(permissionGroup.getUserPermissions());
        LOG.info("New Permission Group has been created!");
        return savedGroup;
    }

    private void validatePermissionGroup(PermissionGroup permissionGroup) {
        if (permissionGroup.getUserPermissions() != null && permissionGroup.getUserPermissions().size() != 2)
            throw new AppGenericException("Permission group is invalid!");
    }
}
