package com.example.gentool.web.views;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class EditableInput implements InputView {

    private Field field;
    private boolean disabled = false;

    public EditableInput(Field field) {
        this.field = field;
    }

    public EditableInput(Field field, boolean disabled) {
        this.field = field;
        this.disabled = disabled;
    }

    @Override
    public String generateHtml() {
        String html = "                    <div class=\"form-group\">\n" +
                "                        <label for=\"--fieldname--\" class=\"label label-default\">--fieldlabel--</label>\n" +
                "                        <input type=\"--fieldtype--\" class=\"form-control\" name=\"--fieldname--\" placeholder=\"--fieldlabel--\" --fieldattributes-->\n" +
                "                    </div>";
        String fieldName = field.getName();
        html = html.replace("--fieldname--", fieldName);
        html = html.replace("--fieldlabel--", StringUtils.capitalize(fieldName));
        html = html.replace("--fieldattributes--", getAttributesForField(field));
        html = html.replace("--fieldtype--", getTypeFor(field));
        return html;
    }


}
