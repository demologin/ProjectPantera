package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MessageRepository implements Repository<Message>{
    private final Map<Long, Message> map = new HashMap<>();
    public static final AtomicLong id = new AtomicLong();

    @Override
    public Message get(long id) {
        return map.get(id);
    }

    @Override
    public Optional<Message> findBy(String name) {
        return map.values()
                .stream()
                .filter(u -> u.getName().equals(name))
                .findAny();
    }

    @Override
    public Map<Long, Message> getAll() {
        return map;
    }

    @Override
    public void create(Message message) {
        if (!map.containsKey(message.getId())) {
            message.setId(id.incrementAndGet());
        }
        map.put(message.getId(), message);
    }

    @Override
    public void delete(Message message) {
        map.remove(message.getId());
    }

    @Override
    public void update(Message message) {
        if (map.containsKey(message.getId())){
            message.setId(id.incrementAndGet());
            message.setName(message.getName());
            message.setEmail(message.getEmail());
            message.setMessage(message.getMessage());
        }
    }
}
