package com.khalil.sms_app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
public class DivisionDTO {

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

    public Set<Division> getChildren() {
        return children;
    }

    public void setChildren(Set<Division> children) {
        this.children = children;
    }

    private Integer id;
    private String name;
    private Division parent;
    private Set<Division> children;

    public DivisionDTO(Integer id, String name, Division parent, Set<Division> children) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.children = children;
    }
}

