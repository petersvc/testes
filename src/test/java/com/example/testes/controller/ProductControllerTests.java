package com.example.testes.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.testes.model.Product;
import com.example.testes.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

    @InjectMocks
    private ProductController controller;

    @Mock
    private ProductService service;

    private MockMvc mockMvc;

    private Product product;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.product = new Product();
        this.product.setId(1L);
        this.product.setName("Test Product");
        this.product.setPrice(10.0);
    }
    
    @Nested
    class Find {

        @Test
        void idShouldBeValid() throws Exception {
            mockMvc.perform(get("/products/abc"))
                   .andExpect(status().isBadRequest());
        }
    

        @Test
        void shouldReturnProductWhenIdIsValid() throws Exception {            
            // Configurar comportamento do serviço
            when(service.findById(1L)).thenReturn(product);
            
            // Executar e verificar
            mockMvc.perform(get("/products/1"))
                   .andExpect(status().isOk());
        }

        @Test
        void shouldReturnNotFoundWhenNoneProductWasFound() throws Exception {
            // Configurar o serviço para lançar ResponseStatusException quando ID 2 for consultado
            when(service.findById(2L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
            
            // Executar e verificar
            mockMvc.perform(get("/products/2"))
                .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Save {

        @Test
        void shouldReturnCreated() throws Exception {
            // Executar e verificar
            mockMvc.perform(post("/products")
                    .param("name", "Test")
                    .param("price", "10.0"))
                    .andExpect(status().isCreated());
        }

        @Test
        void shouldReturnBadRequestWhenNameIsMissing() throws Exception {
            // Executar e verificar
            mockMvc.perform(post("/products?price=10.0"))
                   .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnBadRequestWhenPriceIsMissing() throws Exception {
            // Executar e verificar
            mockMvc.perform(post("/products?name=Test"))
                   .andExpect(status().isBadRequest())
                   .andExpect(result -> assertEquals(
                        "Required request parameter 'price' for method parameter type Double is not present",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()
                   ));
        }

        @Test
        void shouldReturnBadRequestWhenNameAndPriceAreMissing() throws Exception {
            // Executar e verificar
            mockMvc.perform(post("/products"))
                   .andExpect(status().isBadRequest());
        }
    }
}
