package com.linguochao.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2019/9/4 14:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;
    private String name;
    private Long parentId;
}
