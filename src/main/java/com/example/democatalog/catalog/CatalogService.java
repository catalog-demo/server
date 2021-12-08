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
                        .linkedResource(parseToLinkResource(result.getLinkedResource()))
                        .modifyTime(result.getModifyTime().getSeconds())
                        .integratedSystem(result.getIntegratedSystem())
                        .description(result.getDescription())
                        .build();
                catalogResponses.add(catalogResponse);
            }
        }
        return catalogResponses;
    }

    private String parseToLinkResource(String linkResource){
        String temp = linkResource.substring(2);
        String[] tempArray = temp.split("/");
        String projectName = tempArray[2];
        String dataset = tempArray[4];
        if(linkResource.contains("tables")){
            String tableName = tempArray[6];
            return String.format("https://console.cloud.google.com/bigquery?project=%s&d=%s&p=%s&t=%s&page=table&" +
                    "ws=!1m5!1m4!4m3!1s%s!2s%s!3s%s", projectName, dataset, projectName, tableName, projectName, dataset, tableName);
        }else{
            return String.format("https://console.cloud.google.com/bigquery?project=%s&d=%s&p=%s&page=dataset&" +
                    "ws=!1m4!1m3!3m2!1s%s!2s%s", projectName, dataset, projectName, projectName, dataset);
        }
    }
}
