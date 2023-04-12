package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberEntityRepository memberEntityRepository;

    // 1. 회원가입
    @Transactional
    public boolean write(MemberDto memberDto) {
        // 스프링 시큐리티에서 제공하는 암호화 사용
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                // 인코더 : 형식으로 변경 // 디코더 : 원본으로 되돌리기
        log.info("비크립트 암호화 사용 : " + passwordEncoder.encode("1234"));
        // 입력받은[DTO] 패스워드 암호화 해서 다시 DTO에 저장한다.
        memberDto.setMpassword( passwordEncoder.encode(memberDto.getMpassword()) );
        MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
        if (entity.getMno() > 0) { return true;}
        return false;
    }

 /*
    *** 로그인 [ 시큐리티 사용 할 경우 ]

    // *2 로그인 [ 시큐리티 사용 하기 전 ]
    @Transactional
    public boolean login( MemberDto memberDto ){

        // 1. 이메일로 엔티티 찾기 = loadUserByUsername
        MemberEntity entity = memberEntityRepository.findByMemail( memberDto.getMemail() ); log.info("entity : " + entity );
        // 2. 찾은 엔티티 안에는 암호화된 패스워드 존재 =         auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder() );
            // 엔티티안에 있는 패스워드[암호화 O 상태] 와 입력받은 패스워드[암호화 X 상태 ]와 비교
        if ( new BCryptPasswordEncoder().matches( memberDto.getMpassword(), entity.getMpassword()) ){

        // 2. 패스워드 검증
            //    if ( entity.getMpassword().equals( memberDto.getMpassword() )) {
            // (1) ==       스택 메모리 내 데이터 비교(지역변수)
            // (2) .equals  힙 메모리 내 데이터 비교
            // (3) .matches 문자열 주어진 패턴 포함 동일여부 체크

            // 세션 사용 : 메소드 밖 필드에 @Autowired private HttpServletRequest request;
            request.getSession().setAttribute("login", entity.getMemail() );
            return true;
        }
        return false;

        // 2. 입력받은 이메일과 패스워드가 동일한지 확인
        *//* Optional<MemberEntity> result = memberEntityRepository.findByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword() );
           log.info("result : " + result );
        if ( result.isPresent() ){
            request.getSession().setAttribute("login", result.get().getMno() );
            return true;
        }*//*

        // 3. 이메일과 패스워드 동일인지 확인
        *//*        boolean result = memberEntityRepository.existsByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword());
            log.info("result : " + result);
        if ( result == true ){ request.getSession().setAttribute("login" , memberDto.getMemail()); return true;}*//*
        

    }*/




    // 3. 회원수정
    @Transactional
    public boolean update(MemberDto memberDto) {
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(memberDto.getMno() );
        if (entityOptional.isPresent()) {
            MemberEntity entity = entityOptional.get();
            entity.setMname(memberDto.getMname());
            entity.setMphone(memberDto.getMphone());
            entity.setMrole(memberDto.getMrole());
            entity.setMpassword(memberDto.getMpassword());
            return true;
        }
        return false;
    }

    // 4. 회원탈퇴
    @Transactional
    public boolean delete(int mno) {
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(mno);
        if (entityOptional.isPresent()) {
            MemberEntity entity = entityOptional.get();
            memberEntityRepository.delete( entity );
            return true;
        }
        return false;
    }
    // 2. [ 세션에 존재하는 ] 회원정보
    @Transactional
    public MemberDto info() {
        // 1. 시큐리티 인증[로그인] 된 UserDetails 객체[세션]로 관리했을때 [ Spring ]
            // SecurityContextHolder : 시큐리티 정보 저장소
            // SecurityContextHolder.getContext() : 시큐리티 저장된 정보 호출
            // SecurityContextHolder.getContext().getAuthentication(); // 인증 정보 호출
            // SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 인증된 회원의 정보 호출
             log.info("Auth : " + SecurityContextHolder.getContext().getAuthentication() );
             log.info("Auth : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
        Object o =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( o == null ) { return null; }

        // 2. 인증된 객체내 회원정보[ Principal ] 타입 변환
        return (MemberDto)o; // Object -------> Dto

    /*
    // 2. 일반 세션으로 로그인 정보를 관리 했을때 [ JSP ]
        String memail = (String)request.getSession().getAttribute("login");
        if ( memail != null ){
            MemberEntity entity = memberEntityRepository.findByMemail( memail );
            return entity.toDto();
        }
        return null;
    */

    }

    // 2. [ 세션에 존재하는 정보 제거 ] 로그아웃
    /*
    @Transactional
    public boolean logout(){
        request.getSession().setAttribute("login", null); return true;
    }*/

    // [ 스프링 시큐리티 적용했을때 사용되는 로그인 메소드 ]
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        // 1. UserDetailsService 인터페이스 구현
        // 2. loadUserByUsername() 메소드 : 아이디만 검증 -> 패스워드 검증  [ 시큐리티가 자동 검사 ]
             MemberEntity entity = memberEntityRepository.findByMemail(memail);
             if ( entity == null ) { return null; }

        // 3. 검증후 세션에 저장할 DTO 반환
        MemberDto dto =  entity.toDto();
        log.info("dto : " + dto);
        return dto;
    }

}