package io.extact.mp.sample.restclient.resource;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;


@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@ToString
@EqualsAndHashCode
public class Person {
    @With
    private Long id;
    private String name;
    private int age;
}