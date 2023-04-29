package com.stc.assessment.repository;

import com.stc.assessment.model.entity.Item;
import com.stc.assessment.model.enums.ItemType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findByNameAndTypeAndParent(String name, ItemType type, Item parent);

    @Query(nativeQuery = true, value = "SELECT i.* FROM item i \n" +
            "JOIN user_permission p ON p.group_id = i.permission_group_id \n" +
            "WHERE i.id = :itemId AND p.user_email like :userEmail")
    Optional<Item> findByItemIdAndUserEmail(Long itemId, String userEmail);
}