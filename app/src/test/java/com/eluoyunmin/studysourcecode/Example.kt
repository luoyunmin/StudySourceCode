package com.eluoyunmin.studysourcecode

import org.junit.Test
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class Example {

    @Test
    fun test() {
        val clzInFile = File("./src/test/java/com/eluoyunmin/studysourcecode/Printer.class")
        val clzInput = FileInputStream(clzInFile)
        val clzReader = ClassReader(clzInput)
        val clzWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES)
        clzReader.accept(TimeClassVisitor(Opcodes.ASM9, clzWriter), ClassReader.EXPAND_FRAMES)
        val clzOutFile = File("./src/test/java2/com/eluoyunmin/studysourcecode/Printer.class")
        if (!clzOutFile.parentFile.exists()){
            clzOutFile.parentFile.mkdirs()
        }

        if (!clzOutFile.exists()) {
            clzOutFile.createNewFile()
        }
        val clzOutput = FileOutputStream(clzOutFile)
        clzOutput.write(clzWriter.toByteArray())
        clzInput.close()
        clzOutput.close()
    }
}