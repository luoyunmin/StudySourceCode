package com.luoyunmin.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class TimePlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.task("time-plugin") {
            doLast {
                println "this is gradle Time Plugin!"
            }
        }
    }
}