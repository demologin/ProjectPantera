package com.javarush.khmelov.repository;


import com.javarush.khmelov.cmd.Quest;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class QuestRepository {



    private final Map<Integer, Quest> questMap = new HashMap<>();



public Quest getById(int id){
    return questMap.get(id);
}

    public void crate(Quest quest){
        questMap.put(quest.getId(), quest);
    }

    public Collection<Quest> getAll() {
        return questMap.values();
    }






}
