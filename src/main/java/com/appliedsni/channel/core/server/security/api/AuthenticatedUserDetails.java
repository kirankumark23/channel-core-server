package com.appliedsni.channel.core.server.security.api;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import com.appliedsni.channel.core.server.security.domain.Authority;
import com.appliedsni.channel.core.server.user.domain.RoleActionsEntity;

/**
 * {@link Principal} implementation with a set of {@link Authority}.
 *
 * @author gauri
 */
public final class AuthenticatedUserDetails implements Principal {

    private final String username;
    private final Set<RoleActionsEntity> authorities;

    public AuthenticatedUserDetails(String username, Set<RoleActionsEntity> authorities) {
        this.username = username;
        this.authorities = Collections.unmodifiableSet(authorities);
    }

    public Set<RoleActionsEntity> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }
}