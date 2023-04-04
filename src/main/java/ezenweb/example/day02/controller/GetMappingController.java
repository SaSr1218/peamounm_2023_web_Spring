package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/get") // 공통 URL
public class GetMappingController {
    // -------------- 매개변수 요청 --------------- //
    // 1. HttpServletRequest request 객체를 이용한 매개변수 요청 [ JSP 주로 사용 ]
    @GetMapping("/method1") // 쿼리스트링사용 : http://localhost:8080/get/method1?param1=안녕
    public String method1(HttpServletRequest request){
        String param = request.getParameter("param1");
        log.info("클라이언트로부터 받은 변수 : " + param );
        return "요청받은 데이터 : " + param;
    }

    // 2. @PathVariable : URL 경로상의 변수 요청
    @GetMapping("/method2/{param1}/{param2}") // http://localhost:8080/get/method2/안녕/하세요
    public String method2(@PathVariable("param1") String param1 , @PathVariable("param2") String param2 ){
        log.info("클라이언트로부터 받은 변수 : " + param1 + param2 );
        return "요청받은 데이터 : " + param1 + param2;
    }

    // 3. @RequestParam
    @GetMapping("/method3") // http://localhost:8080/get/method3?param1=안녕&param2=하세요
    public String method3(@RequestParam String param1 , @RequestParam String param2){
        log.info("클라이언트로부터 받은 변수 : " + param1 + param2);
        return "요청받은 데이터 : " + param1 + param2;
    }
    // 4. @RequestParam + Map < >  ******* JSON 형식으로 받아들임!! ******** {param1="안녕", param2="하세요"}
    @GetMapping("/method4") //  http://localhost:8080/get/method4param1=안녕&param2=하세요
    public Map< String , String> method4(@RequestParam Map< String , String > params){
        log.info("클라이언트로부터 받은 변수 : " + params );
        return params;
        // return 타입을 Map < > or Array 로 바꾸면 JSON 형식이 됨
    }

    // 5. Dto          -> get,delete 메소드에서 매개변수 한번에 요청시 @RequestParam @RequestBody 불가능!
    @GetMapping("/method5") // {param1="1이다", param2="2이다"}
    public ParamDto method5( ParamDto dto){ // http://localhost:8080/get/method5?param1=1이다&param2=2이다
        log.info("클라이언트로부터 받은 변수 : " + dto );
        return dto;
        // return 타입을 Dto 로 바꾸면 JSON 형식이 됨
    }

    // 6. @ModelAttribute + Dto
    @GetMapping("/method6")  // ParamDto(param1=1이다, param2=2이다)
    public String method6(@ModelAttribute ParamDto dto){ // http://localhost:8080/get/method6?param1=1이다&param2=2이다
        log.info("클라이언트로부터 받은 변수 : " + dto );
        return "요청받은 데이터 : " + dto;
    }

}

/*
    GET 방식, Delete 방식 = @RequestParam 가능! Body 전송 X / 쿼리스트링 O
    POST 방식 , PUT 방식  = @RequestBody 가능! Body 전송 O / 쿼리스트링 X
*/