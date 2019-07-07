package util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * description
 *
 * @author linguochao
 * @date 2019\7\7 0007
 */
public class Md5Utils {
    public static final String ENCRYPT_MODE_MD5 = "MD5";

    public static final String ENCRYPT_MODE_SHA1 = "SHA1";

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    protected static final String ENCRYPT_DEFAULT_CHARSET = "utf-8";

    public static final String [] HEX_DIGITS = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static final String AES_KEY = "0376b773d85c49bdad3521296b672c06";

    public static String MD5Encode(String origin){
        return md5(origin);
    }

    /**
     * 算法
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";


    public static String byteArrayToHexString(byte []b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public static String md5(String origin, String charsetName) {
        return encrypt(origin, charsetName, ENCRYPT_MODE_MD5);
    }

    public static String md5Encode(String origin){
        return md5(origin);
    }

    public static String md5Encode(String origin, String charsetName){
        return md5(origin, charsetName);
    }


    public static String encrypt(String origin, String charsetName, String mode){
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance(mode);
            if (charsetName == null || "".equals(charsetName)){
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            } else{
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetName)));
            }
        } catch (Exception exception) {
        }
        return resultString;
    }


    public static String sha1(String origin, String charsetName){
        return encrypt(origin, charsetName, ENCRYPT_MODE_SHA1);
    }

    public static String sha1(String origin){
        return sha1(origin, ENCRYPT_DEFAULT_CHARSET);
    }

    public static String md5(String origin){
        return md5(origin, ENCRYPT_DEFAULT_CHARSET);
    }

    public static String signature(String secret, String nonce, String timeStamp, String encryptMode){
        if (StringUtils.isEmpty(secret)) {
            throw new IllegalArgumentException("Secret can not be null");
        }

        if (StringUtils.isEmpty(nonce)) {
            throw new IllegalArgumentException("Nonce can not be null");
        }

        if (StringUtils.isEmpty(timeStamp)) {
            throw new IllegalArgumentException("Timestamp can not be null");
        }

        String[] array = new String[]{secret, nonce, timeStamp};
        Arrays.sort(array);
        StringBuffer buffer = new StringBuffer();
        for (String str : array) {
            buffer.append(str);
        }
        return encrypt(buffer.toString(), ENCRYPT_DEFAULT_CHARSET, encryptMode);
    }

    /**
     * HmacSha1方式加密
     * @param source
     * @param key
     * @return
     */
    public static String hmacSha1(String source, String key) {
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signatureKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            //用给定密钥初始化 Mac 对象
            mac.init(signatureKey);
            //完成 Mac 操作
            byte[] rawHmac = mac.doFinal(source.getBytes());
            result = Base64.encodeBase64(rawHmac);

        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }
    }


    /**
     * aes解密
     * @param encrypt   内容
     * @return
     * @throws Exception
     */
    public static String decrypt(String encrypt) {
        try {
            return decrypt(encrypt, AES_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * AES解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    public static void main(String[] args) {
        String plain = "POSTaai.qcloud.com/asr/v1/2000001?callback_url=http://test.qq.com/rec_callback&engine_model_type=1&expired=1473752807&nonce=44925&projectid=0&res_text_format=0&res_type=1&secretid=AKIDUfLUEUigQiXqm7CVSspKJnuaiIKtxqAv&source_type=0&sub_service_type=0&timestamp=1473752207&url=http://test.qq.com/voice_url";
        System.out.println(hmacSha1(plain, "bLcPnl88WU30VY57ipRhSePfPdOfSruK"));
        System.out.println(md5("12345788"));
    }
}
