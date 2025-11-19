package com.flowerlabvlada;

import com.flowerlabvlada.customer.grpc.CustomerGrpcService;
import com.flowerlabvlada.customer.grpc.CustomerRequest;
import com.flowerlabvlada.customer.grpc.CustomerResponse;
import io.quarkus.grpc.GrpcService;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;

@GrpcService
//@Authenticated
public class CustomerService implements CustomerGrpcService {

    @Override
    @Blocking
    public Uni<CustomerResponse> getCustomerById(CustomerRequest request) {
        long id = request.getCustomerId();

        Customer customer = Customer.findById(id);
        if (customer != null) {
            CustomerResponse response = CustomerResponse.newBuilder()
                    .setId(customer.id)
                    .setName(customer.name)
                    .setEmail(customer.email)
                    .setAddress(customer.address)
                    .setPhone(customer.phone)
                    .build();

            return Uni.createFrom().item(response);
        } else {
            return Uni.createFrom().failure(
                    new io.grpc.StatusException(io.grpc.Status.NOT_FOUND
                            .withDescription("Customer not found with ID: " + id))
            );
        }
    }
}