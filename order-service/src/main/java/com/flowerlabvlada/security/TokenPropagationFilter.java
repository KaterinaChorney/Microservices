package com.flowerlabvlada.security;

import io.quarkus.oidc.AccessTokenCredential;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class TokenPropagationFilter implements ClientRequestFilter {

    @Inject
    AccessTokenCredential credential;

    @Override
    public void filter(ClientRequestContext requestContext) {

        if (credential.getToken() == null) {
            return;
        }

        requestContext.getHeaders().add("Authorization", "Bearer " + credential.getToken());
    }
}