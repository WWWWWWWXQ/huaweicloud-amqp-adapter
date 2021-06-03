package edu.hrbust.iot.amqp.security.dao;

import edu.hrbust.iot.amqp.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String s);

    @Query(value = "select userId from user where username = ?1")
    Integer findUserIdByUsername(String username);
}
