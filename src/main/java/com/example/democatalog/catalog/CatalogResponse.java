package com.example.democatalog.catalog;

import com.google.cloud.datacatalog.v1.IntegratedSystem;
import com.google.cloud.datacatalog.v1.SearchResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogResponse implements Serializable {
    private SearchResultType searchResultType;
    private String searchResultSubType;
    private String relativeResourceName;
    private String linkedResource;
    private Long modifyTime;
    private IntegratedSystem integratedSystem;
    private String description;
}
