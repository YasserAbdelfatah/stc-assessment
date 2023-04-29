package com.stc.assessment.service;

import com.stc.assessment.exception.AppGenericException;
import com.stc.assessment.model.entity.File;
import com.stc.assessment.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    public File save(File file) {
        return fileRepository.save(file);
    }

    public byte[] getFileContentDirect(long itemId, String userName) {
        Optional<File> fileOptional = fileRepository.findFileByItemIdAndUserEmail(itemId, userName);
        if (!fileOptional.isPresent()) {
            throw new AppGenericException("File doesn't exist!");
        }
        return fileOptional.get().getBinary();
    }
}
