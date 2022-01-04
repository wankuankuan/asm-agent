package asm;

import java.lang.instrument.Instrumentation;

/**
 * Created with IntelliJ IDEA.
 * agent
 *
 * @author wankuankuan
 * @date 2021/6/16
 */
public class TestAgent {

    /**
     * attach 之后执行入口
     *
     * @param args 参数
     * @param inst inst
     */
    public static void agentmain(String args, Instrumentation inst) {
        // 添加转换器
        inst.addTransformer(new TestTransformer(), true);
        try {
            // 实现对目标类的重定义
            inst.retransformClasses(TransformTarget.class);
            System.out.println("Agent Load Done.");
        } catch (Exception e) {
            System.out.println("agent load failed!");
        }
    }

}
