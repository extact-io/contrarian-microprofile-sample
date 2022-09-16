package io.extact.mp.sample.openapi3.resource;

import java.time.LocalDate;
import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;


@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Schema(description = "Person情報")
public class Person {
    @With
    @Schema(description = "インスタンスID", implementation = Long.class, minimum = "0", maximum = "9999999")
    private Long id;
    @Schema(description = "名前", required = true,  minLength = 1, maxLength = 10)
    private String name;
    //@Schema(required = true, ref = "#/components/schemas/LocalDate")
    private LocalDate birthday;
    @Schema(properties = {
            @SchemaProperty(name = "nickname", description = "あだ名"),
            @SchemaProperty(name = "rank", description = "職位")
    }, implementation = Object.class)
    private Map<String, String> optins;
}