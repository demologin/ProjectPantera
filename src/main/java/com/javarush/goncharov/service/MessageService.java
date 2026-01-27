package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.repository.Repository;

import java.util.Map;
import java.util.Optional;

public class MessageService {
    private final Repository<Message> messageRepository;

    public MessageService(Repository<Message> messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Optional<Message> get(Long id){
        return messageRepository.get(id);
    }

    public Optional<Message> find(String name, String email){
        return messageRepository.findBy(name, email);
    }

    public void post(Message message){
        message.setId(0L);
        messageRepository.create(message);
    }

    public void delete(Message message){
        messageRepository.delete(message);
    }

    public void update(Message message){
        messageRepository.update(message);
    }

    public Map<Long, Message> getAll(){
        return messageRepository.getAll();
    }
}