package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/signup")
    public Resource getSignup(){ return new ClassPathResource("templates/member/signup.html");}
    @GetMapping("/login")
    public Resource getLogin(){ return new ClassPathResource("templates/member/login.html");}



    // 1. @Autowird 생략 할 경우 ( JSP에서 싱글톤 만드는 방식처럼..)
    // MemberService service = new MemberService();

    // 2. @Autowired 있을때 객체[빈] 자동 생성
    @Autowired
    MemberService memberService;

    // 1. [C]회원가입
    @PostMapping("/info")
    public boolean write( @RequestBody MemberDto memberDto ){
        log.info("member info write : " + memberDto );
        boolean result = memberService.write(memberDto);
        return result;
    }

    // 2. [R]회원정보 호출
    @GetMapping("/info")
    public MemberDto info( @RequestParam int mno ){
        log.info("member info write : " + mno );
        MemberDto result = memberService.info( mno );
        return result;
    }

    // 3. [U]회원정보 수정
    @PutMapping("/info")
    public boolean update( @RequestBody MemberDto memberDto ) {
        log.info("member info write : " + memberDto );
        boolean result = memberService.update( memberDto );
        return result;
    }

    // 4. [D]회원정보 탈퇴
    @DeleteMapping("/info")
    public boolean delete( @RequestParam int mno ){
        log.info("member info write : " + mno );
        boolean result = memberService.delete( mno );
        return result;
    }

    // -------------------- 스프링 시큐리티 사용 전 --------------- //
    @PostMapping("/login")
    public boolean login( @RequestBody MemberDto memberDto ){
        boolean result = memberService.login( memberDto );
        return result;
    }

}
