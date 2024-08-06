package com.ivoronline.springboot_db_datasource_create_entitiymanager_hibernate.repository;

import com.ivoronline.springboot_db_datasource_create_entitiymanager_hibernate.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> { }
