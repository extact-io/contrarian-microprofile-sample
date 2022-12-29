package io.extact.mp.sample.jwt.resource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

import io.extact.mp.sample.jwt.auth.AuthUser;
import io.extact.mp.sample.jwt.resource.ProductException.CauseError;

@ApplicationScoped
@Path("products")
public class ProductResource {

    private Map<Long, Product> productMap = new ConcurrentHashMap<>();

    @Inject
    private AuthUser authUser;

    @PostConstruct
    public void init() {
        productMap.put(1L, new Product(1L, "空を飛ぶ車", 800, "PGM"));
        productMap.put(2L, new Product(2L, "海を渡る自転車", 1200, "PGM"));
        productMap.put(3L, new Product(3L, "山を登る船", 500, "PGM"));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("member")
    public Product get(@PathParam("id") long id) {
        if (!productMap.containsKey(id)) {
            throw new ProductException(CauseError.NOT_FOUND);
        }
        return productMap.get(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAll(@Context SecurityContext context) {
        if (!context.isUserInRole("admin")) {
            throw new WebApplicationException(Status.FORBIDDEN); // 403 Forbidden
        }
        return productMap.values().stream()
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Product add(Product product) {
        // name の重複は許可しない
        productMap.values().stream()
                .filter(p -> p.getName().equals(product.getName()))
                .findAny()
                .ifPresent(p -> {
                    throw new ProductException(CauseError.DUPLICATE);
                });
        // Productの登録
        var nextId = productMap.keySet().stream().max(Long::compareTo).get() + 1;
        var newProduct = product
                .withId(nextId)
                .withRegisterBy(authUser.getUserId());
        productMap.put(nextId, newProduct);
        return newProduct;
    }
}
