package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.todo.TodoDto;
import ezenweb.web.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000") // 해당 컨트롤러는 http://localhost:3000 요청 CORS 정책 적용
public class TodoController {

    @Autowired private TodoService todoService;

    // 1. 모든 게시물 출력
    @GetMapping("")
    public List<TodoDto> get( ){    // TodoDto , 서비스 , 리포지토리 , 엔티티  작업
       List<TodoDto> result = todoService.todoList();
        return result; // 서비스에서 리턴 결과를 axios에게 응답
    }
    
    // 2. 게시물 쓰기
    @PostMapping("")
    public boolean post( @RequestBody TodoDto todoDto ){
        boolean result = todoService.write(todoDto);
        return result;  // 서비스에서 리턴 결과를 axios에게 응답
    }
    
    // 3. 게시물 수정
    @PutMapping("")
    public boolean put( @RequestBody TodoDto todoDto  ){
        boolean result = todoService.update(todoDto);
        // [과제] 서비스 구현
        return true;   // 서비스에서 리턴 결과를 axios에게 응답
    }
    
    // 4. 게시물 삭제
    @DeleteMapping("")
    public boolean delete( @RequestParam int id ){
        boolean result = todoService.todoDelete(id);
        return result;  // 서비스에서 리턴 결과를 axios에게 응답
    }
}