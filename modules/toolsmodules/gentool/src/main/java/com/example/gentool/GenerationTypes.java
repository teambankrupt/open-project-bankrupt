package com.example.gentool;

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
        for (GenerationTypes v : values()) {
            if (v.label.equals(label))
                return v;
        }
        throw new RuntimeException("Couldn't find GenerationType with: " + label);
    }
}
