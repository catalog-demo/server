package com.example.democatalog.catalog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DemoCatalog {

    @GetMapping("/search")
    public ResponseEntity<?> searchBySyntax(@RequestParam("syntax") String syntax){
        String projectId = "aeriscom-acpcmn-dev-202006";
        try {
            CatalogService catalogService = new CatalogService();
            List<CatalogResponse> catalogResponses = catalogService.searchCatalog(projectId, syntax);
            ResponseEntity<List<CatalogResponse>> response = new ResponseEntity<>(catalogResponses, HttpStatus.OK);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
