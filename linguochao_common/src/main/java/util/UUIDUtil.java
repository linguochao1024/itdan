package util;


import java.util.UUID;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/3/13 12:33
 */
public class UUIDUtil {

    /**
     * 获取唯一ID
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }

    public static void main(String[] args) {
        String uuid = getUUID();
        System.out.println("uuid:"+uuid);
    }

}
