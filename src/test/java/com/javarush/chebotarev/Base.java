package com.javarush.chebotarev;

import com.javarush.chebotarev.component.ObjectRepository;
import com.javarush.chebotarev.component.QuestService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Base {

    protected final QuestService questService = ObjectRepository.find(QuestService.class);

    protected InputStream createInputStream() {
        String quest =
                """
                        {
                          "title" : "Title",
                          "prologue" : "About",
                          "nodes" : [ {
                            "id" : 1,
                            "text" : "Text.",
                            "type" : "common",
                            "options" : [ {
                              "id" : 0,
                              "text" : "Action1",
                              "nextNodeId" : 2
                            }, {
                              "id" : 1,
                              "text" : "Action2",
                              "nextNodeId" : 3
                            }, {
                              "id" : 2,
                              "text" : "Action3",
                              "nextNodeId" : 4
                            } ],
                            "victory" : false,
                            "commonType" : true
                          }, {
                            "id" : 2,
                            "text" : "Text.",
                            "type" : "victory",
                            "options" : [ ],
                            "victory" : true,
                            "commonType" : false
                          }, {
                            "id" : 3,
                            "text" : "Text.",
                            "type" : "defeat",
                            "options" : [ ],
                            "victory" : false,
                            "commonType" : false
                          }, {
                            "id" : 4,
                            "text" : "Text.",
                            "type" : "common",
                            "options" : [ {
                              "id" : 0,
                              "text" : "Action1",
                              "nextNodeId" : 1
                            } ],
                            "victory" : false,
                            "commonType" : true
                          } ]
                        }
                        """;
        return new ByteArrayInputStream(quest.getBytes(StandardCharsets.UTF_8));
    }
}
