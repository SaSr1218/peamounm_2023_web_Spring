package ezenweb.web.config;

import ezenweb.web.controller.AuthSuccessFailHandler;
import ezenweb.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 빈에 등록 [ MVC 컴포넌트 ]
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthSuccessFailHandler authSuccessFailHandler;

    // 인증[로그인] 관련 보안 담당 메소드 -> 패스워드 검증 코드인 셈
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder() ); // 내가 만든 memberService에 사용하기 위함
        // auth.userDetailsService()    :   detailService 가 구현된 서비스 대입
        // .passwordEncoder( new BCryptPasswordEncoder() ) :  서비스 안에서 로그인 패드워드 검증시 사용된 암호화 객체 대입
        // Bcrypt 안썼을경우 생략 가능!

    }

    // configure(HttpSecurity http) : http[URL] 관련 보안 담당 메소드
    @Override // 재정의 [ 코드 바꾸기 ]
    protected void configure(HttpSecurity http) throws Exception{
        //super.configure(http); // super : 부모 클래스 호출
        http
                // 권한에 따른 HTTP GET 요청 제한
                .authorizeHttpRequests() // HTTP 인증 요청
                    .antMatchers("/member/info/mypage") // 인증시에만 사용할 URL
                        .hasRole("user") // 위 URL 패턴에 요청할 수 있는 권한명
                    .antMatchers("/admin/**")// ~~ 이하 페이지는 admin만 가능
                        .hasRole("admin")
                    .antMatchers("/board/**") // 게시판 페이지는 회원만 가능
                        .hasRole("user")
                .antMatchers("/**") // localhost:8080 ~ 이하 페이지는 권한 해제
                    .permitAll() // 권한 해제
                        // 토큰 ( ROLE_user ) : ROLE_ 제외한 권한명 작성
                
                .and()
                    .csrf() // 사이트 간 요청 위조 [ post,put http 사용 불가 ]
                    // .disable() // 모든 http csrf 해제
                        .ignoringAntMatchers("/member/info") // 특정 매핑URL csrf 무시
                        .ignoringAntMatchers("/member/login")
                        .ignoringAntMatchers("/member/find") // 아이디 찾기, 비밀번호 찾기 열기
                        .ignoringAntMatchers("/board/category/write")
                        .ignoringAntMatchers("/board/write")
                        .ignoringAntMatchers("/board/click")
                        .ignoringAntMatchers("/board/delete")

                .and() // 기능 추가할 때 사용되는 메소드
                    .formLogin()
                        .loginPage("/member/login")               // 로그인페이지로 사용할 URL
                        .loginProcessingUrl("/member/login")      // 로그인 처리할 매핑 URL
                        //.defaultSuccessUrl("/")                 // 로그인 성공시 이동할 매핑 url
                        .successHandler(authSuccessFailHandler)
                        //.failureUrl("/member/login")  // 로그인 실패할경우 이동할 매핑 URL
                        .failureHandler(authSuccessFailHandler)
                        .usernameParameter("memail")              // 로그인 시 사용될 계정 아이디 필드명
                        .passwordParameter("mpassword")           // 로그인 시 사용될 계정 패스워드 필드명

                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))   // 로그아웃 처리 를 요청할 매핑 URL
                        .logoutSuccessUrl("/")           // 로그아웃 성공할 경우 이동할 매핑 URL
                        .invalidateHttpSession(true)     // 세션 초기화

                .and()
                    .oauth2Login() // 소셜 로그인 설정
                        .defaultSuccessUrl("/") // 로그인 성공시 이동할 매핑 URL
                        .successHandler(authSuccessFailHandler)
                        .userInfoEndpoint()    // 스프링 시큐리티로 들어올 수 있도록 시큐리티 로그인 엔드포인트[종착점]
                        .userService(memberService); //
    }
}
