package com.stc.assessment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stc.assessment.model.entity.Item;
import com.stc.assessment.model.entity.PermissionGroup;
import com.stc.assessment.model.enums.ItemType;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class ItemDto {
    private Long id;
    private String name;
    private ItemType type;
    private MultipartFile content;
    private Long parentId;
    private PermissionGroup permissionGroup;

    public static ItemDto entityToDto(Item item) {
        return ItemDto.builder().id(item.getId())
                .name(item.getName())
                .type(item.getType())
                .build();
    }
}
