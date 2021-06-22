package com.cashonline.examen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "email", nullable = false, columnDefinition = "varchar", length = 128)
    private String email;

    @Column(name = "firstName", nullable = false, columnDefinition = "varchar", length = 128)
    private String firstName;

    @Column(name = "lastName", nullable = false, columnDefinition = "varchar", length = 128)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL,
                mappedBy = "userId",
                fetch = FetchType.LAZY,
                orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

}
