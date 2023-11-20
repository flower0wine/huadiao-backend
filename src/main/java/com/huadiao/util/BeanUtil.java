package com.huadiao.util;

import com.huadiao.entity.elasticsearch.UserEs;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * bean 的工具类
 *
 * @author flowerwine
 * @date 2023 年 11 月 15 日
 */
public class BeanUtil {
    /**
     * 移动 source 的属性值到 target 的属性, source 属性值为 null 的属性忽略,
     * 使用到的类有三个
     * {@link org.springframework.beans.BeanWrapper},
     * {@link org.springframework.beans.BeanWrapperImpl},
     * {@link org.springframework.beans.BeanUtils}
     *
     * @param target 移动属性值的目标 target
     * @param source 移动属性值的源头 source
     */
    public static void moveProperties(Object target, Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        org.springframework.beans.BeanUtils.copyProperties(source, target, emptyNames.toArray(result));
    }
}
