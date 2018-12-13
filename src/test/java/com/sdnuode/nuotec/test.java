package com.sdnuode.nuotec;

/**
 * @ClassName test
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/29 10:26
 **/
public class test {
    public static void Main(){
        System.out.println("Main");
    }
    static String name;
    public static void main(String[] args){
//        test.Main();
        new test();
        System.out.println("main");
        String osName = System.getProperty("os.name");
        String userDir = System.getProperty("user.dir");
        String userName = System.getProperty("user.name");
        String userHome = System.getProperty("user.home");
        System.out.println("当前操作系统是：" + osName);
        System.out.println("用户的主目录：" + userHome);
        System.out.println("用户的当前工作目录：" + userDir);
        System.out.println("当前用户是：" + userName);
        System.out.println("name：" + name);
        if(name!=null&name.equals("")){
            System.out.println("ok");
        }else{
            System.out.println("erro");
        }
    }
}
