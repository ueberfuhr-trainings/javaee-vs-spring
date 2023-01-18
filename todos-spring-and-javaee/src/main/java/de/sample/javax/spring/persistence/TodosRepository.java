package de.sample.javax.spring.persistence;

import de.sample.javax.common.persistence.TodoEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Lazy
@Repository
public interface TodosRepository extends JpaRepository<TodoEntity, Long> {

    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER (:titleparam)")
    List<TodoEntity> findByTitleContaining(@Param("titleparam") String title);

}
