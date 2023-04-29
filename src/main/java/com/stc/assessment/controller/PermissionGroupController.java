package com.stc.assessment.controller;

import com.stc.assessment.model.dto.BaseResponse;
import com.stc.assessment.model.entity.PermissionGroup;
import com.stc.assessment.service.PermissionGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class PermissionGroupController {

    private final PermissionGroupService permissionGroupService;

    @PostMapping
    public ResponseEntity<BaseResponse<PermissionGroup>> createGroup(@RequestBody PermissionGroup permissionGroup)  {

        PermissionGroup savedPermissionGroup = permissionGroupService.createPermissionGroupIfNotExists(permissionGroup);
        return ResponseEntity.ok(new BaseResponse<>(savedPermissionGroup));
    }
}
