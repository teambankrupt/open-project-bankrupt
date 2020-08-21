package com.example.gentool.web.mapping;

import java.util.HashMap;
import java.util.Map;

public class Mapping {

    public static Map<String, String> annotationMap() {
        Map<String, String> map = new HashMap<>();
        map.put("@java.lang.Deprecated()", "required=\"required\"");

        return map;
    }

    public static Map<String, String> getInputTypesMap() {
        Map<String, String> map = new HashMap<>();
        map.put("String", "text");
        map.put("Instant", "datetime-local");
        map.put("LocalDate", "date");
        map.put("int", "number");
        map.put("Integer", "number");
        map.put("Long", "number");
        map.put("Double", "number");
        return map;
    }


}
