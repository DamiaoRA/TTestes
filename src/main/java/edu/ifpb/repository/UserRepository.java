package edu.ifpb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import edu.ifpb.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("from User u where upper(u.name) = upper(:name)")
	User findByNameIgnoreCase(String name);

	@Query("from User u order by id limit 1")
	User findFirstById();

	@Query("from User u where u.email = :email")
	UserDetails findByEmail(String email);
}