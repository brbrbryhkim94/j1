package org.zerock.j1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j1.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> { // Entity타입과 PK타입을 JpaRepository< >안에 적음

}
