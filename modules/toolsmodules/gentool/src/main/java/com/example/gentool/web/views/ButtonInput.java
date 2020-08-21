package com.example.gentool.web.views;

import com.example.gentool.web.views.enums.ButtonTypes;

public class ButtonInput implements InputView {

    ButtonTypes type;
    String label;

    public ButtonInput(ButtonTypes type, String label) {
        this.type = type;
        this.label = label;
    }

    @Override
    public String generateHtml() {
        String html = "<button class=\"--classplaceholder--\" type=\"--typeplaceholder--\">--labelplaceholder--</button>";
        html = html.replace("--typeplaceholder--", type.getType());
        html = html.replace("--labelplaceholder--", type.getLabel());
        html = html.replace("--classplaceholder--", type.getBtnClass());
        return html;
    }


}
