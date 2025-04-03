package com.example.crud.domain.product;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "product")
@Entity(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private Integer price;
    private Boolean active;
    private String category;
    @Column(name = "distribution_center")
    private String distribution_center;


    public Product(RequestProduct requestProduct) {
        this.name = requestProduct.name();
        this.price = requestProduct.price();
        this.category = requestProduct.category();
        this.active = true;
        this.distribution_center = requestProduct.distribution_center();
    }


}
