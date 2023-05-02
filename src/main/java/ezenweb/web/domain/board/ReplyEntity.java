package ezenweb.web.domain.board;

import ezenweb.web.domain.BaseTime;
import ezenweb.web.domain.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity @Table(name = "reply")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReplyEntity extends BaseTime {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;            // 댓글번호
    @Column
    private String rcontent;    // 댓글내용
    @Column
    private int rindex;         // 상위댓글번호
    // 게시물fk
    @ManyToOne@JoinColumn(name = "bno")@ToString.Exclude
    private BoardEntity boardEntity;
    // 작성자fk
    @ManyToOne@JoinColumn(name="mno")@ToString.Exclude
    private MemberEntity memberEntity;

    // 출력용
    public ReplyDto toDto(){
        return ReplyDto.builder()
                .rno( this.rno ).rcontent( this.rcontent )
                .rdate( this.cdate.toLocalDate().toString() )
                .mno( this.memberEntity.getMno() )
                .mname( this.memberEntity.getMname())
                .rindex( this.rindex )
                // cdate[ LocalDateTime ] <--> rdate[ String ]
                .build();
    }

}
