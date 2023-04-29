package com.stc.assessment.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "permission_group")
@Data
public class PermissionGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String groupName;
    @OneToMany(mappedBy = "permissionGroup", fetch = FetchType.LAZY)
    private List<UserPermission> userPermissions;


}