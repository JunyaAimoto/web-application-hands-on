package com.example.taskapp.repository;
import com.example.taskapp.entity.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface TaskRepository extends JpaRepository<Task,Long>{
 @Query("select t from Task t join fetch t.user u where (lower(t.title) like lower(concat('%',coalesce(:keyword,''),'%')) or lower(coalesce(t.description,'')) like lower(concat('%',coalesce(:keyword,''),'%'))) and (:status is null or t.status=:status) and (:userId is null or u.id=:userId) order by t.id")
 List<Task> search(@Param("keyword") String keyword,@Param("status") TaskStatus status,@Param("userId") Long userId);
}
