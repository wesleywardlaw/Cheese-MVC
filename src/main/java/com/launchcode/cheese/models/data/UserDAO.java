package com.launchcode.cheese.models.data;

import com.launchcode.cheese.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional  //every operation individually, able to roll back
public interface UserDAO extends CrudRepository<User, Long> {
}
