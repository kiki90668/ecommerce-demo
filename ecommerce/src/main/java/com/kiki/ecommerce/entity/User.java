package com.kiki.ecommerce.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false, unique = true)
private String username;

@Column(nullable = false)
private String password;

@Column(name = "mobile_no", unique = true, length = 10, nullable = true)
private String mobileNo;

private String role; //User or Admin
}
