package io.extact.mp.sample.config3;

import org.eclipse.microprofile.config.inject.ConfigProperties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ConfigBean {

    @Inject
    @ConfigProperties
    private DbInfo sampleDbInfo;

    @Inject
    @ConfigProperties(prefix = "test.db")
    private DbInfo testDbInfo;

    public DbInfo getSampleDbInfo() {
        return this.sampleDbInfo;
    }

    public DbInfo getTestDbInfo() {
        return this.testDbInfo;
    }
}
