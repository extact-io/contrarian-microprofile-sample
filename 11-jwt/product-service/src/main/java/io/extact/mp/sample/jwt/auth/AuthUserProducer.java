package io.extact.mp.sample.jwt.auth;

import java.util.Set;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;

import org.eclipse.microprofile.jwt.JsonWebToken;

@Dependent
@Priority(Interceptor.Priority.APPLICATION)
public class AuthUserProducer {
    @Inject
    private JsonWebToken jsonWebToken;

    @Produces
    public AuthUser authUser() {
        return new AuthUserImpl(jsonWebToken);
    }

    static class AuthUserImpl implements AuthUser {
        private JsonWebToken jwt;
        AuthUserImpl(JsonWebToken jwt) {
            this.jwt = jwt;
        }
        @Override
        public String getUserId() {
            return jwt.getSubject();
        }
        @Override
        public String getName() {
            return jwt.getClaim("name").toString(); // maybe a helidon bug..
        }
        @Override
        public Set<String> getGroups() {
            return jwt.getGroups();
        }
    }
}
