package com.sdnuode.nuotec;

/**
 * @ClassName Father
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/29 9:50
 **/
public class Father {
    private int sons;
    private String wifeName;
    private int age;
    private static String know = "我哪知道";

    public static void isOwn(){
        System.out.println("执行了isOwn静态函数" + "-----它有几个儿子？" + "----" + know);
    }

    public void howOld(){
        System.out.println("执行了howOld函数" + "-----它多大了？" + "----" + this.age);
    }

    public Father(){
        System.out.println("执行了Father构造函数" + "-----此人身份可疑" + "-----它今年" + this.age + "岁，它老婆是" + this.wifeName + "，它们有" + this.sons + "个儿子");
    }

    public Father(int sons, String wifeName, int age){
        this.age = age;
        this.sons = sons;
        this.wifeName = wifeName;
        System.out.println("执行了Father构造函数" + "-----它今年" + this.age + "岁，它老婆是" + this.wifeName + "，它们有" + this.sons + "个儿子");
    }

    static{
        System.out.println("执行了static静态函数" + "-----它老婆是谁？" + "----" + know);
    }
}
