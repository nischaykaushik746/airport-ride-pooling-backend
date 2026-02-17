package com.nischay.airpool.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users",
        indexes = @Index(name="idx_user_email", columnList="email", unique=true))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private java.util.List<Booking> bookings;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String role;
}
