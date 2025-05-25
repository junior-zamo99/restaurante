package org.restaurante.msvc_erp.Client;

import org.restaurante.msvc_erp.dto.GraphQLRequest;
import org.restaurante.msvc_erp.dto.GraphQLResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${auth.service.url}")
public interface AuthServiceClient {
    @PostMapping
    GraphQLResponse executeGraphQL(@RequestBody GraphQLRequest request);
}
