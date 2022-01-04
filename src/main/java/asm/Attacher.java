package asm;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * 简介
 *
 * @author wankuankuan
 * @date 2021/6/16
 */
public class Attacher {
    public static void main(String[] args) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException {
        // 目标 JVM pid
        VirtualMachine vm = VirtualMachine.attach("");
        vm.loadAgent("D:\\code\\idea-workspace\\github\\asm-agent\\target\\asm-agent-1.0.jar");
    }
}
