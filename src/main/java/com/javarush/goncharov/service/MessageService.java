package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.repository.MessageRepository;
import com.javarush.goncharov.repository.Repository;

import java.util.Map;
import java.util.Optional;

public class MessageService {
    private static MessageService instance;
    private final Repository<Message> repository;

    private MessageService(Repository<Message> repository) {
        this.repository = repository;
    }

    public static MessageService getInstance(Repository<Message> repository) {
        if (instance == null) {
            instance = new MessageService(repository);
        }
        return instance;
    }

    public Message get(Long id){
        return repository.get(id);
    }

    public Optional<Message> find(String name){
        return repository.findBy(name);
    }

    public void post(Message message){
        message.setId(0L);
        repository.create(message);
    }

    public void delete(Message message){
        repository.delete(message);
    }

    public void update(Message message){
        repository.update(message);
    }

    public Map<Long, Message> getAll(){
        return repository.getAll();
    }
}