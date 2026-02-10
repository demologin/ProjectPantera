package com.javarush.goncharov.service;

import com.javarush.goncharov.model.*;
import com.javarush.goncharov.repository.*;
import org.eclipse.tags.shaded.org.apache.bcel.generic.ARETURN;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class QuestService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;
    private final Repository<Quest> questRepository;

    public QuestService(AnswerRepository answerRepository, QuestionService questionService,
                        UserService userService,
                        Repository<Quest> questRepository) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.userService = userService;
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
            Optional<Question> newQuestion = extracted(quest, symbolQuestion, text, question, idQuestion);
            if (newQuestion.isPresent()){
                question = newQuestion.get();
                question.setQuestId(quest.getId());
                question.setQuestName(quest.getName());
                questionService.post(question);
                question.setId(idQuestion);
                questionService.update(question);
                quest.getQuestions().add(question);
                if (question.getId().equals(1L)){
                    quest.setStartQuestionId(question.getId());
                }
            }
        }
        questRepository.update(quest);
    }

    private Optional<Question> extracted(Quest quest, String symbolQuestion, String text,
                                         Question question, Long idQuestion) {
        question = switch (symbolQuestion) {
            case ":" -> Question.builder()
                    .text(text)
                    .gameState(GameState.PLAY)
                    .build();
            case "+" -> Question.builder()
                    .text(text)
                    .gameState(GameState.WIN)
                    .build();
            case "-" -> Question.builder()
                    .text(text)
                    .gameState(GameState.LOSE)
                    .build();
            case "<" -> {
                Answer answer = Answer.builder()
                        .questionId(question.getId())
                        .nextQuestionId(idQuestion)
                        .text(text)
                        .questName(quest.getName())
                        .build();
                answerRepository.create(answer);
                question.getAnswers().add(answer);
                yield  null;
            }
            default -> throw new RuntimeException("incorrect parsing");
        };
        return Optional.ofNullable(question);
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
