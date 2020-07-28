package com.example.coreweb.domains.fileuploads.models.entities;

import com.example.coreweb.domains.base.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.File;

@Entity
@Table(name = "uploaded_files")
public class UploadProperties extends BaseEntity {

    @Column(name = "namespace")
    private String namespace;

    @Column(name = "unique_property")
    private String uniqueProperty;

    @Column(name = "root_path")
    private String rootPath;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType = "fileuploads";

    public UploadProperties() {
    }

    public UploadProperties(String rootPath, String namespace, String uniqueProperty, String fileType) {
        this.rootPath = rootPath;
        this.namespace = namespace;
        this.uniqueProperty = uniqueProperty;
        this.fileType = fileType;
    }

    public enum NameSpaces {
        USERS("users"),
        PROMOTIONS("promotions"),
        PRODUCTS("products");

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
                + File.separator + uniqueProperty + File.separator + this.fileType;
    }

    public String getFilePath() {
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("Filename can not be null or empty!");
        return getDirPath() + File.separator + fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
