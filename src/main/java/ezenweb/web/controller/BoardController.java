package ezenweb.web.controller;


import ezenweb.example.day06_객체관계.Board;
import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.domain.board.CategoryDto;
import ezenweb.web.domain.board.PageDto;
import ezenweb.web.domain.board.ReplyDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


// @CrossOrigin( origins = "http://localhost:3000")
@RestController
@Slf4j
@RequestMapping("/board")

public class BoardController {

    // -------------------- 서비스 객체들 ------------------- //
    @Autowired private BoardService boardService;

    // ------------------- view 반환 ------------------- // -> react 통합 후 사용 X
        /*
            @GetMapping("")
            public Resource index(){
                return new ClassPathResource("templates/board/list.html");
            }
        */

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
    public List<CategoryDto> categoryList() { log.info("c categoryList : " );
        List<CategoryDto> result = boardService.categoryList( );
        return result;
    }

    // 3. 게시물 쓰기 // body { "btitle" : "입력한제목" , "bcontent" : "입력한내용" , "cno" : "선택한cno" }
    @PostMapping("")
    public byte write( @RequestBody BoardDto boardDto ){
        log.info("board dto : " + boardDto);
        byte result = boardService.write( boardDto );
        return result;
    }

    // 4. 카테고리별 게시물 전체 출력
    @GetMapping("")
    public PageDto list( PageDto pageDto ){
        PageDto result = boardService.list( pageDto );
        return result;
    }

    // 5. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards(){log.info("s myboards : ");
        List<BoardDto> result = boardService.myboards();
        return result;
    }

    // 6. 개별 게시물 출력
    @GetMapping("/getboard")
    public BoardDto getboard(@RequestParam int bno){ log.info("boardclick bno : " + bno);
        BoardDto result = boardService.getboard( bno );
        return result;
    }

    // 7. 개별 게시물 삭제
    @DeleteMapping("")
    public int delete( @RequestParam int bno  ){
        int result = boardService.delete( bno );
        return result;
    }

    // 8. 개별 게시물 수정
    @PutMapping("")
    public boolean update(@RequestBody BoardDto boardDto ) {
        boolean result = boardService.boardUpdate( boardDto );
        return result;
    }

    // 9. 댓글 작성 [ C ]
    @PostMapping("reply")
    public boolean postReply(@RequestBody ReplyDto replyDto){
        boolean result = boardService.postReply(replyDto);
        return result;
    }

    // 10. 댓글 출력 [ R ] -> 6번 함수랑 같이 출력! -> 할 필요 X
    /*    @GetMapping("reply")
        public boolean getReply() {
            return true;
        }*/

    // 11. 댓글 수정 [ U ]
    @PutMapping("reply")
    public boolean putReply(@RequestBody ReplyDto replyDto) {
        boolean result = boardService.putReply( replyDto );
        return result;
    }

    // 12. 댓글 삭제 [ D ]
    @DeleteMapping("reply")
    public boolean deleteReply(@RequestParam int rno) {
        boolean result = boardService.deleteReply( rno );
        return result;
    }


}