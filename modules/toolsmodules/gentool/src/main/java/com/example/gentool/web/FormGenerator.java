package com.example.gentool.web;

import com.example.gentool.web.mapping.Mapping;
import com.example.gentool.web.views.EditableInput;
import com.example.gentool.web.views.InputView;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class FormGenerator implements WebComponentGenerator {
    @Override
    public void generate(Class cls) {
        Field[] fields = cls.getDeclaredFields();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<form class=\"form\">\n");
        for (Field field : fields) {
            stringBuilder.append("\n<!--------------" + StringUtils.capitalize(field.getName()) + "----------------->\n");
            String fieldType = field.getType().getSimpleName();
            if (Mapping.getInputTypesMap().containsKey(fieldType)) {
                InputView inputView = new EditableInput(field);
                stringBuilder.append(inputView.generateHtml());
            }
            stringBuilder.append("\n<!--------------------------------->\n");
        }
        stringBuilder.append("\n</form>");
        System.out.println(stringBuilder.toString());
    }


}
