package entity;

import java.util.Objects;

public class Answer {
    String id;
    String answer;
    String idQuestion;
    Result result;
    String resultText;

    public Answer(String id) {
        this.id = id;
    }

    public Answer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return Objects.equals(id, answer1.id) && Objects.equals(answer, answer1.answer)
                && Objects.equals(idQuestion, answer1.idQuestion) && result == answer1.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer, idQuestion, result);
    }
}
