package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Answer;
import com.javarush.goncharov.model.Quest;
import com.javarush.goncharov.model.Question;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.*;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class QuestService {
    private final Storage storage = Storage.getInstance();
    private final UserService userService = new UserService(new UserRepository(storage));
    private final QuestionService questionService = new QuestionService(new QuestionRepository(storage));
    private final AnswerRepository answerRepository = new AnswerRepository(storage);
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
        getQuestions(quest);
        Optional<User> userFind = userService.get(idAuthor);
        Collection<Quest> quests = userFind.get().getQuests();
        quests.add(quest);
        getQuestions(quest);
        questRepository.update(quest);
        System.out.println(questionService.getAll());
    }

    private void getQuestions(Quest quest) {
        Matcher matcher = getMatcher(quest);
        Question question = new Question();
        while (matcher.find()) {
            Long idQuestion = Long.parseLong(matcher.group(1));
            String symbolQuestion = matcher.group(2);
            String text = matcher.group(3);
            if (symbolQuestion.equals(":") ||
                    symbolQuestion.equals("+") ||
                    symbolQuestion.equals("-")){
                question = Question.builder()
                        .questId(quest.getId())
                        .questName(quest.getName())
                        .text(text)
                        .build();
                questionService.post(question);
                question.setId(idQuestion);
                questionService.update(question);
                quest.getQuestions().add(question);
                if (question.getId().equals(1L)){
                    quest.setStartQuestionId(question.getId());
                }
            }
            if (symbolQuestion.equals("<")){
                Answer answer = Answer.builder()
                        .questionId(question.getId())
                        .nextQuestionId(idQuestion)
                        .text(text)
                        .questName(quest.getName())
                        .build();
                answerRepository.create(answer);
                question.getAnswers().add(answer);
            }
        }
        questRepository.update(quest);
    }

    private static Matcher getMatcher(Quest quest) {
        String patternQ = "(\\d+)\\s*([:<\\-+])\\s*(.*)";
        Pattern pattern = Pattern.compile(patternQ);
        return pattern.matcher(quest.getText());
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
