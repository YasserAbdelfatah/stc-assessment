package com.stc.assessment.service;

import com.stc.assessment.exception.AppGenericException;
import com.stc.assessment.model.dto.ItemDto;
import com.stc.assessment.model.entity.File;
import com.stc.assessment.model.entity.Item;
import com.stc.assessment.model.entity.PermissionGroup;
import com.stc.assessment.model.entity.UserPermission;
import com.stc.assessment.model.enums.ItemType;
import com.stc.assessment.model.enums.PermissionLevel;
import com.stc.assessment.repository.ItemRepository;
import com.stc.assessment.repository.UserPermissionRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private static final Logger LOG = LoggerFactory.getLogger(ItemService.class);
    private static final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    PermissionGroupService permissionGroupService;
    @Autowired
    FileService fileService;
    @Autowired
    UserPermissionRepository userPermissionRepository;

    public Item saveItem(ItemDto itemDto, String userEmail) throws IOException {

        Item item = modelMapper.map(itemDto, Item.class);

        switch (itemDto.getType()) {
            case SPACE:
                return saveSpace(itemDto, item);
            case FOLDER:
                return saveFolderOrFileMetaData(itemDto, item, userEmail);
            case FILE:
                return saveFile(itemDto, item, userEmail);
            default:
                throw new AppGenericException("Type is invalid!");
        }
    }

    private Item saveSpace(ItemDto itemDto, Item item) {
        checkIfItemAlreadyExists(itemDto.getName(), itemDto.getType(), null);
        PermissionGroup permissionGroup = permissionGroupService.createPermissionGroupIfNotExists(itemDto.getPermissionGroup());
        item.setPermissionGroup(permissionGroup);
        return itemRepository.save(item);
    }

    private Item saveFolderOrFileMetaData(ItemDto itemDto, Item item, String userEmail) {

        Item parentItem = getParentItem(itemDto);

        checkIfItemAlreadyExists(itemDto.getName(), itemDto.getType(), parentItem);

        isValidUser(userEmail, parentItem.getPermissionGroup().getId(), PermissionLevel.EDIT);

        item.setParent(parentItem);
        item.setPermissionGroup(parentItem.getPermissionGroup());
        return itemRepository.save(item);
    }

    private Item saveFile(ItemDto itemDto, Item item, String userEmail) throws IOException {

        if (itemDto.getContent() == null) {
            throw new AppGenericException("file content must be provided to save file!");
        }

        Item savedItem = saveFolderOrFileMetaData(itemDto, item, userEmail);
        File file = new File(itemDto.getContent().getBytes(), savedItem);
        fileService.save(file);
        return savedItem;
    }

    public ItemDto getFileMetaData(long itemId, String userEmail) {
        Optional<Item> itemOptional = itemRepository.findByItemIdAndUserEmail(itemId, userEmail);
        if (!itemOptional.isPresent()) {
            throw new AppGenericException("File doesn't exist!");
        }
        return ItemDto.entityToDto(itemOptional.get());
    }
    @Transactional
    public byte[] downloadFile(long itemId, String userEmail) {
        return fileService.getFileContentDirect(itemId,userEmail);
    }

    private Item getParentItem(ItemDto itemDto) {
        if (itemDto.getParentId() == null) {
            throw new AppGenericException("Parent item must be provided to save folder or file!");
        }

        Optional<Item> parentItem = itemRepository.findById(itemDto.getParentId());
        if (!parentItem.isPresent()) {
            throw new AppGenericException("Parent item doesn't exist!");
        }
        return parentItem.get();
    }

    public void isValidUser(String userEmail, long permissionGroupId, PermissionLevel permissionLevel) {
        Optional<List<UserPermission>> userPermissions = userPermissionRepository.findByUserEmail(userEmail);
        boolean hasPermission = false;
        if (userPermissions.isPresent()) {
            hasPermission = userPermissions.get().stream().anyMatch(permission ->
                    permission.getPermissionGroup().getId() == permissionGroupId
                            && permission.getPermissionLevel().equals(permissionLevel));
        }

        if (!hasPermission)
            throw new AppGenericException("User doesn't have permission to access this space files");
    }

    private void checkIfItemAlreadyExists(String name, ItemType type, Item parent) {
        Optional<Item> itemOptional = itemRepository.findByNameAndTypeAndParent(name, type, parent);
        if (itemOptional.isPresent()) {
            throw new AppGenericException("Item is already existed");
        }
    }
}
