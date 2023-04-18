package ezenweb.web.controller;


import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {

    // -------------------- 서비스 객체들 ------------------- //
    @Autowired private BoardService boardService;


    // ------------------- view 반환 ------------------- //
    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }



    // -------------------- model 반환 ------------------- //
    // 1. 카테고리 등록
    @PostMapping("/category/write") // body { cname : "공지사항" }
    public boolean categoryWrite(@RequestBody BoardDto boardDto ) { log.info("board dto : " + boardDto);
        boolean result = boardService.categoryWrite( boardDto );
        return result;
    }

    // 2. 카테고리 출력 [ 반환타입 : { 1 : 공지사항 , 2 : 자유게시판 }
        // List : { 값 , 값, 값, 값 }               -->
        // Map  : { 키 : 값 , 키 : 값 , 키 : 값 }    -->
    @GetMapping("/category/list")
    public Map<Integer , String> categoryList() { log.info("c categoryList : " );
        Map<Integer , String> result = boardService.categoryList( );
        return result;
    }

    // 3. 게시물 쓰기 // body { "btitle" : "입력한제목" , "bcontent" : "입력한내용" , "cno" : "선택한cno" }
    @PostMapping("/write")
    public byte write( @RequestBody BoardDto boardDto ){
        log.info("board dto : " + boardDto);
        byte result = boardService.write( boardDto );
        return result;
    }

    // 4. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards(){
        log.info("s myboards : ");
        List<BoardDto> result = boardService.myboards();
        return result;

    }



}