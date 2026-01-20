package com.javarush.matsarskaya.cmd;

public class HomePage implements Command{
    @Override
    public String getView() {
        return "/WEB-INF/home-page.jsp";
    }
}
