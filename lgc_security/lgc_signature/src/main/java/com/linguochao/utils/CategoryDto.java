package com.linguochao.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.util.resources.ga.LocaleNames_ga;

import java.util.*;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2019/9/4 14:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Collection<Category> subs;

    Collection<CategoryDto> buildTree(Collection<Category> allCategory){
        Map<Long,CategoryDto> treeMap=new HashMap<>();
        Set<Long> idSet=new HashSet<>();
        allCategory.forEach(category->{
            CategoryDto category1 = new CategoryDto(category.getId(), category.getName(), new HashSet<>());
            treeMap.put(category.getId(),category1);
            idSet.add(category.getId());
        });
        allCategory.forEach(category->{
            Long parentId = category.getParentId();
            if (idSet.contains(parentId)){
                CategoryDto categoryDto = treeMap.get(parentId);
                Collection<Category> subs = categoryDto.getSubs();
                subs.add(category);
            }
        });
        return treeMap.values();
    }
}
