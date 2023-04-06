package ezenweb.example.day04.domain.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 상속을 주는 클래스로 이용 [ 부모 클래스 필드를 자식 클래스가 사용 가능 ]
@EntityListeners( AuditingEntityListener.class ) // 엔티티 실시간 감시
public class Basetime {

    @CreatedDate
    private LocalDateTime cdate;    // 레코드 생성 날짜

    @LastModifiedDate
    private LocalDateTime udate;    // 레코드 수정 날짜

}
