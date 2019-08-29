package com.codingdojo.gymbuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingdojo.gymbuddy.models.FileStorage;

public interface FileStorageRepository extends JpaRepository<FileStorage, String> {

}
