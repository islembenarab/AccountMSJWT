package com.accountms.repo;

import com.accountms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
     Optional<Role> findByNameContainingIgnoreCase(String name );
}