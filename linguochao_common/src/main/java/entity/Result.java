package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装后端返回给前端数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable{

    private Boolean flag;//是否执行成功
    private Integer code;//返回码
    private String message;//提示信息
    private Object data;//查询后返回的数据内容

    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
