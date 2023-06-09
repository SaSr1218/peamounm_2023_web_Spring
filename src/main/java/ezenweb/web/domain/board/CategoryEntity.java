package ezenweb.web.domain.board;

import ezenweb.web.domain.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity@Table(name = "bcategory")
@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class CategoryEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    @Column( name = "cname" , nullable = true )
    private String cname;
    // 양방향
    // 카테고리[pk] <----> 게시물[fk]
    // pk테이블에는 fk 흔적 남긴적이 없다.[ 필드 존재 X 객체 존재 O ]
    @OneToMany( mappedBy = "categoryEntity") // 하나 가 다수에게 [ pk --> fk ]
    @Builder.Default // .builder 사용시 현재 필드 기본값으로 설정
    private List<BoardEntity> boardEntityList = new ArrayList<>();
}
