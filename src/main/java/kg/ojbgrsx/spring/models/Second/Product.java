package kg.ojbgrsx.spring.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    private Long id;
    private String name;
    private Integer amount;
    private Double price;
}
