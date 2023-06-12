package com.luoyunmin.plugin1

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File
import java.io.FileOutputStream

class TimeTransform : Transform() {
    override fun getName(): String = "TimeTransform"

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
        TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
        TransformManager.SCOPE_FULL_PROJECT

    override fun isIncremental(): Boolean = false

    override fun transform(
        context: Context?,
        inputs: MutableCollection<TransformInput>?,
        referencedInputs: MutableCollection<TransformInput>?,
        outputProvider: TransformOutputProvider?,
        isIncremental: Boolean
    ) {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
        if (!isIncremental) {
            outputProvider?.deleteAll()
        }
        inputs?.forEach { transforInput ->
            transforInput.directoryInputs.forEach { dirInput ->
                handleDirectoryInput(dirInput, outputProvider)
            }
            transforInput.jarInputs.forEach { jarInput ->
                handleJarInput(jarInput, outputProvider)
            }
        }
    }

    /**
     * 处理所有jar
     */
    fun handleJarInput(jarInput: JarInput, outputProvider: TransformOutputProvider?) {
        val dest = outputProvider?.getContentLocation(
            jarInput.name,
            jarInput.contentTypes,
            jarInput.scopes,
            Format.JAR
        )
        FileUtils.copyFile(jarInput.file, dest)
    }

    /**
     * 处理文件目录下的class文件
     */
    fun handleDirectoryInput(
        directoryInput: DirectoryInput,
        outputProvider: TransformOutputProvider?
    ) {
        //是否是目录
        if (directoryInput.file.isDirectory) {
            //列出目录所有文件（包含子文件夹，子文件夹内文件）
            directoryInput.file.listFiles()?.forEach { file ->
                val name = file.name
                if (filterClass(name)) {
                    val classReader = ClassReader(file.readBytes())
                    val classWriter = ClassWriter(
                        classReader,
                        ClassWriter.COMPUTE_MAXS
                    )
                    val classVisitor = TimeClassVisitor(
                        Opcodes.ASM9,
                        classWriter
                    )
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                    val code = classWriter.toByteArray()
                    val fos = FileOutputStream(file.parentFile.absolutePath + File.separator + name)
                    fos.write(code)
                    fos.close()
                }
            }
        }
        //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
        // 获取output目录
        val dest = outputProvider?.getContentLocation(
            directoryInput.name,
            directoryInput.contentTypes,
            directoryInput.scopes,
            Format.DIRECTORY
        )
        //这里执行字节码的注入，不操作字节码的话也要将输入路径拷贝到输出路径
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    /**
     * 检查class文件是否需要处理
     * @param fileName
     * @return
     */
    fun filterClass(name: String): Boolean {
//        return name.contains("TestASM")
        return (name.endsWith(".class")
                && !name.startsWith("R\$")
                && "R.class" != name
                && "BuildConfig.class" != name)
    }
}