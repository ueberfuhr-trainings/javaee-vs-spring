package de.deutscherv.gb0500.schulung.spring.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.deutscherv.gb0500.schulung.common.persistence.TodoEntity;

@Repository
public interface TodosRepository extends JpaRepository<TodoEntity, Long> {

}
