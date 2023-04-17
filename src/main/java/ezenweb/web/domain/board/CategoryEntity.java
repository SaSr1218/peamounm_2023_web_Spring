package ezenweb.web.domain.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity@Table(name = "bcategory")
@Data@NoArgsConstructor@AllArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;

    @Column
    private String canme;
}
