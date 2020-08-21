package com.example.gentool.web.views;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SelectInput implements InputView {

    Field field;

    public SelectInput(Field field) {
        this.field = field;
    }

    @Override
    public String generateHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div class=\"form-group\">\n" +
                "        <label for=\"--nameplaceholder--\" class=\"--labelplaceholder--\">Value</label>\n" +
                "        <select name=\"--nameplaceholder--\">\n");
        for (Object obj : field.getType().getEnumConstants()) {
            String option = "<option value=\"--valueplaceholder--\">--optionnameplaceholder--</option>\n";
            option = option.replace("--valueplaceholder--", obj.toString());
            option = option.replace("--optionnameplaceholder--", getLabelForEnum(field.getType(), obj));
            stringBuilder.append(option);
        }
        stringBuilder.append("    </select>\n" +
                "    </div>");
        String html = stringBuilder.toString();
        html = html.replace("--nameplaceholder--", field.getName());
        html = html.replace("--labelplaceholder--", StringUtils.capitalize(field.getName()));

        return html;
    }

    private String getLabelForEnum(Class<?> cls, Object obj) {
        try {
            Method m = cls.getMethod("getLabel", null);
            return (String) m.invoke(obj, null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            return obj.toString();
        }
    }

}
