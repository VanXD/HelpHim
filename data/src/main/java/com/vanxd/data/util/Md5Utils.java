package com.vanxd.data.util;

import java.security.MessageDigest;

public class Md5Utils {

    private static byte[] md5(String s) throws Exception{
        MessageDigest algorithm = MessageDigest.getInstance("MD5");
        algorithm.reset();
        algorithm.update(s.getBytes("UTF-8"));
        byte[] messageDigest = algorithm.digest();
        return messageDigest;
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) throws Exception{
        return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
    }


}
