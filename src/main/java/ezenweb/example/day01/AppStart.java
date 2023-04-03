package ezenweb.example.day01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {

    @Autowired
    private static Testmember2Repository testmember2Repository;

    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);

        LombokDto dto = LombokDto.builder()
                .mid("qweqwe") .mpassword("qweqwe")
                .build();

        testmember2Repository.save( dto.toEntity() );

    }
}

/*
    오류 JPA -------> DB 연동 실패

    Description:
    Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
    Reason: Failed to determine a suitable driver class

    -------------------------------------------
    해결
    resources -> file 생성 ( application.properties ) -> file 설정값 대입

    -------------------------------------------
    확인
    http://localhost:8080/ 들어가보기 ->   Whitelabel Error Page 면 연동 된 것임


*/