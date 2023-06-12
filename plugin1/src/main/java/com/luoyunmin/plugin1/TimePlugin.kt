package com.luoyunmin.plugin1

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TimePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("this is TimePlugin!")
        val appExtension = project.extensions.findByType(AppExtension::class.java)
        appExtension?.registerTransform(TimeTransform())
    }
}