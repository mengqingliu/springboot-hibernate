package com.sdnuode.nuotec;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FatherTest
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/29 10:04
 **/
public class FatherTest {
    public static void main (String[] args){
        Father father2 = new Father();
        Father father0 = new Father(3, "小深深儿", 23);
        Father father1 = new Father(2, "小深深", 22);
        Father father3 = new Father();
        List<Father> list = new ArrayList<Father>();
        list.add(father0);
        list.add(father1);
        list.add(father2);
        list.add(father3);
        for (Father fa: list) {
            fa.howOld();//不会重新new一个fa
        }
    }
}
