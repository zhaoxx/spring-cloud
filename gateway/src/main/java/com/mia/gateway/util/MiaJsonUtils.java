package com.mia.gateway.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;

public class MiaJsonUtils {

    public static String getJsonValue(Object arg) {
        if (arg == null) {
            return "null";
        }
        String typeName = arg.getClass().getName();
        if ("java.lang.String".equals(typeName)) {
            return (String) arg;
        }

        try {
            return JSON.toJSONString(arg, profilter);
        } catch (Exception ex) {
            return "{}";
        }
    }

    private static PropertyFilter profilter = new PropertyFilter() {
        @Override
        public boolean apply(Object object, String name, Object value) {
            if (name != null && (name.startsWith("set")
                    || name.endsWith("Iterator")
                    || "$ref".equals(name)
            )) {
                //false表示last字段将被排除在外
                return false;
            }
            return true;
        }
    };
}
