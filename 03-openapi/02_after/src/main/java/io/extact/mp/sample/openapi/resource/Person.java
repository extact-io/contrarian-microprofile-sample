package io.extact.mp.sample.openapi.resource;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;


@Schema(description = "Person情報")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Person {
    @With
    @Schema(description = "インスタンスID", implementation = Long.class, minimum = "0", maximum = "9999999")
    private Long id;
    @Schema(description = "名前", required = true,  minLength = 1, maxLength = 10)
    private String name;
    @Schema(description = "年齢", required = true,  implementation = Integer.class, minimum = "0", maximum = "199")
    private int age;
}