package io.extact.mp.sample.openapi3.reader;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASModelReader;
import org.eclipse.microprofile.openapi.models.Components;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.media.Schema.SchemaType;

public class LocalDateApiModelReader implements OASModelReader {

    @Override
    public OpenAPI buildModel() {
        Components components = OASFactory.createComponents()
                .addSchema("LocalDate", OASFactory.createSchema()
                        .description("日付型フォーマット(ModelReader)")
                        .example("20220904")
                        .format("yyyyMMdd")
                        .type(SchemaType.STRING));
        OpenAPI openAPI = OASFactory.createOpenAPI();
        openAPI.components(components);
        return openAPI;
    }
}
