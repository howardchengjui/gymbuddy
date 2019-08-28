package com.codingdojo.gymbuddy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.gymbuddy.models.Message;
import com.codingdojo.gymbuddy.repositories.MessageRepository;



@Service
public class MessageService {
	private MessageRepository messageRepository;
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	
	}
	
	public void create(Message message) {
        messageRepository.save(message);
    }
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }
    public List<Message> allMessages() {
    	return (List<Message>) messageRepository.findAll();
    }
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
    public void update(Message message) {
    	messageRepository.save(message);
    }
}
