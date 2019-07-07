package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author linguochao
 * @date 2019\7\7 0007
 */
public class StringUtils {

    private static Pattern NUMBER_PATTERN = Pattern.compile("(-|\\+)?[0-9]+(.[0-9]+)?");
    // 手机号码正则表达式
    public static final String MOBILE_REG = "^1[3|4|5|7|8][0-9]{9}$";
    // 电子邮箱检测正则表达式
    public static final String EMAIL_REG = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
    //身份证
    public static final String IDENTIFY = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";

    public static final int MAX_NUMBER_LEN = 8;

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean endsWith(String src, String suffix) {
        if (isEmpty(src)) {
            return false;
        }
        return src.endsWith(suffix);
    }

    public static boolean startsWith(String src, String prefix) {
        if (isEmpty(src)) {
            return false;
        }
        return src.startsWith(prefix);
    }


    public static boolean isMatch(String src, String reg) {
        if (isEmpty(src)) {
            return false;
        }
        Matcher matcher = Pattern.compile(reg).matcher(src);
        return matcher.matches();
    }

    public static boolean isMobile(String mobile) {
        return isMatch(mobile, MOBILE_REG);
    }


    public static boolean isEmail(String email) {
        return isMatch(email, EMAIL_REG);
    }

    /**
     * 获取唯一UUID字符串
     *
     * @return
     */
    public static String uniqueStr() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取当前时间点的唯一ID
     *
     * @return
     */
    public static String nowStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
        StringBuilder builder = new StringBuilder(format.format(new Date()));
        builder.append(randomNum(5));
        return builder.toString();
    }

    /**
     * 获取指定长度的随机数字
     *
     * @param len
     * @return
     */
    public static int randomNum(int len) {
        Random random = new Random();
        int max = max(len);
        int min = min(len);
        return random.nextInt(max - min) + min;
    }

    /**
     * 获取最小N为整数
     *
     * @param len
     * @return
     */
    public static int min(int len) {
        if (len > MAX_NUMBER_LEN) {
            throw new RuntimeException("length out of range");
        }
        int number = 1;
        for (int i = 0; i < len - 1; i++) {
            number *= 10;
        }
        return number;
    }

    /**
     * 获取最大len位整数
     *
     * @param len
     * @return
     */
    public static int max(int len) {
        if (len > MAX_NUMBER_LEN) {
            throw new RuntimeException("length out of range");
        }
        int number = 1;
        for (int i = 0; i < len; i++) {
            number *= 10;
        }
        return number - 1;
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumeric(String str) {
        Matcher isNum = NUMBER_PATTERN.matcher(str);
        return isNum.matches();
    }


    public static Integer parseInt(Object str) {
        return str == null ? 0 : Integer.valueOf((isNumeric(str.toString())) ? Integer.parseInt(str.toString()) : 0);
    }

    /**
     * null 或者 'null' 转换成 ""
     */
    public static final String null2Blank(Object str) {
        return ((null == str || "null".equals(str)) ? "" : str.toString());
    }

    public static Byte parseByte(Object str) {
        return str == null ? 0 : Byte.valueOf((isNumeric(str.toString())) ? Byte.parseByte(str.toString()) : 0);
    }

    /**
     * 手机号码脱敏（前三位，后两位）
     *
     * @param mobile
     * @return
     */
    public static String desMobile(String mobile) {
        if (!isMobile(mobile)) {
            return mobile;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(mobile.substring(0, 3)).append("******").append(mobile.substring(9, 11));
        return buffer.toString();
    }

    /**
     * 判断是否是身份证号码
     *
     * @param identify
     * @return
     */
    public static boolean isIdentify(String identify) {
        return isMatch(identify, IDENTIFY);
    }
}
