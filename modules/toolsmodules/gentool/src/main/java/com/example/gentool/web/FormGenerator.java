package com.example.gentool.web;

import com.example.gentool.web.mapping.Mapping;
import com.example.gentool.web.views.ButtonInput;
import com.example.gentool.web.views.EditableInput;
import com.example.gentool.web.views.InputView;
import com.example.gentool.web.views.SelectInput;
import com.example.gentool.web.views.enums.ButtonTypes;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class FormGenerator implements WebComponentGenerator {
    @Override
    public void generate(Class cls) {
        Field[] fields = cls.getDeclaredFields();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<form method=\"post\" th:action=\"@{/admin/crudexamples/}+${crudexample!=null?crudexample?.id:''}\">\n");
        for (Field field : fields) {
            stringBuilder.append("\n<!--------------").append(StringUtils.capitalize(field.getName())).append("----------------->\n");
            String fieldType = field.getType().getSimpleName();
            if (Mapping.getInputTypesMap().containsKey(fieldType)) {
                InputView inputView = new EditableInput(field);
                stringBuilder.append(inputView.generateHtml());
            }
            if (field.getType().isEnum()) {
                stringBuilder.append(new SelectInput(field).generateHtml());
            }

            stringBuilder.append("\n<!--------------------------------->\n");
        }
        stringBuilder.append("<div class=\"form-group\">\n");
        stringBuilder.append(new ButtonInput(ButtonTypes.SUBMIT, "Submit").generateHtml()).append("\n");
        stringBuilder.append(new ButtonInput(ButtonTypes.CANCEL, "Cancel").generateHtml()).append("\n");
        stringBuilder.append("</div>");

        stringBuilder.append("\n</form>");

        System.out.println(stringBuilder.toString());
    }

}
