package io.extact.mp.sample.openapi3.resource;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBodySchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

public interface PersonResource {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "get", summary = "Person情報を取得する", description = "指定されたIDに対するPerson情報を取得する")
    @Parameter(name = "id", description = "PersonのインスタンスID", required = true, schema = @Schema(implementation = Long.class, minimum = "0", maximum = "9999999"))
    @APIResponse(responseCode = "200", description = "成功")
    @APIResponse(responseCode = "404", description = "該当なし", content = @Content(mediaType = "text/plan", example = "Not Found"))
    Person get(@PathParam("id")long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "add", summary = "Person情報を登録する", description = "IDは登録時に自動で採番する")
    @Parameter(name = "person", description = "登録するPersonインスタンス", required = true)
    @RequestBodySchema(Person.class)
    @APIResponseSchema(Person.class)
    @APIResponse(responseCode = "200", description = "成功。IDには採番した値を設定する")
    @APIResponse(responseCode = "409", description = "既に使用されているためnameのため重複エラー", content = @Content(mediaType = "text/plan", example = "Conflict"))
    Person add(Person person);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "findByName", summary = "Person情報を検索する", description = "QueryStringで指定されたnameに前方一致するPerson情報を検索する")
    @Parameter(name = "name", description = "検索するnameの値", required = true)
    @APIResponse(responseCode = "200", description = "成功。該当なしの場合は空リストで返却する")
    List<Person> findByName(@QueryParam("name") String name);
}
