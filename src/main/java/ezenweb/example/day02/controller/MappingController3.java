package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// 스프링 관리하는 IOC 컨테이너 빈[객체] 등록
@RestController // @Controller + @ResponseBody 가 합쳐진 것임.
@Slf4j          // 스프링 로그 메소드 제공 [ 레벨 : trace < debug < info < warn < error ]
public class MappingController3 {

    @GetMapping ("/blue" ) // console 응답
    public String getOrange(){
        log.info("클라이언트로부터 getOrange메소드 요청이 들어옴");
        return "GET 정상 응답";
    }

    @PostMapping ("/blue" ) // console 응답
    public String postOrange(){
        log.info("클라이언트로부터 postOrange메소드 요청이 들어옴");
        return "POST 정상 응답";
    }

    @PutMapping ("/blue" ) // console 응답
    public String putOrange(){
        log.info("클라이언트로부터 putOrange메소드 요청이 들어옴");
        return "PUT 정상 응답";
    }

    @DeleteMapping ("/blue" ) // console 응답
    public String deleteOrange(){
        log.info("클라이언트로부터 deleteOrange메소드 요청이 들어옴");
        return "DELETE 정상 응답";
    }
}

