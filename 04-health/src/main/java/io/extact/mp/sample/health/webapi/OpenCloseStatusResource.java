package io.extact.mp.sample.health.webapi;

import io.extact.mp.sample.health.status.OpenCloseStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("/status")
public class OpenCloseStatusResource {

    private OpenCloseStatus status;

    @Inject
    public OpenCloseStatusResource(OpenCloseStatus status) {
        this.status = status;
    }

    @GET
    @Path("/open")
    public void openStatus() {
        status.changeStatusToOpen();
    }

    @GET
    @Path("/close")
    public void closeStatus() {
        status.changeStatusToClose();
    }
}
