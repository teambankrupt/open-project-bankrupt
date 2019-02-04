package com.example.webservice.entities.pojo;

import java.io.File;

public class UploadProperties {
    private String namespace;
    private String uniqueProperty;
    private String rootPath;
    private String fileName;

    public enum NameSpaces {
        USERS("users"),
        BUILDINGS("buildings"),
        APARTMENTS("apartments"),
        PROMOTIONS("promotions");

        String value;

        NameSpaces(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public String getDirPath() {
        if (namespace == null || namespace.isEmpty()
                || uniqueProperty == null || uniqueProperty.isEmpty()
                || rootPath == null || rootPath.isEmpty())
            throw new IllegalArgumentException("Rootpath or uniqueProperty or Namespace can not be null or empty!");
        return rootPath + File.separator + "AppData" + File.separator + namespace
                + File.separator + uniqueProperty + File.separator + "images";
    }

    public String getFilePath() {
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("Filename can not be null or empty!");
        return getDirPath() + File.separator + fileName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getUniqueProperty() {
        return uniqueProperty;
    }

    public void setUniqueProperty(String uniqueProperty) {
        this.uniqueProperty = uniqueProperty;
    }


    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}