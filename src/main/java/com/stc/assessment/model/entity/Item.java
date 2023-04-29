package com.stc.assessment.model.entity;

import com.stc.assessment.model.enums.ItemType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Data
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_item_id")
    private Item parent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_group_id")
    private PermissionGroup permissionGroup;

}