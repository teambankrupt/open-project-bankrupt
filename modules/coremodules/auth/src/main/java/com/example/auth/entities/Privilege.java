package com.example.auth.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "privileges", schema = "auth")
public class Privilege extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String label;

    private String description;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "privileges_access_urls", schema = "auth")
    private List<String> accessUrls;


    public Privilege() {
    }

    public Privilege(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String[] accessesArr() {
        String[] itemsArray = new String[accessUrls.size()];
        return accessUrls.toArray(itemsArray);
    }

    public String accessesStr() {
        return String.join(",", accessUrls);
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAccessUrls() {
        return accessUrls;
    }

    public void setAccessUrls(List<String> accessUrls) {
        this.accessUrls = accessUrls;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
