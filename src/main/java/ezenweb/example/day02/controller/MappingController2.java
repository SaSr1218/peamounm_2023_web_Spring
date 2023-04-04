package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// 스프링 관리하는 IOC 컨테이너 빈[객체] 등록
// @Controller = 서버로부터 HTTP 요청이 왔을때 해당 클래스로 핸들러 매핑 -> @ResponseBody 필요
@RestController // @Controller + @ResponseBody 가 합쳐진 것임.
@Slf4j          // 스프링 로그 메소드 제공 [ 레벨 : trace < debug < info < warn < error ]
public class MappingController2 {

    @RequestMapping( value = "/red" , method = RequestMethod.GET ) // console 응답
    public String getOrange(){
        log.info("클라이언트로부터 getOrange메소드 요청이 들어옴");
        return "GET 정상 응답";
    }

    @RequestMapping( value = "/red" , method = RequestMethod.POST )
    public String postOrange(){
        log.info("클라이언트로부터 postOrange메소드 요청이 들어옴");
        return "POST 정상 응답";
    }

    @RequestMapping( value = "/red" , method = RequestMethod.PUT )
    public String putOrange(){
        log.info("클라이언트로부터 putOrange메소드 요청이 들어옴");
        return "PUT 정상 응답";
    }

    @RequestMapping( value = "/red" , method = RequestMethod.DELETE )
    public String deleteOrange(){
        log.info("클라이언트로부터 deleteOrange메소드 요청이 들어옴");
        return "DELETE 정상 응답";
    }
}

