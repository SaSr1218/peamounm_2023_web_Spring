package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

// UserDetailsService : 일반유저 서비스 구현 --> loadUserByUsername 구현
// OAuth2UserService  : oauth2 유저 서비스 구현

@Service
@Slf4j
public class MemberService implements UserDetailsService , OAuth2UserService<OAuth2UserRequest , OAuth2User> {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. 인증[로그인]결과 토큰 확인
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();   log.info("서비스 정보 : " + oAuth2UserService.loadUser(userRequest) );

        // 2. 전달받은 정보 객체
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);    log.info("회원정보 : " + oAuth2User.getAuthorities() );

        // 3. 클라이언트id 식별 ( JSON 구조가 다르기 때문 ) [ 구글 vs 네이버  vs 카카오 ]
        String registrationId = userRequest.getClientRegistration().getRegistrationId();    log.info("클라이언트 id : " + registrationId);

        String email = null;
        String name = null;
        if ( registrationId.equals("kakao") ){
            Map<String , Object> kakao_account = (Map<String, Object>)oAuth2User.getAttributes().get("kakao_account"); log.info("카카오 정보 : " + kakao_account);
            Map<String , Object> profile = (Map<String, Object>) kakao_account.get("profile"); log.info("카카오 프로필 : " + profile);

            email = (String)kakao_account.get("email");
            name = (String)profile.get("nickname");

        }else if ( registrationId.equals("naver") ){
            Map<String , Object >response = (Map<String, Object>) oAuth2User.getAttributes().get("response");

            email = (String)response.get("email");
            name = (String)response.get("nickname");

        }else if ( registrationId.equals("google") ) {
            // 구글 Attributes = { sub = 1231231231 , name = 최성아, given_name = 성아 , email = cc@naver.com }
             email = (String)oAuth2User.getAttributes().get("email");  log.info("google email : " + email); // 구글 이메일 호출
             name = (String)oAuth2User.getAttributes().get("name");   log.info("google name : " + name);    // 구글 이름 호출

        }

        // !!!!!!!!!!!!! : oAuth2User.getAttributes() map< String , Object > 구조
            // [{sub=115552644313400062559, name=최성아, given_name=성아, family_name=최, picture=https://lh3.googleusercontent.com/a/AGNmyxbg6E8kYgMgAVFPjxAuWouYsNYzuBZfz-gO4jz9=s96-c, email=peamounm@gmail.com, email_verified=true, locale=ko}]

        // 4.

        // 인가 객체 [ OAuth2User -> MemberDto 통합Dto( 일반 + oauth2 )
        MemberDto memberDto = new MemberDto();
        memberDto.set소셜회원정보(oAuth2User.getAttributes() );
        memberDto.setMemail(email);
        memberDto.setMname(name);
            Set<GrantedAuthority> 권한목록 = new HashSet<>();
            SimpleGrantedAuthority 권한 = new SimpleGrantedAuthority("ROLE_user");
            권한목록.add(권한);
        memberDto.set권한목록(권한목록);

        // 1. DB 저장하기 전에
        Optional<MemberEntity> entity =  memberEntityRepository.findByMemail( email );
        if ( !entity.isPresent() ) { // 첫 방문               entity == null  -> optional이 아닐 경우!!
            // DB 처리 [ 첫 방문시에만 db 등록 , 두번째 방문부터는 db 수정 ]
            memberDto.setMrole("oauthuser"); // DB에 저장할 권한명
            memberEntityRepository.save( memberDto.toEntity() );
        }else{ // 두번째 방문 이상 수정 처리
            entity.get().setMname( name );
        }

        return memberDto;
    }

    @Autowired
    private MemberEntityRepository memberEntityRepository;

    // 1. 일반 회원가입 [ 본 애플리케이션에서 가입한 사람 ]
    @Transactional
    public boolean write(MemberDto memberDto) {
        // 스프링 시큐리티에서 제공하는 암호화 사용
                // 인코더 : 형식으로 변경 // 디코더 : 원본으로 되돌리기
        log.info("비크립트 암호화 사용 : " + passwordEncoder.encode("1234"));
        // 입력받은[DTO] 패스워드 암호화 해서 다시 DTO에 저장한다.
        memberDto.setMpassword( passwordEncoder.encode(memberDto.getMpassword()) );

        // 회원가입할때 등급 부여
        memberDto.setMrole("user");

        MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
        if (entity.getMno() > 0) { return true;}
        return false;

    }

 /*
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
            boolean result = memberEntityRepository.existsByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword());
            log.info("result : " + result);
        if ( result == true ){ request.getSession().setAttribute("login" , memberDto.getMemail()); return true;}
        

    }*/

    // 3. 회원수정
    @Transactional
    public boolean update(MemberDto memberDto) {
        Optional<MemberEntity> entityOptional = memberEntityRepository.findById(memberDto.getMno() );
        if (entityOptional.isPresent()) {
            MemberEntity entity = entityOptional.get();

            // setter로 mname, mphone만 수정
            entity.setMname(memberDto.getMname());
            entity.setMphone(memberDto.getMphone());

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

    // 2. [ 세션에 존재하는 정보 제거 ] 로그아웃
    /*
    @Transactional
    public boolean logout(){
        request.getSession().setAttribute("login", null); return true;
    }*/
    // 5. 아이디찾기
    @Transactional
    public String findId(MemberDto memberDto){
        System.out.println("memberDto : " + memberDto);
        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findByMnameAndMphone(memberDto.getMname(), memberDto.getMphone());
        System.out.println("아이디찾기에 사용 : " + optionalMemberEntity);
        if ( optionalMemberEntity.isPresent() ){
            return optionalMemberEntity.get().getMemail();
        }
        return null;
    }

    // 6. 비밀번호 찾기
    @Transactional
    public String findPassword(MemberDto memberDto){
        boolean result = memberEntityRepository.existsByMemailAndMphone(memberDto.getMemail(), memberDto.getMphone());            log.info("qwe : " + result);
        String charStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^";
        String newPwd = "";

        if(result){
            Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findByMemail(memberDto.getMemail());
            System.out.println("비밀번호찾기 사용 : " + optionalMemberEntity);
            if(optionalMemberEntity.isPresent()){
                MemberEntity entity = optionalMemberEntity.get();

                for(int i = 0; i < 8; i++){
                    Random random = new Random();
                    int index = random.nextInt(charStr.length());
                    newPwd += charStr.charAt(index);

                }
                entity.setMpassword(passwordEncoder.encode(newPwd));
                return newPwd;
            }
        }
        return newPwd;
    }

    // 7. 아이디중복검사
    public boolean checkId(String memail){
        log.info("memail : " + memail);
        boolean result = memberEntityRepository.existsByMemail(memail);
        return !result;
    }

    // 8. 핸드폰중복검사
    public boolean checkPhone(String mphone){
        boolean result = memberEntityRepository.existsByMphone(mphone);
        return !result;

    }

    // 1. [ 스프링 시큐리티 적용했을때 사용되는 로그인 메소드 ]
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        // 1. UserDetailsService 인터페이스 구현
        // 2. loadUserByUsername() 메소드 : 아이디만 검증 -> 패스워드 검증  [ 시큐리티가 자동 검사 ]
        Optional<MemberEntity> entity = memberEntityRepository.findByMemail(memail);
             if (entity.get() == null ) { throw new UsernameNotFoundException("해당 계정회원이 없습니다."); }

        // 3. 검증후 세션에 저장할 DTO 반환
        MemberDto dto = entity.get().toDto();
            // Dto 권한(여러개) 넣어주기
        // 1. 권한목록 만들기
        Set<GrantedAuthority> 권한목록 = new HashSet<>();
        // 2. 권한객체 만들기 [ DB에 존재하는 권한명( ROLE_@@@으로 ]
            // 권한 없을 경우 : ROLE_ANONYMOUS
            // 권한 있을 경우 : ROLE_admin , ROLE_user
        SimpleGrantedAuthority 권한명 =  new SimpleGrantedAuthority("ROLE_"+ entity.get().getMrole() );
        // 3. 만든 권한객체를 권한목록[컬렉션]에 추가
        권한목록.add( 권한명 );
        // 4. UserDetails 에 권한목록 대입
        dto.set권한목록(권한목록);

        log.info("dto : " + dto);
        return dto; // UserDetails : 원본데이터의 검증할 계정, 패스워드 포함
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
        if ( o.equals("anonymousUser") ) { return null; }
        // [ Principal ]
        // 인증 실패 시 : anonymousUser
        // 인증 성공 시 : 회원정보[Dto]

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




}

