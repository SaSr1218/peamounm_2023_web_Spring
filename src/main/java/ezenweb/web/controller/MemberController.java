package ezenweb.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {

    // 1. 회원가입
    @PostMapping("/info")
    public boolean write( @RequestBody MemberDto memberDto ){
        return false;
    }

    // 2. 회원정보 호출
    @GetMapping("/info")
    public MemberDto info( @RequestParam int mno ){
        return null;
    }

    // 3. 회원정보 수정
    @PutMapping("/info")
    public boolean update( @RequestBody MemberDto memberDto ) {
        return false;
    }

    // 4. 회원정보 탈퇴
    @DeleteMapping("/info")
    public boolean delete( @RequestParam int mno ){
        return false;
    }

}
