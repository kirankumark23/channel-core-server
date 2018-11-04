package com.appliedsni.channel.core.server.user.api.model;

import com.appliedsni.channel.core.server.security.domain.Authority;
import com.appliedsni.channel.core.server.user.domain.RoleActionsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API model for returning user details.
 *
 * @author gauri
 */
@JsonInclude(Include.NON_NULL)
public class QueryUserResult {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Set<RoleActionsEntity> authorities;
    private Boolean active;

    public QueryUserResult() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<RoleActionsEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleActionsEntity> authorities) {
        this.authorities = authorities;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}