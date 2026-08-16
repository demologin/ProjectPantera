package com.javarush.aleinik.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class MyConfiguration {

    public MyConfiguration configure() {
        System.out.println("configure()");
        return this;
    }

    public MyConfiguration setUsername(String username) {
        System.out.println("username = " + username);
        return this;
    }

    public MyConfiguration setPassword(String password) {
        System.out.println("password = " + password);
        return this;
    }

    public static void main(String[] args) {
//        MyConfiguration configuration = new MyConfiguration()
//                .configure()
//                .setUsername("KATE")
//                .setPassword("WORK");
//
//
//        try (Session session =
//                     HibernateUtil.getSessionFactory().openSession()) {
//
//            System.out.println("Hibernate стартовал");
//        } finally {
//            HibernateUtil.shutdown();
//        }


//        RedisClient redisClient = RedisUtil.getConnection();
//
//        try (StatefulRedisConnection<String, String> connection =
//                     redisClient.connect()) {
//
//            RedisCommands<String, String> commands = connection.sync();
//
//            System.out.println(commands.ping());
//
//            commands.set("test:key", "hello redis");
//            System.out.println(commands.get("test:key"));
//        }
    }



    }
