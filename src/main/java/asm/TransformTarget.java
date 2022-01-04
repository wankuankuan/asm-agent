package asm;

/**
 * Created with IntelliJ IDEA.
 * 简介
 *
 * @author wankuankuan
 * @date 2021/6/16
 */
public class TransformTarget {

    public static void main(String[] args) {
        while (true) {
            try {
                Thread.sleep(3000L);
            } catch (Exception e) {
                break;
            }
            printSomething();
        }
    }

    public static void printSomething() {
        System.out.println("hello");
    }
}
