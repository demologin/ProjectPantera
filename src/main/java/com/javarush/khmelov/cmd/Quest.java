package com.javarush.khmelov.cmd;

import com.javarush.khmelov.quest.Answer;
import com.javarush.khmelov.quest.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Quest {
   static AtomicInteger atomicInteger = new AtomicInteger(0);
   @Setter
   @Getter
   String win;
    @Getter
    int id = atomicInteger.getAndIncrement();
    @Getter
    String name;

    public Quest(String name){
        this.name = name;
    }

    Map<Integer, Question> questions = new HashMap<>();
    Map<Integer, String> loseMap = new HashMap<>();
    public Question  getQuestionById(int i){
        return questions.get(i);
    }
    public void crete(int i,String first, String second, String third, String lose){
        questions.put(i,
                new Question(
                        i,
                        first,
                        List.of(new Answer(second,1),
                                new Answer(third,2))

                ));
        loseMap.put(i,lose);
    }

    public String lose(int id){
        return loseMap.get(id);
    }
}
