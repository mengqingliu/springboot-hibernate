package com.sdnuode.nuotec.hibernate.Service;

/**
 * @ClassName ImgCheckService
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/29 12:25
 **/
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ImgCheckService {

//    private static final String KEY = "XXX:";
//
//    @Resource
//    private RedisTemplate<String, String> redisTemplate;
//
//    public Boolean checkImg(String id, String mX, String mY) {
//        String str = redisTemplate.opsForValue().get(KEY + MD5Utils.encrypt(id));
//        if (str == null) {
//            return false;
//        }
//        String[] split2 = str.split(":");
//        int x = Integer.parseInt(mX);
//        int y = Integer.parseInt(mY);
//        int x1 = Integer.parseInt(split2[0]);
//        int y1 = Integer.parseInt(split2[1]);
//        return x1 - 22 < x && x < x1 + 22 && y1 - 22 < y && y < y1 + 22;
//    }
//
//    public Boolean checkImg(String imgCheckArr) {
//        if (MyString.isEmpty(imgCheckArr)) {
//            return false;
//        }
//        String[] imgChecks = imgCheckArr.split(":");
//        if (imgChecks.length != 3) {
//            return false;
//        }
//        return checkImg(imgChecks[0], imgChecks[1], imgChecks[2]);
//    }
//
//    public void removeRedis(String imgCheckArr) {
//        if (!MyString.isEmpty(imgCheckArr)) {
//            String[] imgChecks = imgCheckArr.split(":");
//            if (imgChecks.length == 3) {
//                redisTemplate.delete(KEY + MD5Utils.encrypt(imgChecks[0]));
//            }
//        }
//    }
}

