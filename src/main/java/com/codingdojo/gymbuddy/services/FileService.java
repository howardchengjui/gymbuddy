package com.codingdojo.gymbuddy.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.codingdojo.gymbuddy.exceptions.FileStorageException;
import com.codingdojo.gymbuddy.exceptions.MyFileNotFoundException;
import com.codingdojo.gymbuddy.models.FileStorage;
import com.codingdojo.gymbuddy.repositories.FileStorageRepository;


@Service
public class FileService {

	@Autowired
    private FileStorageRepository dbFileRepository;

    public FileStorage storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            FileStorage fileStorage = new FileStorage(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(fileStorage);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public FileStorage getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}

