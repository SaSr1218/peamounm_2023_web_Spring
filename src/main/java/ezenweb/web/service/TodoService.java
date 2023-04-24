package ezenweb.web.service;

import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.domain.todo.TodoEntity;
import ezenweb.web.domain.todo.TodoEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {

    @Autowired private TodoEntityRepository todoEntityRepository;

    // 1. 모든 게시물 출력
    @Transactional
    public List<TodoDto> todoList() {
        List<TodoDto> list = new ArrayList<>();
        List<TodoEntity> todoEntityList = todoEntityRepository.findAll();

        todoEntityList.forEach( (e) -> {
            list.add(e.todoDto() );
        });
        return list;
    }

    // 2. 게시물 쓰기
    @Transactional
    public boolean write( TodoDto todoDto ){
        TodoEntity todoEntity = todoEntityRepository.save( todoDto.todoEntity() );
        if ( todoEntity.getId() > 0 ){ return true; }
        return false;
    }

    // 3. 게시물 수정
    @Transactional
    public boolean update( TodoDto todoDto  ){
        Optional<TodoEntity> entityOptional = todoEntityRepository.findById(todoDto.getId());
        if ( entityOptional.isPresent() ) {
            TodoEntity entity = entityOptional.get();

            entity.setTitle( todoDto.getTitle() );

            return true;
        }
        return false;
    }


    // 4. 게시물 삭제
    @Transactional
    public boolean todoDelete(int id){
        Optional<TodoEntity> optionalTodo = todoEntityRepository.findById(id);
        if(optionalTodo.isPresent() ){
            todoEntityRepository.delete(optionalTodo.get() );
            return true;
        }
        return false;
    }

}
