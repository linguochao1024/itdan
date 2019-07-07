package util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES加解密工具类
 *
 * @author linguochao
 * @date 2019\7\7 0007
 */
public class AESUtils {

    public static final String IV = "1903121747010498";
    /**
     * 加解秘钥
     */
    public static String KEY = "c6d55cc5b1a54e19b49245f8549700d5";

    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            //"算法/模式/补码方式"NoPadding PkcsPadding
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));

            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,"utf-8");
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV).trim();
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV).trim();
    }

    /**
     * 测试
     */
    public static void main(String [] args) throws Exception {

        String test1 = "{\"pageNum\":\"1\", \"pageSize\":\"15\", \"preId\":\"0\"}";
//        String test1 = "{pageNum:1,pageSize:15,preId:0}";

        String test = new String(test1.getBytes(),"UTF-8");
        String data = null;
        String key = KEY;
        String iv = IV;
//        // /g2wzfqvMOeazgtsUVbq1kmJawROa6mcRAzwG1/GeJ4=

        //随机字符串
        String nonce = "123456123";
        //加密字符串
        String signString = test + nonce + key;
        //获取签名
        String sign = Md5Utils.md5(signString).toUpperCase();
        System.out.println("------------sign:" + sign + "----------");
        data = encrypt(test, key, iv);
        System.out.println("数据："+test);
        System.out.println("加密："+data);

        String jiemi = desEncrypt(data, key, iv);
        System.out.println("解密："+jiemi);


    }
}
