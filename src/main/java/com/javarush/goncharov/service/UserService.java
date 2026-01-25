package com.javarush.goncharov.service;


import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.Repository;
import com.javarush.goncharov.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

public class UserService {
//    private static UserService instance;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
//    public static UserService getInstance(Repository<User> repository){
//        if (instance == null){
//            return new UserService(userRepository);
//        }
//        return instance;
//    }

    public User get(Long id){
        return userRepository.get(id);
    }

    public Optional<User> find(String login){
        return userRepository.findBy(login);
    }

    public void post(User user){
        if (userRepository.findBy(user.getLogin()).stream().findAny().isEmpty()){
            user.setId(0L);
            userRepository.create(user);
        }
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void update(User user){
        userRepository.update(user);
    }

    public Map<Long, User> getAll(){
        return userRepository.getAll();
    }
    
}
