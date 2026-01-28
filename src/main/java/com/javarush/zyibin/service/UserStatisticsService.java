package com.javarush.zyibin.service;

import com.javarush.zyibin.dto.UserTopicStats;
import com.javarush.zyibin.model.TestResult;

import java.util.List;

public interface UserStatisticsService {

    List<UserTopicStats> calculateUserTopicStats(List<TestResult> results);
}
