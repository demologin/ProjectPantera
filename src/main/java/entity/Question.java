package entity;

import java.util.List;
import java.util.Objects;

public class Question {
    String id;
    String question;
    int quantityAnswers;
    List<String> answersId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswersId() {
        return answersId;
    }

    public void setAnswersId(List<String> answersId) {
        this.answersId = answersId;
    }

    public int getQuantityAnswers() {
        return quantityAnswers;
    }

    public void setQuantityAnswers(int quantityAnswers) {
        this.quantityAnswers = quantityAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        for (int i = 0; i < question1.getAnswersId().size(); i++) {
            try {
                if (!this.getAnswersId().get(i).equals(question1.getAnswersId().get(i))) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return quantityAnswers == question1.quantityAnswers && Objects.equals(id, question1.id)
                && Objects.equals(question, question1.question) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, quantityAnswers, answersId);
    }
}
