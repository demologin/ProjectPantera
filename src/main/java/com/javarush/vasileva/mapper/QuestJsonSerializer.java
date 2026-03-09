package com.javarush.vasileva.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.javarush.vasileva.entity.Answer;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.Question;

import java.io.IOException;

public class QuestJsonSerializer extends JsonSerializer<Quest> {
    @Override
    public void serialize(Quest quest, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        // Базовые поля
        gen.writeNumberField("id", quest.getId());
        gen.writeStringField("title", quest.getTitle());
        gen.writeStringField("description", quest.getDescription());
        gen.writeStringField("text", quest.getText());
        gen.writeStringField("image", quest.getImage());
        gen.writeNumberField("startQuestionId", quest.getStartQuestionId());

        // Сериализуем вопросы
        gen.writeArrayFieldStart("questions");
        if (quest.getQuestions() != null) {
            for (Question question : quest.getQuestions()) {
                serializeQuestion(question, gen);
            }
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }

    private void serializeQuestion(Question question, JsonGenerator gen) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("label", question.getLabel());
        gen.writeStringField("text", question.getText());

        // Сериализуем ответы
        gen.writeArrayFieldStart("answers");
        if (question.getAnswers() != null) {
            for (Answer answer : question.getAnswers()) {
                serializeAnswer(answer, gen);
            }
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

    private void serializeAnswer(Answer answer, JsonGenerator gen) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("nextQuestionLabel", answer.getNextQuestionLabel());
        gen.writeStringField("text", answer.getText());
        gen.writeEndObject();
    }
}
