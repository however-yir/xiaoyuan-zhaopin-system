package com.utils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 脱敏工具类
 */
public class DeSensUtil {

    public static void desensitize(PageUtils page, Map<String, String> deSens) {
        if (null != page && !CollectionUtils.isEmpty(page.getList())) {
            desensitize(page.getList(), deSens);
        }
    }

    public static void desensitize(List<?> objects, Map<String, String> deSens) {
        for (Object obj : objects) {
            if (obj != null) {
                desensitize(obj, deSens);
            }
        }
    }

    public static void desensitize(Object obj, Map<String, String> deSens) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                desensitizeField(obj, field, deSens);
            }
            clazz = clazz.getSuperclass();
        }
    }


    private static void desensitizeField(Object obj, Field field, Map<String, String> deSens) {
        field.setAccessible(true);
        try {
            String fieldName = field.getName();
            String rule = deSens.get(fieldName);
            if (rule != null) {
                Object value = field.get(obj);
                if (value instanceof String) {
                    String stringValue = (String) value;
                    String desensitizedValue = applyDesensitizationRule(stringValue, rule);
                    field.set(obj, desensitizedValue);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static String applyDesensitizationRule(String content, String type) {
        switch (type) {
            case "名": //保留姓名的第一个字，其余部分用*
                if (content == null || content.length() <= 1) {
                    return content;
                }
                return content.charAt(0) + repeatChar('*', content.length() - 1);
            case "手": //将手机号中间四位数字替换为*
                if (content == null || content.length() != 11) {
                    return content;
                }
                return content.substring(0, 3) + "****" + content.substring(7);
            case "身": //将身份证号码的第7位到第16位用*
                if (content == null || content.length() < 18) {
                    return content;
                }
                return content.substring(0, 6) + repeatChar('*', 10) + content.substring(14);
            case "卡": //银行卡号保留前6位和后4位，其他部分用*
                if (content == null || content.length() < 10) {
                    return content;
                }
                return content.substring(0, 6) + repeatChar('*', content.length() - 10) + content.substring(content.length() - 4);
            case "邮": //邮箱保留首字符及@符号后的域名部分，其他部分用*
                if (content == null || !content.contains("@")) {
                    return content;
                }
                int atIndex = content.indexOf("@");
                return content.charAt(0) + repeatChar('*', atIndex - 1) + content.substring(atIndex);
            default:
                return content;
        }
    }

    private static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

}

