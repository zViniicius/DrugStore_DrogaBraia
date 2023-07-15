package br.com.drogabraia.domain.service;


import br.com.drogabraia.controller.response.ProductResponse;
import br.com.drogabraia.domain.dto.ProductDTO;
import br.com.drogabraia.domain.model.Manufacturer;
import br.com.drogabraia.domain.model.Product;
import br.com.drogabraia.domain.repository.ManufacturerRepository;
import br.com.drogabraia.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;


    public Page<ProductResponse> getAllProducts(Long id,
                                                Integer fabricanteId,
                                                String tipoProduto,
                                                String descricao,
                                                String marca,
                                                Double maxPrice,
                                                Double minPrice,
                                                Integer minDiscount,
                                                Pageable pageable) {
        Specification<Product> specification = Specification.where(null);

        if (id != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), id));
        }

        if (fabricanteId != null) {
            Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById((long) fabricanteId);
            if (optionalManufacturer.isPresent()) {
                Manufacturer manufacturer = optionalManufacturer.get();
                specification = specification.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("fabricante"), manufacturer));
            }
        }

        if (tipoProduto != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("tipoProduto"), "%" + tipoProduto + "%"));
        }

        if (descricao != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("descricao"), "%" + descricao + "%"));
        }

        if (marca != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("marca"), "%" + marca + "%"));
        }

        if (maxPrice != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("preco"), maxPrice));
        }

        if (minPrice != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), minPrice));
        }

        if (minDiscount != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("desconto"), minDiscount));
        }

        Page<Product> products = productRepository.findAll(specification, pageable);
        return products.map(this::convertToResponse);
    }

    public ProductDTO getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return convertToDto(product);
        }
        return null;
    }

    public ProductDTO createProduct(ProductDTO productDto) {
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            BeanUtils.copyProperties(productDto, existingProduct, "id");
            Product updatedProduct = productRepository.save(existingProduct);
            return convertToDto(updatedProduct);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    protected ProductDTO convertToDto(Product product) {
        ProductDTO productDto = new ProductDTO();
        Product.Medicament medicament = new Product.Medicament();
        Product.Beauty beauty = new Product.Beauty();

        if (product.getTipoProduto().equals("MEDICAMENTO")) {
            medicament = (Product.Medicament) product;
            productDto.setClassificacao(medicament.getClassificacao());
            productDto.setViaAdministracao(medicament.getViaAdministracao());
            productDto.setPrincipioAtivo(medicament.getPrincipioAtivo());
        } else {
            beauty = (Product.Beauty) product;
            productDto.setIndicacao(beauty.getIndicacao());
        }
        productDto.setCnpjFabricante(product.getFabricante().getCnpj());
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    protected Product convertToEntity(ProductDTO productDto) {
        Product product;
        if ("MEDICAMENTO".equalsIgnoreCase(productDto.getTipoProduto())) {
            Product.Medicament medicament = new Product.Medicament();
            medicament.setClassificacao(productDto.getClassificacao().toUpperCase());
            medicament.setViaAdministracao(productDto.getViaAdministracao().toUpperCase());
            medicament.setPrincipioAtivo(productDto.getPrincipioAtivo());
            product = medicament;

        } else {
            Product.Beauty beauty = new Product.Beauty();
            beauty.setIndicacao(productDto.getIndicacao().toUpperCase());
            product = beauty;
        }
        product.setFabricante(convertToManufacturerEntity(productDto));
        BeanUtils.copyProperties(productDto, product, "id");
        return product;
    }

    protected ProductResponse convertToResponse (Product product) {
        ProductResponse productResponse = new ProductResponse();

        if ("MEDICAMENTO".equalsIgnoreCase(product.getTipoProduto())) {
            Product.Medicament medicament = (Product.Medicament) product;
            productResponse.setClassificacao(medicament.getClassificacao());
            productResponse.setViaAdministracao(medicament.getViaAdministracao());
            productResponse.setPrincipioAtivo(medicament.getPrincipioAtivo());
        } else {
            Product.Beauty beauty = (Product.Beauty) product;
            productResponse.setIndicacao(beauty.getIndicacao());
        }
        productResponse.setFabricante(product.getFabricante().getRazaoSocial());
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    private Manufacturer convertToManufacturerEntity(ProductDTO productDto) {
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findByCnpj(productDto.getCnpjFabricante());
        return optionalManufacturer.orElseThrow();

    }

}
