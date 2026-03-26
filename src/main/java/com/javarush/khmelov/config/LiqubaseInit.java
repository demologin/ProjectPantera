package com.javarush.khmelov.config;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiqubaseInit {
    public void init() {
        log.info("Running Liquibase...");
        try {
            Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
                CommandScope update = new CommandScope("update");

                update.addArgumentValue("changelogFile", "db/changelog.xml");
                update.addArgumentValue("url", "jdbc:postgresql://localhost:5432/game");
                update.addArgumentValue("username", "postgres");
                update.addArgumentValue("password", "postgres");

                update.execute();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Running Liquibase...DONE");
    }
}
