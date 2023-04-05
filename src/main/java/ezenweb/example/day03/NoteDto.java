package ezenweb.example.day03;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 롬복 사용
@Data // getter , setter , toString 제공
@AllArgsConstructor @NoArgsConstructor
@Builder

public class NoteDto {
    private int nno;
    private String ncontents;

    // dto -> entity [ 서비스에서 사용 ]
    public NoteEntity toEntity(){
        return NoteEntity.builder()
                .nno(this.nno)
                .ncontents(this.ncontents)
                .build();
    }

}
