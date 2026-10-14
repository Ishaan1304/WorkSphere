package com.worksphere.WorkSphere.master.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String tenantKey;

    private String dbUrl;

    private String dbUsername;

    private String dbPassword;

    private String status;
}