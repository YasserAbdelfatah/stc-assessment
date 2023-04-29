package com.stc.assessment.model.entity;

import com.stc.assessment.model.enums.PermissionLevel;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "user_permission")
@Data
public class UserPermission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_email", unique = true)
    private String userEmail;
    @Column(name = "permission_level")
    @Enumerated(EnumType.STRING)
    private PermissionLevel permissionLevel;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private PermissionGroup permissionGroup;

}