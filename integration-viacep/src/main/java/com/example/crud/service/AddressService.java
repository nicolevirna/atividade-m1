package com.example.crud.service;

import com.example.crud.domain.address.Address;
import com.example.crud.domain.product.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import com.example.crud.domain.product.ProductRepository;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



@Service
public class AddressService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Autowired
    private final ProductRepository repository;


    public AddressService(RestTemplate restTemplate, ObjectMapper objectMapper, ProductRepository repository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.repository = repository;

    }

    public boolean findAddress(String id, String cep) {
        String url = "https://viacep.com.br/ws/{cep}/json/";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("cep", cep);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, uriVariables);
        try {
            Address address = objectMapper.readValue(response.getBody(), Address.class);
            String city = address.getLocalidade();
            Optional<Product> product = repository.findById(id);

            if(product.isPresent()){
                Product foundProduct = product.get();
                return foundProduct.getDistribution_center().equals(city);
            }
            return false;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }
}

