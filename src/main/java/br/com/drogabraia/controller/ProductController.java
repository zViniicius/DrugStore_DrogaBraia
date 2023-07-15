package br.com.drogabraia.controller;

import br.com.drogabraia.controller.response.ProductResponse;
import br.com.drogabraia.domain.dto.ProductDTO;
import br.com.drogabraia.domain.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String tipoProduto,
            @RequestParam(required = false) Integer fabricanteId,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false, defaultValue = "0") Integer minDiscount,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String orderBy,
            Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), size, Sort.Direction.fromString(orderBy), sortBy);

        Page<ProductResponse> products = productService.getAllProducts(id,fabricanteId,tipoProduto,descricao, marca, maxPrice, minPrice, minDiscount,
                pageable);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@Valid @PathVariable Long productId) {
        ProductDTO product = productService.getProductById(productId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDto) {
        ProductDTO createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @Valid @PathVariable Long productId, @Valid @RequestBody ProductDTO productDto) {
        ProductDTO updatedProduct = productService.updateProduct(productId, productDto);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@Valid @PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().body("Produto deletado com sucesso!");
    }
}
