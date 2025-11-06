package com.flowerlabvlada;

import com.flowerlabvlada.customer.grpc.CustomerGrpcService;
import com.flowerlabvlada.customer.grpc.CustomerRequest;
import com.flowerlabvlada.customer.grpc.CustomerResponse;
import io.quarkus.grpc.GrpcService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
@Authenticated
public class CustomerService implements CustomerGrpcService {

    private static final Map<Long, Customer> FAKE_REPO = List.of(
            new Customer(1L, "Олександра Христафулова", "sasha.hr@gmail.com", "м. Чернівці, вул. Героїв Майдану, 184", "+380501112233"),
            new Customer(2L, "Анна Іванюк", "anna.ivanyuk@gmail.com", "м. Чернівці, вул. Головна, 109", "+380931234567"),
            new Customer(3L, "Іван Горняк ", "ivan.g@gmail.com", "м. Чернівці, вул. Руська, 12", "+380503498239"),
            new Customer(4L, "Христина Чорней", "kristi.chorney@gmail.com", "м. Тернопіль, вул. Руська, 55", "+380967834092"),
            new Customer(5L, "Дмитро Пономаренко", "dmutro.ponomarenko@gmail.com", "м. Івано-Франківськ, вул. Степана Бандери,6", "+380671234890")
    ).stream().collect(Collectors.toMap(Customer::id, c -> c));


    @Override
    public Uni<CustomerResponse> getCustomerById(CustomerRequest request) {

        long id = request.getCustomerId();
        Optional<Customer> customerOpt = Optional.ofNullable(FAKE_REPO.get(id));

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            CustomerResponse response = CustomerResponse.newBuilder()
                    .setId(customer.id())
                    .setName(customer.name())
                    .setEmail(customer.email())
                    .setAddress(customer.address())
                    .setPhone(customer.phone())
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