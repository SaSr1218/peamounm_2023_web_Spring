package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/signup")
    public Resource getSignup(){ return new ClassPathResource("templates/member/signup.html");}
    @GetMapping("/login")
    public Resource getLogin(){ return new ClassPathResource("templates/member/login.html");}
    @GetMapping("/findid")
    public Resource findId(){ return new ClassPathResource("templates/member/findid.html");}
    @GetMapping("/findpassword")
    public Resource findPassword(){ return new ClassPathResource("templates/member/findpassword.html");}
    @GetMapping("/update")
    public Resource getUpdate(){ return new ClassPathResource("templates/member/update.html");}



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
    public MemberDto info(){
        MemberDto result = memberService.info();
        return result;
    }

    // 3. [U]회원정보 수정
    @PutMapping("/info")
    public boolean update( @RequestBody MemberDto memberDto ) {
        log.info("member info write : " + memberDto );
        MemberDto dto = memberService.info();
        memberDto.setMno( dto.getMno() );
        boolean result = memberService.update( memberDto );
        return result;
    }

    // 4. [D]회원정보 탈퇴
    @DeleteMapping("/info")
    public boolean delete( @RequestParam String mpassword ){
        MemberDto dto =  memberService.info();
        boolean result = false;

        if(new BCryptPasswordEncoder().matches(mpassword, dto.getMpassword())){
            result =  memberService.delete(dto.getMno());
        }

        return result;
    }
    // 5. 아이디 찾기
    @PostMapping("/find")
    public String findId(@RequestBody MemberDto memberDto ){
        String result = memberService.findId( memberDto );
        return result;
    }

    // 6. 비밀번호 찾기
    @PutMapping("/find")
    public String findPassword(@RequestBody MemberDto memberDto ){
        String result = memberService.findPassword( memberDto );
        return result;
    }

    // 7. 아이디 중복 검사
    @GetMapping("/find")
    public boolean findId(@RequestParam String memail){
        log.info("memail 값 : " + memail);
        boolean result = memberService.checkId(memail);
        return result;
    }


    // ----------- 스프링 시큐리티 적용될 경우 아래코드 사용 X  ----------- //

    /*
        // 1. 로그인
        @PostMapping("/login")
        public boolean login( @RequestBody MemberDto memberDto ){
            boolean result = memberService.login( memberDto );
            return result;
        }

        // 2. 회원정보[세션] 로그아웃
        @GetMapping("/logout")
        public boolean logout() { return memberService.logout(); }
    */

}
