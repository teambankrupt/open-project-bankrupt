package com.example.gentool.web.views;

import com.example.gentool.web.mapping.Mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface InputView extends HtmlView {

    default String getAttributesForField(Field field) {
        List<String> attributes = new ArrayList<>();

        for (Annotation a : field.getDeclaredAnnotations()) {
            System.out.println("Processing Annotation: " + a.toString());
            String value = Mapping.annotationMap().get(a.toString());
            if (value != null) attributes.add(value);
        }

        String fieldType = field.getType().getSimpleName();
        if ("Double".equals(fieldType))
            attributes.add("step=\"0.001\"");

        return String.join(" ", attributes);
    }

    default String getTypeFor(Field field) {
        String type = Mapping.getInputTypesMap().get(field.getType().getSimpleName());
        if (type == null) type = "text";
        return type;
    }


}
