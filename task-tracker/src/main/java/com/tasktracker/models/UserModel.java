package com.tasktracker.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tasktracker.models.embeddables.AuditInfo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class UserModel extends PanacheEntity {

    public String name;
    public String email;
    @Embedded
    public AuditInfo auditInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    public List<TaskModel> tasks = new ArrayList<>();
}
