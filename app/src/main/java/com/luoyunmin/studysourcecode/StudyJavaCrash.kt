package com.luoyunmin.studysourcecode

import java.lang.Thread.UncaughtExceptionHandler

object StudyJavaCrash : UncaughtExceptionHandler {

    private val defaultExceptionHandler: UncaughtExceptionHandler by lazy {
        Thread.getDefaultUncaughtExceptionHandler()
    }

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    fun performJavaCrash() {
        throw IndexOutOfBoundsException("test crash")
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
//        defaultExceptionHandler.uncaughtException(t, e)
    }
}