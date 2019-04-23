package com.launchcode.cheese.models.data;

import com.launchcode.cheese.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryDAO extends CrudRepository<Category, Long> {
}
