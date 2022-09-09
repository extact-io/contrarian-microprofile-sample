package io.extact.mp.sample.health.webapi;

import io.extact.mp.sample.health.status.AppStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("/status")
public class AppStatusResource {

    private AppStatus status;

    @Inject
    public AppStatusResource(AppStatus status) {
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
