package com.linguochao.swagger.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应对象
 *
 * @author linguochao
 * @date 2019\7\13 0013
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "响应对象")
public class BaseResult<T> {

    private static final int SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "成功";
    private static final int ERROR_CODE = -1;
    private static final String ERROR_MESSAGE = "失败";

    @ApiModelProperty(value = "响应码", position = 1, required = true, example = "" + SUCCESS_CODE)
    private int code;

    @ApiModelProperty(value = "响应消息", position = 2, required = true, example = SUCCESS_MESSAGE)
    private String msg;

    @ApiModelProperty(value = "响应数据", position = 3)
    private T data;


    private BaseResult(int code, String msg) {
        this(code, msg, null);
    }

    private BaseResult(T data) {
        this(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public <T> BaseResult<T> success() {
        return new BaseResult<>(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public <T> BaseResult<T> success(T data) {
        return new BaseResult<>(data);
    }

    public <T> BaseResult<T> error() {
        return new BaseResult<>(ERROR_CODE, ERROR_MESSAGE);
    }

    public <T> BaseResult<T> error(int code, String msg) {
        return new BaseResult<>(code, msg, null);
    }
}
