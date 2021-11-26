package com.example.democatalog.catalog;

import com.google.cloud.datacatalog.v1.DataCatalogClient;
import com.google.cloud.datacatalog.v1.SearchCatalogRequest;
import com.google.cloud.datacatalog.v1.SearchCatalogResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {
    public List<CatalogResponse> searchCatalog(String projectId, String query) throws IOException {
        // Create a scope object setting search boundaries to the given organization.
        // Scope scope = Scope.newBuilder().addIncludeOrgIds(orgId).build();

        // Alternatively, search using project scopes.
        SearchCatalogRequest.Scope scope = SearchCatalogRequest.Scope.newBuilder().addIncludeProjectIds(projectId).build();

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        List<CatalogResponse> catalogResponses = new ArrayList<>();
        try (DataCatalogClient dataCatalogClient = DataCatalogClient.create()) {
            // Search the catalog.
            SearchCatalogRequest searchCatalogRequest =
                    SearchCatalogRequest.newBuilder().setScope(scope).setQuery(query).build();
            DataCatalogClient.SearchCatalogPagedResponse response = dataCatalogClient.searchCatalog(searchCatalogRequest);
            for (SearchCatalogResult result : response.iterateAll()) {
                CatalogResponse catalogResponse = CatalogResponse.builder()
                        .searchResultType(result.getSearchResultType())
                        .searchResultSubType(result.getSearchResultSubtype())
                        .relativeResourceName(result.getRelativeResourceName())
                        .linkedResource(result.getLinkedResource())
                        .modifyTime(result.getModifyTime().getSeconds())
                        .integratedSystem(result.getIntegratedSystem())
                        .description(result.getDescription())
                        .build();
                catalogResponses.add(catalogResponse);
            }
        }
        return catalogResponses;
    }
}
