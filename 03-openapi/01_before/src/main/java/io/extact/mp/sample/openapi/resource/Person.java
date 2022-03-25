package io.extact.mp.sample.openapi.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;


@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Person {
    @With
    private Long id;
    private String name;
    private int age;
}