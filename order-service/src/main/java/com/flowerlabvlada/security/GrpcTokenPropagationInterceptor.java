package com.flowerlabvlada.security;

import io.grpc.*;
import io.quarkus.grpc.GlobalInterceptor;
import io.quarkus.oidc.AccessTokenCredential;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@GlobalInterceptor
@ApplicationScoped
public class GrpcTokenPropagationInterceptor implements ClientInterceptor {

    @Inject
    AccessTokenCredential credential;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {

        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {

                if (credential != null && credential.getToken() != null) {

                    Metadata.Key<String> authKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
                    headers.put(authKey, "Bearer " + credential.getToken());
                }

                super.start(responseListener, headers);
            }
        };
    }
}