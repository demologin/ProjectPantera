package services;

import entity.Answer;
import entity.Question;
import entity.Result;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepositoryService {
    Repository repository;
    List<Question> questions;
    List<Answer> answers;
    String intro;


    public RepositoryService(Repository repository) {
        questions = fillQuestionsFromRepo(repository.getRecords());
        answers = fillAnswersFromRepo(repository.getRecords());
    }

    List<Question> fillQuestionsFromRepo(List<String[]> repoList) {
        questions = new ArrayList<>();
        for (String[] record : repoList) {
            if (record[0].equals("0")) {
                intro = record[1];
                continue;
            }
            if (record.length != 3 && record.length != 4) {
                throw new IllegalArgumentException("invalid data: small length record");
            }
            if (record.length == 3 && record[0].length() < 3 && !record[0].isEmpty()) {
                String id = record[0];
                Question question = new Question();
                question.setId(id);
                question.setQuestion(record[1]);
                try {
                    question.setQuantityAnswers(Integer.parseInt(record[2]));
                } catch (NumberFormatException e) {
                    System.out.println("invalid quantity answer: " + record[2]);
                    throw new NumberFormatException(e.getMessage());
                }
                List<String> answers = new ArrayList<>();
                question.setAnswersId(answers);
                for (int i = 0; i < question.getQuantityAnswers(); i++) {
                    answers.add(id + "0" + (i + 1));
                }
                questions.add(question);
            }
        }
        return questions;
    }

    List<Answer> fillAnswersFromRepo(List<String[]> repoList) {
        answers = new ArrayList<>();
        for (String[] record : repoList) {
            if (record.length != 3 && record.length != 4) {
                throw new IllegalArgumentException("invalid data: small length record");
            }
            if (record.length == 3) {
                if (record[0].length() >= 3) {
                    Answer answer = new Answer();
                    answer.setId(record[0]);
                    answer.setAnswer(record[1]);
                    answer.setResult(Result.NEXT_QUESTION);
                    answer.setIdQuestion(record[2]);
                    answers.add(answer);
                }
            } else {
                Answer answer = new Answer();
                answer.setId(record[0]);
                answer.setAnswer(record[1]);
                answer.setResult(getResult(record[2]));
                answer.setResultText(record[3]);
                answers.add(answer);
            }
        }
        return answers;
    }

    Result getResult(String text) {
        return switch (text) {
            case "lose" -> Result.LOSE;
            case "win" -> Result.WIN;
            default -> throw new IllegalArgumentException("invalid result answer: " + text);
        };
    }

    public Question getQuestionById(String id) {
        return questions.stream().filter(q -> q.getId().equals(id)).findFirst().orElse(null);
    }

    public Answer getAnswerById(String id) {
        return answers.stream().filter(q -> q.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryService that = (RepositoryService) o;
        return Objects.equals(repository, that.repository) && that.getQuestions().equals(this.getQuestions()) && that.getAnswers().equals(this.getAnswers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(repository, questions, answers);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getIntro() {
        return intro;
    }

}
