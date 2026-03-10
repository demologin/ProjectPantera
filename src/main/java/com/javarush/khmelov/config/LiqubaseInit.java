package com.javarush.khmelov.config;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class LiqubaseInit {
    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    public void init() {
        if (INITIALIZED.compareAndSet(false, true)) {
            System.out.println("Running Liquibase...");
            try {
                Scope.child(Map.of(Scope.Attr.resourceAccessor.name(), new ClassLoaderResourceAccessor()), () -> {
                    new CommandScope("update")
                            .addArgumentValue("changelogFile", "db/changelog.xml")
                            .addArgumentValue("url", "jdbc:postgresql://localhost:5432/game")
                            .addArgumentValue("username", "postgres")
                            .addArgumentValue("password", "postgres")
                            .execute();
                });
                System.out.println("Running Liquibase...DONE");
            } catch (Exception e) {
                INITIALIZED.set(false);
                e.printStackTrace(System.err);
            }
        }
    }
}