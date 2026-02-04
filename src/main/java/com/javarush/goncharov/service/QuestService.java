package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Quest;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.QuestRepository;
import com.javarush.goncharov.repository.Repository;
import com.javarush.goncharov.repository.Storage;
import com.javarush.goncharov.repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;


public class QuestService {
    private final Storage storage = Storage.getInstance();
    private final UserService userService = new UserService(new UserRepository(storage));
//    private final QuestService questService = new QuestService(new QuestRepository(storage));

    private final Repository<Quest> questRepository;

    public QuestService(Repository<Quest> questRepository) {
        this.questRepository = questRepository;
    }

    public Optional<Quest> get(Long id){
        return questRepository.get(id);
    }

    public Optional<Quest> find(String name, String authorName){
        return questRepository.findBy(name, authorName);
    }

    public void create(String name, String text, Long idAuthor, String nameAuthor){
        Quest quest = Quest.builder()
                .name(name)
                .authorName(nameAuthor)
                .text(text)
                .developerId(idAuthor)
                .startQuestionId(0L)
                .build();
        questRepository.create(quest);
        Optional<User> userFind = userService.get(idAuthor);
        Collection<Quest> quests = userFind.get().getQuests();
        quests.add(quest);
        questRepository.update(quest);
        System.out.println(userFind.get().getQuests());
    }

    public void delete(Quest quest){
        questRepository.delete(quest);
    }

    public void update(Quest quest){
        questRepository.update(quest);
    }

    public Map<Long, Quest> getAll(){
        return questRepository.getAll();
    }
}
