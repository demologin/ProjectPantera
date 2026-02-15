package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Message;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MessageRepository implements Repository<Message>{
    private final Map<Long, Message> map;
    public static final AtomicLong id = new AtomicLong();

    public MessageRepository(Storage messageStorage) {
        this.map = messageStorage.getMessages();
    }

    @Override
    public Optional<Message> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Message> findBy(String name, String email) {
        return map.values()
                .stream()
                .filter(u -> u.getName().equals(name))
                .filter(u -> u.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Map<Long, Message> getAll() {
        return map;
    }

    @Override
    public Optional<Message> create(Message message) {
        if (!map.containsKey(message.getId())) {
            message.setId(id.incrementAndGet());
        }
        map.put(message.getId(), message);
        return Optional.of(message);
    }

    @Override
    public Boolean delete(Message message) {
        int sizeBeforeDelete = map.size();
        map.remove(message.getId());
        int sizeAfterDelete = map.size();
        return sizeBeforeDelete > sizeAfterDelete ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Optional<Message> update(Message message) {
        message.setName(message.getName());
        message.setEmail(message.getEmail());
        message.setMessage(message.getMessage());
        return Optional.of(message);
    }
}
