package dev.rezaur.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.rezaur.jwt.model.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, String> {

	AppUser findByUserName(String userName); 
}
