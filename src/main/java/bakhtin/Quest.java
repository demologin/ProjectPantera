package bakhtin;

import bakhtin.Quest.Question.Answer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
public class Quest implements Serializable {

    @Setter
    Question startQuestion;
    String name;
    ConcurrentHashMap<Long, Question> questions;

    public Quest(String name) {
        this.name = name;
        questions = new ConcurrentHashMap<>();
    }

    public static QuestBuilder builder() {
        return new QuestBuilder();
    }


    public Question addNewQuestion(String question, List<Answer> answers) {
        Question q = new Question(question, answers);
        if (q.getId().equals(1L)) {
            startQuestion = q;
        }
        questions.put(q.getId(), q);
        return q;
    }

    public Question getQuestion(Long questionId) {
        return questions.get(questionId);
    }

    public static class QuestBuilder {

        private String name;
        private Question startQuestion;
        private final Map<Long, Question> questions = new ConcurrentHashMap<>();

        private QuestBuilder() {
        }

        public QuestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public QuestBuilder question(Question question) {
            if (question.getId() == 1L) {
                startQuestion = question;
            }
            questions.put(question.getId(), question);
            return this;
        }

        public QuestBuilder startQuestion(Question startQuestion) {
            this.startQuestion = startQuestion;
            questions.put(startQuestion.getId(), startQuestion);
            return this;
        }

        public Quest build() {
            Quest quest = new Quest(name);
            quest.setStartQuestion(startQuestion);
            quest.getQuestions().putAll(questions);
            return quest;
        }

    }


    @Getter
    public static class Question implements Serializable {

        private static final long serialVersionUID = 1L;
        private boolean finish;
        private boolean win;
        private static final AtomicLong idGenerator = new AtomicLong(0);
        String question;
        ConcurrentHashMap<Long, Answer> answers = new ConcurrentHashMap<>();
        Long id;

        public static QuestionBuilder builder() {
            return new QuestionBuilder();
        }

        public Question(boolean win, String question) {
            this.win = win;
            this.question = question;
            finish = true;
            id = idGenerator.incrementAndGet();
        }

        public Question(String question, List<Answer> answers) {
            this.question = question;
            id = idGenerator.incrementAndGet();
            for (Answer answer : answers) {
                this.answers.put(answer.id, answer);
            }
            finish = false;
        }

        public Answer getAnswer(Long answerId) {
            return answers.get(answerId);
        }

        public static class QuestionBuilder {

            private String question;
            private List<Answer> answers = new ArrayList<>();
            private boolean terminal;
            private boolean win;

            private QuestionBuilder() {
            }

            public QuestionBuilder question(String question) {
                this.question = question;
                return this;
            }

            public QuestionBuilder answer(Answer answer) {
                answers.add(answer);
                return this;
            }

            public QuestionBuilder win(boolean win) {
                terminal = true;
                this.win = win;
                return this;
            }

            public Question build() {
                if (terminal) {
                    return new Question(win, question);
                } else {
                    return new Question(question, answers);
                }
            }

        }

        @Getter
        public static class Answer implements Serializable {

            private static final long serialVersionUID = 1L;
            private static final AtomicLong idGenerator = new AtomicLong(0);

            private final Long id;
            private final String text;
            private @Setter
            Question nextQuestion;


            public static AnswerBuilder builder() {
                return new AnswerBuilder();
            }

            public Answer(String text) {
                this.text = text;
                this.nextQuestion = null;
                id = idGenerator.incrementAndGet();
            }

            public Answer(String text, Question nextQuestion) {
                this.text = text;
                this.nextQuestion = nextQuestion;
                id = idGenerator.incrementAndGet();
            }

            public static class AnswerBuilder {

                private String answer;
                private Question nextQuestion;

                public AnswerBuilder answer(String answer) {
                    this.answer = answer;
                    return this;
                }

                public AnswerBuilder nextQuestion(Question nextQuestion) {
                    this.nextQuestion = nextQuestion;
                    return this;
                }

                private AnswerBuilder() {
                }

                public Answer build() {
                    return new Answer(answer, nextQuestion);
                }

            }

        }

    }
}
