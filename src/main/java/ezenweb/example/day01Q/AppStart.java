package ezenweb.example.day01Q;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 모든 컨테이너 빈 등록
public class AppStart {
    public static void main(String[] args) {
        // SpringApplication.run(현재클래스명.class);
        SpringApplication.run(AppStart.class);
    }
}
