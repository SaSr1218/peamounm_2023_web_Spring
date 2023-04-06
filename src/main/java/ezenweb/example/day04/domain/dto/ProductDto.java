package ezenweb.example.day04.domain.dto;

import ezenweb.example.day04.domain.entity.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductDto {

    private int pno;
    private String pname;
    private String pcontent;
    private LocalDateTime cdate;
    private LocalDateTime udate;

    public ProductEntity toEntity(){
        return ProductEntity.builder()
                .pno(this.pno).pname(this.pname).pcontent(this.pcontent)
                .build();
    }
}
