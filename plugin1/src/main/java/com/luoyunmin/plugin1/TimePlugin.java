package com.luoyunmin.plugin1;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class TimePlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.task("plugin1TimePlugin").doLast(new Action<Task>() {
            @Override
            public void execute(Task task) {
                System.out.println("this is plugin1TimePlugin!");
            }
        });
    }
}
