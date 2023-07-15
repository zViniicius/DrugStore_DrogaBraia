package br.com.drogabraia.domain.service;

import br.com.drogabraia.controller.response.ManufacturerResponse;
import br.com.drogabraia.controller.response.ProductResponse;
import br.com.drogabraia.domain.dto.ManufacturerDTO;
import br.com.drogabraia.domain.model.Manufacturer;
import br.com.drogabraia.domain.model.Product;
import br.com.drogabraia.domain.repository.ManufacturerRepository;
import br.com.drogabraia.domain.repository.ProductRepository;
import br.com.drogabraia.exceptions.ApiExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManufacturerService {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductService productService;

    public Page<ManufacturerDTO> getAllManufacturers(Pageable pageable) {
        Page<Manufacturer> manufacturers = manufacturerRepository.findAll(pageable);
        return manufacturers.map(this::convertToDto);

    }

    public ManufacturerResponse getManufacturerById(Long manufacturerId) {
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(manufacturerId);
        if (optionalManufacturer.isPresent()) {
            Manufacturer manufacturer = optionalManufacturer.get();
            ManufacturerResponse manufacturerResponse = convertToResponse(manufacturer);

            List<Product> products = productRepository.findAllByFabricante(manufacturer);
            List<ProductResponse> productResponses = convertProductsToResponse(products);
            manufacturerResponse.setProducts(productResponses);

            return manufacturerResponse;
        }
        return null;
    }

    public ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDto) {
        if (manufacturerRepository.existsByCnpj(manufacturerDto.getCnpj())) {
            throw new ApiExceptionHandler.GenericException(HttpStatus.CONFLICT,"O CNPJ informado já está cadastrado no sistema.");
        }
        Manufacturer manufacturer = convertToEntity(manufacturerDto);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return convertToDto(savedManufacturer);
    }

    public ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO manufacturerDto) {
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(id);
        if (optionalManufacturer.isPresent()) {
            Manufacturer existingManufacturer = optionalManufacturer.get();
            BeanUtils.copyProperties(manufacturerDto, existingManufacturer, "id");
            Manufacturer updatedManufacturer = manufacturerRepository.save(existingManufacturer);
            return convertToDto(updatedManufacturer);
        }
        return null;
    }

    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }

    private ManufacturerDTO convertToDto(Manufacturer manufacturer) {
        ManufacturerDTO manufacturerDto = new ManufacturerDTO();
        BeanUtils.copyProperties(manufacturer, manufacturerDto);
        return manufacturerDto;
    }

    private Manufacturer convertToEntity(ManufacturerDTO manufacturerDto) {
        Manufacturer manufacturer = new Manufacturer();
        BeanUtils.copyProperties(manufacturerDto, manufacturer, "id");
        return manufacturer;
    }


    private ManufacturerResponse convertToResponse(Manufacturer manufacturer) {
        ManufacturerResponse manufacturerResponse = new ManufacturerResponse();
        BeanUtils.copyProperties(manufacturer, manufacturerResponse);

        return manufacturerResponse;
    }

    private List<ProductResponse> convertProductsToResponse(List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponse = productService.convertToResponse(product);
            productResponses.add(productResponse);
        }
        return productResponses;
    }
}
