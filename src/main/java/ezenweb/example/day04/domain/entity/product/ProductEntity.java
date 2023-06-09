package ezenweb.example.day04.domain.entity.product;

import ezenweb.example.day04.domain.dto.ProductDto;
import ezenweb.example.day04.domain.entity.Basetime;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ProductEntity extends Basetime {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int pno;
    @Column
    private String pname;
    @Column
    private String pcontent;

    public ProductDto toDto(){
        return ProductDto.builder()
                .pno(this.pno).pname(this.pname).pcontent(this.pcontent)
                .cdate(this.cdate).udate(this.udate)
                .build();
    }

}
