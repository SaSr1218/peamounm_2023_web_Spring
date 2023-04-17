package ezenweb.web.domain.board;

import ezenweb.web.domain.member.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity@Table(name = "board")
@Data@NoArgsConstructor@AllArgsConstructor
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    @Column
    private String btitle;
    @Column
    private String bcontent;
    // Fk = 외래키
    // 카테고리번호
    @ManyToOne // 다수가 하나에게 [ fk --> pk ]
    @JoinColumn(name="cno") // fk필드명
    @ToString.Exclude // 해당 필드는 ToString 쓰지 않겠다! [ * 양방향에는 필수! * ]
    private CategoryEntity categoryEntity;

    // 회원번호 [ 작성자 ]
    @ManyToOne
    @JoinColumn(name="mno")
    @ToString.Exclude
    private MemberEntity memberEntity;


}
