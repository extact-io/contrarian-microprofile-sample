package io.extact.mp.sample.config3;

import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.Dependent;

@ConfigProperties(prefix = "sample.db")
@Dependent
public class DbInfo {

    private String url;

    @ConfigProperty(name = "id")
    private String user;

    private String password;

    @ConfigProperty(name = "option.prop1") // 任意設定のためOptional
    private Optional<String> prop1;

    @ConfigProperty(name = "option.prop2") // 任意設定のためOptional
    private Optional<String> prop2;

    @Override
    public String toString() {
        return "DbInfo [url=" + url + ", user=" + user + ", password=" + password + ", prop1=" + prop1 + ", prop2="
                + prop2 + "]";
    }

}
