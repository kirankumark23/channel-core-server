package com.appliedsni.channel.core.server.security.api;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.appliedsni.channel.core.server.security.domain.Authority;
import com.appliedsni.channel.core.server.user.domain.RoleActionsEntity;

/**
 * Model that holds details about an authentication token.
 *
 * @author gauri
 */
public final class AuthenticationTokenDetails {

    private final String id;
    private final String username;
    private final Set<RoleActionsEntity> authorities;
    private final ZonedDateTime issuedDate;
    private final ZonedDateTime expirationDate;
    private final int refreshCount;
    private final int refreshLimit;

    private AuthenticationTokenDetails(String id, String username, Set<RoleActionsEntity> authorities, ZonedDateTime issuedDate, ZonedDateTime expirationDate, int refreshCount, int refreshLimit) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
        this.refreshCount = refreshCount;
        this.refreshLimit = refreshLimit;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<RoleActionsEntity> getAuthorities() {
        return authorities;
    }

    public ZonedDateTime getIssuedDate() {
        return issuedDate;
    }

    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    public int getRefreshLimit() {
        return refreshLimit;
    }

    /**
     * Check if the authentication token is eligible for refreshment.
     *
     * @return
     */
    public boolean isEligibleForRefreshment() {
        return refreshCount < refreshLimit;
    }

    /**
     * Builder for the {@link AuthenticationTokenDetails}.
     */
    public static class Builder {

        private String id;
        private String username;
        private Set<RoleActionsEntity> authorities;
        private ZonedDateTime issuedDate;
        private ZonedDateTime expirationDate;
        private int refreshCount;
        private int refreshLimit;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withAuthorities(Set<RoleActionsEntity> authorities) {
            //this.authorities = Collections.unmodifiableSet(authorities == null ? new HashSet<>() : authorities);
            return this;
        }

        public Builder withIssuedDate(ZonedDateTime issuedDate) {
            this.issuedDate = issuedDate;
            return this;
        }

        public Builder withExpirationDate(ZonedDateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder withRefreshCount(int refreshCount) {
            this.refreshCount = refreshCount;
            return this;
        }

        public Builder withRefreshLimit(int refreshLimit) {
            this.refreshLimit = refreshLimit;
            return this;
        }

        public AuthenticationTokenDetails build() {
            return new AuthenticationTokenDetails(id, username, authorities, issuedDate, expirationDate, refreshCount, refreshLimit);
        }
    }
}