package com.worksphere.WorkSphere.master.repository;

import com.worksphere.WorkSphere.master.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository
        extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByTenantKey(String tenantKey);
}