package org.restaurante.msvc_erp.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GraphQLResponse {

    private Map<String, Object> data;
    private List<GraphQLError> errors;

    @Data
    public static class GraphQLError {
        private String message;
        private List<String> path;
    }

    public TenantInfoDTO getTenantData() {
        if (data != null && data.get("tenantById") != null) {

            return new org.springframework.core.convert.support.DefaultConversionService()
                    .convert(data.get("tenantById"), TenantInfoDTO.class);
        }
        return null;
    }

}
