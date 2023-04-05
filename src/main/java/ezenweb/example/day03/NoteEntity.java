package ezenweb.example.day03;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;

import javax.persistence.*;

@Entity
@Table(name="note")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nno;

    @Column
    private String ncontents;

    // entity -> dto [ 서비스에서 사용 ]
    public NoteDto toDto(){
        return NoteDto.builder()
                .nno(this.nno)
                .ncontents(this.ncontents)
                .build();
    }
}
