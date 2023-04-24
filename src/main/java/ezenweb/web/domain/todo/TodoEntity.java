package ezenweb.web.domain.todo;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="todo")
public class TodoEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;       // Todo 식별번호
    @Column private String title; // Todo 내용
    @Column private boolean done; // 체크여부

    public TodoDto todoDto(){
        return TodoDto.builder()
                .id(this.id)
                .title(this.title)
                .done(this.done)
                .build();
    }

}
