package com.stc.assessment.controller;

import com.stc.assessment.model.dto.BaseResponse;
import com.stc.assessment.model.dto.ItemDto;
import com.stc.assessment.model.entity.Item;
import com.stc.assessment.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<BaseResponse<ItemDto>> saveItem(@RequestBody ItemDto itemDto, @RequestHeader String userEmail) throws IOException {
        Item savedItem = itemService.saveItem(itemDto, userEmail);
        ItemDto mapEntityToDto = ItemDto.entityToDto(savedItem);
        return ResponseEntity.ok(new BaseResponse<>(mapEntityToDto));
    }

    @PostMapping("/file")
    public ResponseEntity<BaseResponse<ItemDto>> saveFile(@ModelAttribute ItemDto itemDto,
                                                         @RequestHeader String userEmail) throws IOException {
        Item savedItem = itemService.saveItem(itemDto, userEmail);
        ItemDto mapEntityToDto = ItemDto.entityToDto(savedItem);
        return ResponseEntity.ok(new BaseResponse<>(mapEntityToDto));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<BaseResponse<ItemDto>> getFileMetaData(@PathVariable long itemId, @RequestHeader String userEmail) {
        ItemDto savedItemDto = itemService.getFileMetaData(itemId, userEmail);
        return ResponseEntity.ok(new BaseResponse<>(savedItemDto));
    }

    @GetMapping("/download/{itemId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable long itemId, @RequestHeader String userEmail) {
        byte[] response = itemService.downloadFile(itemId, userEmail);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.attachment().filename("file.txt").build());
        headers.setContentLength(response.length);

        return ResponseEntity.ok().headers(headers)
                .body(response);
    }
}
