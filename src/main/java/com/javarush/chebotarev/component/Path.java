package com.javarush.chebotarev.component;

public interface Path {

    String WEB_INF = "/WEB-INF";
    String JSP = ".jsp";
    String MAIN_MENU = WEB_INF + Go.MAIN_MENU + JSP;
    String EDITOR = WEB_INF + Go.EDITOR + JSP;
    String NEW_QUEST = WEB_INF + Go.NEW_QUEST + JSP;
    String QUEST = WEB_INF + "/quest" + JSP;
    String RESULT = WEB_INF + "/result" + JSP;
}
