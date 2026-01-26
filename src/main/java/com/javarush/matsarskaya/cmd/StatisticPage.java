package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.service.StatisticService;
import jakarta.servlet.http.HttpServletRequest;

public class StatisticPage implements Command{
    private final StatisticService statisticService;

    public StatisticPage(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        Statistic statistic = statisticService.getStatistic(username);
        request.setAttribute("statistic", statistic);
        return getView();
    }

    @Override
    public String getView() {
        return "/WEB-INF/statistic-page.jsp";
    }
}
