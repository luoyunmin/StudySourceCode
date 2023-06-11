package com.eluoyunmin.studysourcecode;

import com.eluoyunmin.studysourcecode.ASMTest;

public class Printer {
    public static void main(String[] args) {
        b();
    }

    @ASMTest
    private static void b() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
