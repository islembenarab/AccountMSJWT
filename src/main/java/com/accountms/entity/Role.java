package com.accountms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long roleId;
    private String name;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @ToString.Exclude
    Set<User> users = new HashSet<>();

    @Override
    public String toString() {
        return "Role [name=" + name + "]" + "[id=" + roleId + "]";
    }

}
