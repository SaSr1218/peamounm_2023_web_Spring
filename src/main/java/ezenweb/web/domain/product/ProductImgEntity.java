package ezenweb.web.domain.product;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="productimg")
public class ProductImgEntity {

    @Id
    private String uuidFile;               // 1. 이미지식별이름 [ 오토키 ]

    @Column
    private String oridinalFilename;         // 2. 이미지 이름

    @ManyToOne             // fk 필드 선언시!
    @JoinColumn(name="id") // DB 테이블에 표시될 FK 필드명
    @ToString.Exclude // 순환찬조 방지 [ 양방향일때는 필수 ]
    private ProductEntity productEntity;    // 3. 제품객체 [ fk ]
}