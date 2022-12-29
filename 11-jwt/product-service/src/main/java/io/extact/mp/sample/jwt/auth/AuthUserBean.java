package io.extact.mp.sample.jwt.auth;

import java.util.Set;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

@RequestScoped
public class AuthUserBean implements AuthUser {

    @Inject
    @Claim(standard = Claims.sub)
    private String userId;
    @Inject
    @Claim("name")
    private String name;
    @Inject
    @Claim(standard = Claims.groups)
    private Set<String> groups;

    @Override
    public String getUserId() {
        return userId;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public Set<String> getGroups() {
        return this.groups;
    }
}
