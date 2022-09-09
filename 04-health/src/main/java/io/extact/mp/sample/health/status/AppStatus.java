package io.extact.mp.sample.health.status;

import static io.extact.mp.sample.health.status.AppStatus.Status.*;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppStatus {

    private Status currentStatus = CLOSE;

    enum Status {
        OPEN, CLOSE
    }

    public void changeStatusToOpen() {
        this.currentStatus = OPEN;
    }

    public void changeStatusToClose() {
        this.currentStatus = CLOSE;
    }

    public boolean isOpen() {
        return currentStatus == OPEN;
    }
}
