package org.zerock.j1.service;

import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;

import jakarta.transaction.Transactional;

@Transactional // spring꺼 써도되고 jakarta써도 상관없음
public interface TodoService {
    
    PageResponseDTO<TodoDTO> getList();
 
    TodoDTO register(TodoDTO dto);

    TodoDTO getOne(Long tno);

    void remove(Long tno);

    void modify(TodoDTO dto);

    
}
