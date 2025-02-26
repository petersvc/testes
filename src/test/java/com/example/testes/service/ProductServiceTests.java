package com.example.testes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.testes.model.Product;
import com.example.testes.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private Product product;

    @BeforeEach
    public void setup() {
        this.product = new Product();
        this.product.setId(1L);
        this.product.setName("Product 1");
        this.product.setPrice(10.0);
    }

    @Nested
    class FindById {

        @Test
        @DisplayName("Should find a product by id")
        public void shouldFindById() {
            // Mockar o método findById do repositório
            when(repository.findById(1L)).thenReturn(Optional.of(product));

            // Chamar o método a ser testado
            var foundProduct = service.findById(1L);

            // Verificar se o método findById foi chamado corretamente
            verify(repository).findById(1L);
            verifyNoMoreInteractions(repository);

            // Verificar se o produto encontrado é o esperado
            assertSame(product, foundProduct);
            assertEquals(1L, foundProduct.getId());
            assertEquals("Product 1", foundProduct.getName());
            assertEquals(10.0, foundProduct.getPrice());
        }

        @Test
        @DisplayName("Should throw exception when product not found")
        public void shouldThrowExceptionWhenProductNotFound() {
            // Mockar o método findById do repositório para retornar nulo
            when(repository.findById(2L)).thenReturn(Optional.empty());

            // Chamar o método a ser testado e verificar se uma exceção é lançada
            var exception = assertThrows(ResponseStatusException.class, () -> service.findById(2L));

            // Verificar se o método findById foi chamado corretamente
            verify(repository, times(1)).findById(2L);

            // Verificar se nenhuma outra interação com o repositório foi feita
            verifyNoMoreInteractions(repository);

            // Verificar se a razão da exceção está correta
            assertEquals("Product not found", exception.getReason());

            // Verificar se o status da exceção está correto
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        }        
    }

    @Nested
    class FindAll {

        @Test
        @DisplayName("Should find all products")
        public void shouldFindAll() {
            // Mockar o método findAll do repositório
            var products = new ArrayList<Product>();
            products.add(product);

            when(repository.findAll()).thenReturn(products);

            // Chamar o método a ser testado
            var foundProducts = service.findAll();

            // Verificar se o método findAll foi chamado corretamente
            verify(repository, times(1)).findAll();
            verifyNoMoreInteractions(repository);

            // Verificar se os produtos encontrados são os esperados
            assertEquals(1, foundProducts.size());
            assertSame(products, foundProducts);
            assertEquals(1L, foundProducts.get(0).getId());
            assertEquals("Product 1", foundProducts.get(0).getName());
            assertEquals(10.0, foundProducts.get(0).getPrice());
        }
    }

    @Nested
    class Save {

        @Test
        @DisplayName("Should save a product")
        public void shouldSave() {
            // Mockar o método save do repositório
            when(repository.save(any(Product.class))).thenReturn(product);
            // when(repository.save(product)).thenReturn(product);

            // Chamar o método a ser testado
            var savedProduct = service.save("Product 1", 10.0);

            // Verificar se o método save foi chamado corretamente
            verify(repository, times(1)).save(any(Product.class));
            verifyNoMoreInteractions(repository);

            // Verificar se o produto salvo é o esperado
            assertSame(product, savedProduct);
            assertEquals(1L, savedProduct.getId());
            assertEquals("Product 1", savedProduct.getName());
            assertEquals(10.0, savedProduct.getPrice());
        }
    }
    
}
