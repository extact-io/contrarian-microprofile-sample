package io.extact.mp.sample.jwt.auth;

import java.util.Set;

public interface AuthUser {
    String getUserId();
    String getName();
    Set<String> getGroups();
}
