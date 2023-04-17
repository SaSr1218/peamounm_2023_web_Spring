package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name="cno")
    private CategoryEntity categoryEntity;


}
