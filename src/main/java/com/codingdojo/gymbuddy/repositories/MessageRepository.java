package com.codingdojo.gymbuddy.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.gymbuddy.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{

}
