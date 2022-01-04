package asm;

import jdk.internal.org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Created with IntelliJ IDEA.
 * 转换类，实现instrument 的 ClassFileTransformer 接口定义一个类文件转换器，它唯一的一个 transform() 方法会在类文件被加载时调用
 *
 * @author wankuankuan
 * @date 2021/6/16
 */
public class TestTransformer implements ClassFileTransformer {

    /**
     * 可以对传入的二进制字节码进行改写或替换，生成新的字节码数组后返回，JVM 会使用 transform 方法返回的字节码数据进行类的加载
     *
     * @param loader              要转换的类的定义加载器，如果引导加载器可能为空
     * @param className           在 Java 虚拟机规范中定义的完全限定类和接口名称的内部形式的类名称。 例如，“java/util/List”。
     * @param classBeingRedefined 如果这是由重新定义或重新转换触发的，则该类被重新定义或重新转换； 如果这是一个类加载，则为空
     * @param protectionDomain    正在定义或重新定义的类的保护域
     * @param classfileBuffer     类文件格式的输入字节缓冲区——不得修改
     * @return 格式良好的类文件缓冲区（转换的结果），如果未执行转换，则为 null。
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        System.out.println("Transforming " + className);
        // 使用 asm 框架中的 api 进行转换
        // ClassReader 是 asm 代码的入口，通过它解析二进制字节码
        ClassReader reader = new ClassReader(classfileBuffer);
        // ClassWriter 接口继承了 ClassVisitor 接口
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        // 在实例化类访问器时，将 ClassWriter “注入” 到里面，以实现对类写入的声明
        ClassVisitor classVisitor = new TestClassVisitor(Opcodes.ASM5, classWriter);
        // 使给定访问者访问此 ClassReader 的 Java 类。 此类是在构造函数中指定的类（请参阅 ClassReader）。
        reader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        return classWriter.toByteArray();
    }

    class TestClassVisitor extends ClassVisitor implements Opcodes {

        TestClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        // 定义对类结构（如方法、字段、注解）的访问方法。
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("printSomething")) {
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitLineNumber(19, l0);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("bytecode replaced!");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitLineNumber(20, l1);
                mv.visitInsn(Opcodes.RETURN);
                mv.visitMaxs(2, 0);
                mv.visitEnd();
                TransformTarget.printSomething();
            }
            return mv;
        }
    }
}
