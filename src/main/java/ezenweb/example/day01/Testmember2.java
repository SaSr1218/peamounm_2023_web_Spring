package ezenweb.example.day01;

import lombok.Builder;

import javax.persistence.*; // !!!!!! javax.persistence 로 사용!

@Entity // 엔티티 = DB 테이블 매핑 = 테이블 = 객체
@Table (name="testmember2") // 테이블 이름 정하기 = 생략시 클래스명으로 테이블이 생성된다.
@Builder
public class Testmember2 {
    // DB 테이블 필드 선언  --> 워크벤치에서 create 대신 사용하는 것임!
    @Id // PK 필드 선언
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto key
    private int mno;
    @Column
    private String mid;
    @Column
    private String mpassword;
    @Column
    private long mpoint;
    @Column
    private String mphone;
}
