package com.luoyunmin.plugin1

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class TimeMethodVisitor(
    api: Int,
    val methodVisitor: MethodVisitor?,
    access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    private var startTime: Int = 0
    private var endTime: Int = 0
//    private var inject: Boolean = false

    override fun onMethodEnter() {
        super.onMethodEnter()
        println("onMethodEnter")
        // 调用System.currentTimeMillis()方法
        invokeStatic(Type.getType("Ljava/lang/System;"), Method("currentTimeMillis", "()J"))
        // newlocal创建一个本地变量，
        startTime = newLocal(Type.LONG_TYPE)
        // 上一步的执行结果赋值给startTime
        storeLocal(startTime)
//        methodVisitor?.visitMethodInsn(
//            INVOKESTATIC,
//            "java/lang/System",
//            "currentTimeMillis",
//            "()J",
//            false
//        )
//        val startTime = System.currentTimeMillis()
    }

//    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
//        // 可以查看哪个方法里面有注解
//        println("method name: $name, annotation: $descriptor")
//        if ("Lcom/eluoyunmin/studysourcecode/ASMTest;".equals(descriptor)) {
//            inject = true
//        }
//        return super.visitAnnotation(descriptor, visible)
//    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        println("onMethodExit")
        // 调用System.currentTimeMillis()方法
        invokeStatic(Type.getType("Ljava/lang/System;"), Method("currentTimeMillis", "()J"))
        // newlocal创建一个本地变量，
        endTime = newLocal(Type.LONG_TYPE)
        // 上一步的执行结果赋值给endTime
        storeLocal(endTime)
//        val endTime = System.currentTimeMillis()
//        Log.i("luoyunmin", "method execute : ${startTime - endTime}")
        // 调用
        val stringBuilderType = Type.getType("Ljava/lang/StringBuilder;")
        getStatic(
            Type.getType("Ljava/lang/System;"),
            "out",
            Type.getType("Ljava/io/PrintStream;")
        )
        newInstance(stringBuilderType)
        dup()
        invokeConstructor(stringBuilderType, Method("<init>", "()V"))
        // 调用StringBuilder对象的append方法，传入参数"method execute"
        visitLdcInsn("method execute:")
        invokeVirtual(
            stringBuilderType,
            Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;")
        )
        // 加载startTime和endTime，然后调用减法
        loadLocal(endTime)
        loadLocal(startTime)
        // SUB是指减法
        math(SUB, Type.LONG_TYPE)
        // // 调用StringBuilder对象的append方法，传入参数" ms"
        invokeVirtual(stringBuilderType, Method("append", "(J)Ljava/lang/StringBuilder;"))
        visitLdcInsn(" ms")
        invokeVirtual(
            stringBuilderType,
            Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;")
        )
        // 调用toString方法
        invokeVirtual(stringBuilderType, Method("toString", "()Ljava/lang/String;"))
        // 调用System.out.println()
        invokeVirtual(
            Type.getType("Ljava/io/PrintStream;"),
            Method("println", "(Ljava/lang/String;)V")
        )
    }
}