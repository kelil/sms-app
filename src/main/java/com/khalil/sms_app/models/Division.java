package com.khalil.sms_app.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table
public class Division {
    @Id
    @SequenceGenerator(
            name = "division_sequence",
            sequenceName = "division_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
    generator = "division_sequence")
    private Integer id;
    private String name;
    @ManyToOne
    private Division parent;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Division> children = new HashSet<>();
    
   
    public Division(){

    }
    public Division(Integer id, String name, Division parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Division getParent() {
        return parent;
    }
    public void setParent(Division parent) {
        this.parent = parent;
    }

    @JsonIgnore
    public Set<Division> getChildren() {
        return children;
    }
    public void setChildren(Set<Division> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", parent='" + getParent() + "'" +
            ", children='" + getChildren() + "'" +
            "}";
    }

}

