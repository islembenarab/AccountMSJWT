package com.accountms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long structureId;
    private String structureName;
    private String location;
    @OneToMany(mappedBy = "structure")
    @ToString.Exclude
    Set<User> user;

}
