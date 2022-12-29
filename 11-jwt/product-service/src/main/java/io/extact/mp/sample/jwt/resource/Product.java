package io.extact.mp.sample.jwt.resource;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@EqualsAndHashCode @ToString
public class Product {
    @With
    private Long id;
    private String name;
    private int price;
    @With
    private String registerBy;
}