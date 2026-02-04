package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.javarush.vasileva.service.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestServiceTest {

    @Mock
    private QuestRepository questRepository;

    @InjectMocks
    private QuestService questService;

    private Quest quest;

    @BeforeEach
    public void setUp() {
        quest = createValidQuest();
    }

    @Test
    @DisplayName("when getAll() then return all quests from repository")
    void whenGetAll_thenReturnAllQuestsFromRepository() {
        List<Quest> quests = createMultipleQuests();
        when(questRepository.getAll()).thenReturn(quests);

        List<Quest> result = questService.getAll();

        assertEquals(quests, result);
        verify(questRepository).getAll();
    }

    @Test
    @DisplayName("when findById() then return quest by ID")
    void whenFindById_ThenFindById() {
        long questId = quest.getId();
        when(questRepository.findById(questId)).thenReturn(Optional.of(quest));

        Optional<Quest> result = questService.findById(questId);

        assertTrue(result.isPresent());
        assertEquals(quest, result.get());
        verify(questRepository).findById(questId);
    }

    @Test
    @DisplayName("when findById() then return empty Optional if quest not found")
    void whenFindById_ThenNotFound() {
        long questId = NON_EXISTENT_QUEST_ID;
        when(questRepository.findById(questId)).thenReturn(Optional.empty());

        Optional<Quest> result = questService.findById(questId);

        assertFalse(result.isPresent());
        verify(questRepository).findById(questId);
    }

    @Test
    @DisplayName("when create() then delegate to repository")
    void testCreate() {
        questService.create(quest);
        verify(questRepository).create(quest);
    }

    @Test
    @DisplayName("when update() than delegate to repository")
    void testUpdate() {
        questService.update(quest);
        verify(questRepository).update(quest);
    }

    @Test
    @DisplayName("when delete() then delegate to repository")
    void testDelete() {
        questService.delete(quest);
        verify(questRepository).delete(quest);
    }

    @Test
    @DisplayName("when getValidatedQuest() then return empty when questIdStr is null")
    void givenQuestIdNull_whenGetValidatedQuest_ThenReturnEmpty() {
        Optional<Quest> result = questService.getValidatedQuest(NULL_QUEST_ID_STR);
        assertFalse(result.isPresent());
        verify(questRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("when getValidatedQuest() then return empty when questIdStr is empty")
    void givenEmptyQuestId_WhenGetValidatedQuest_ThenReturnEmpty() {
        Optional<Quest> result = questService.getValidatedQuest(EMPTY_QUEST_ID_STR);
        assertFalse(result.isPresent());
        verify(questRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("when getValidatedQuest() then parse string to long and find quest")
    void whenGetValidatedQuest_ThenReturnQuest() {
        String questIdStr = String.valueOf(quest.getId());
        long questId = Long.parseLong(questIdStr);

        when(questRepository.findById(questId)).thenReturn(Optional.of(quest));

        Optional<Quest> result = questService.getValidatedQuest(questIdStr);

        assertTrue(result.isPresent());
        assertEquals(quest, result.get());
        verify(questRepository).findById(questId);
    }

    @Test
    @DisplayName("when getValidatedQuest() then return empty if quest not found by parsed ID")
    void whenGetValidatedQuest_thenQuestNotFound() {
        String questIdStr = NON_EXISTENT_QUEST_ID_STR;
        long questId = Long.parseLong(questIdStr);

        when(questRepository.findById(questId)).thenReturn(Optional.empty());

        Optional<Quest> result = questService.getValidatedQuest(questIdStr);

        assertFalse(result.isPresent());
        verify(questRepository).findById(questId);
    }
}
