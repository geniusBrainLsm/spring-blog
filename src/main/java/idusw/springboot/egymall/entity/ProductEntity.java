package idusw.springboot.egymall.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // Spring Data JPA의 엔티티(개체)임을 의미함
@Table(name="product")

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String productName;

    private String description;

    private Long price;

    private Long quantity;
}
