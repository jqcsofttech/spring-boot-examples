package com.edu.enterprise.productservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("products")  // Mapeo de R2DBC
public class Product {
    @Id
    @Column("id")
    private Integer id;
    @Column("name")
    private String name;
    @Column("price")
    private Double price;
}