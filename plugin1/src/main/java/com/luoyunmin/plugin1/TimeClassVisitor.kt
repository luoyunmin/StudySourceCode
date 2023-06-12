package com.luoyunmin.plugin1

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

class TimeClassVisitor(api: Int, classVisitor: ClassVisitor) : ClassVisitor(api, classVisitor) {

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        println("method name:$name")

        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return TimeMethodVisitor(
            api = api,
            methodVisitor = methodVisitor,
            access = access,
            name = name,
            descriptor = descriptor
        )
    }
}