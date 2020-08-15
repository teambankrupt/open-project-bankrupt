package com.example.gentool;

import java.util.Arrays;

public enum GenerationTypes {
    MODULE("module", ""),
    CRUD("crud", "s");

    private String label;
    private String suffixForPackage;

    GenerationTypes(String label, String suffixForPackage) {
        this.label = label;
        this.suffixForPackage = suffixForPackage;
    }

    public String getSuffixForPackage() {
        return suffixForPackage;
    }

    public String getLabel() {
        return label;
    }

    public static GenerationTypes find(String label) {
        return Arrays.stream(values())
                .filter(v -> v.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find GenerationType with: " + label));
    }
}
