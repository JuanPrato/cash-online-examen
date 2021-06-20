package com.cashonline.examen.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "loans")
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total", nullable = false, columnDefinition = "REAL")
    private Float total;

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Long user;

}
