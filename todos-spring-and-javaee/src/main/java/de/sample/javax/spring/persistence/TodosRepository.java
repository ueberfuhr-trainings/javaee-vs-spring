package de.sample.javax.spring.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.sample.javax.common.persistence.TodoEntity;

@Repository
public interface TodosRepository extends JpaRepository<TodoEntity, Long> {

}
