package me.wangcai.gypayserver.utils;

import java.security.MessageDigest;

/**
 * md5加密类;
 */
public class MD5Utils {

    /**
     * md5加密方法;
     * @param str 加密对象
     * @return
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//创建MessageDigest对象;
            md.update(str.getBytes()); //使用指定的 byte数组更新数据
            byte b[] = md.digest();//进行加密;

            int i;

            StringBuffer buf = new StringBuffer("");//创建StringBuffer对象;
            for (int offset = 0; offset < b.length; offset++) {//遍历数组;
                i = b[offset];//赋值;
                if (i < 0)//判断i小于0'
                    i += 256;
                if (i < 16)//判断i小于16
                    buf.append("0");//字符串拼接;
                buf.append(Integer.toHexString(i));//字符串拼接;
            }
            str = buf.toString();//转换成字符串;
        } catch (Exception e) {//异常处理;
            e.printStackTrace();

        }
        return str;
    }
}