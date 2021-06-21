package dev.rezaur.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.rezaur.jwt.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByRole(String role);
}
