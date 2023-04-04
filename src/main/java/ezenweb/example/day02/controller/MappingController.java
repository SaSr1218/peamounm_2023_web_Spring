package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// 스프링 관리하는 IOC 컨테이너 빈[객체] 등록
@Controller     //  서버로부터 HTTP 요청이 왔을때 해당 클래스로 핸들러 매핑 -> @ResponseBody 필요
@Slf4j          // 스프링 로그 메소드 제공 [ 레벨 : trace < debug < info < warn < error ]
public class MappingController {

    @RequestMapping( value = "/orange" , method = RequestMethod.GET ) // console 응답
    @ResponseBody
    public String getOrange(){
        log.info("클라이언트로부터 getOrange메소드 요청이 들어옴");
        return "GET 정상 응답";
    }

    @RequestMapping( value = "/orange" , method = RequestMethod.POST )
    @ResponseBody
    public String postOrange(){
        log.info("클라이언트로부터 postOrange메소드 요청이 들어옴");
        return "POST 정상 응답";
    }

    @RequestMapping( value = "/orange" , method = RequestMethod.PUT )
    @ResponseBody
    public String putOrange(){
        log.info("클라이언트로부터 putOrange메소드 요청이 들어옴");
        return "PUT 정상 응답";
    }

    @RequestMapping( value = "/orange" , method = RequestMethod.DELETE )
    @ResponseBody
    public String deleteOrange(){
        log.info("클라이언트로부터 deleteOrange메소드 요청이 들어옴");
        return "DELETE 정상 응답";
    }
}

