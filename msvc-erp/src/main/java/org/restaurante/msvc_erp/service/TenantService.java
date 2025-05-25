package org.restaurante.msvc_erp.service;

import org.restaurante.msvc_erp.Client.AuthServiceClient;
import org.restaurante.msvc_erp.dto.GraphQLRequest;
import org.restaurante.msvc_erp.dto.GraphQLResponse;
import org.restaurante.msvc_erp.dto.TenantInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TenantService {

    private final AuthServiceClient authServiceClient;

    public TenantService(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @Cacheable(value = "tenantInfo", key = "#tenantId")
    public TenantInfoDTO getTenantInfo(String tenantId){
        String query = "query($id: ID!) { tenantById(id: $id) { tenantId nombre razonSocial estado } }";
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", tenantId);


        GraphQLRequest request = GraphQLRequest.builder()
                .query(query)
                .variables(variables)
                .build();


        GraphQLResponse response = authServiceClient.executeGraphQL(request);


        return response.getTenantData();
    }
    public boolean existeTenant(String tenantId) {
        try {
            TenantInfoDTO tenant = getTenantInfo(tenantId);
            return tenant != null && tenant.getTenantId() != null;
        } catch (Exception e) {
            return false;
        }
    }
}
