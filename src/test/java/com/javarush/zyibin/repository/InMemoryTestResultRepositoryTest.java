package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTestResultRepositoryTest {

    private InMemoryTestResultRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTestResultRepository();
    }

    private TestResult createResult(long userId) {
        return new TestResult(
                userId,
                "java-core",
                10,
                7,
                true,
                LocalDateTime.now()
        );
    }

    @Test
    void shouldAssignIdWhenSavingResultWithoutId() {

        TestResult result = createResult(1L);

        repository.save(result);

        assertTrue(result.getId() > 0);
    }

    @Test
    void shouldNotOverrideExistingIdWhenSaving() {

        TestResult result = createResult(1L);
        result.setId(42L);

        repository.save(result);

        assertEquals(42L, result.getId());
    }

    @Test
    void shouldFindResultsByUserId() {

        TestResult user1Result1 = createResult(1L);
        TestResult user1Result2 = createResult(1L);
        TestResult user2Result = createResult(2L);

        repository.save(user1Result1);
        repository.save(user1Result2);
        repository.save(user2Result);

        List<TestResult> results = repository.findByUserId(1L);

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(r -> r.getUserId() == 1L));
    }

    @Test
    void shouldReturnEmptyListWhenNoResultsForUser() {

        repository.save(createResult(2L));

        List<TestResult> results = repository.findByUserId(1L);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void shouldReturnAllSavedResults() {

        repository.save(createResult(1L));
        repository.save(createResult(2L));
        repository.save(createResult(3L));

        List<TestResult> allResults = repository.findAll();

        assertEquals(3, allResults.size());
    }

}
