package br.com.drogabraia.controller;

import br.com.drogabraia.controller.response.ManufacturerResponse;
import br.com.drogabraia.domain.dto.ManufacturerDTO;
import br.com.drogabraia.domain.service.ManufacturerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/manufacturers")
@AllArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<Page<ManufacturerDTO>> getAllManufacturers(Pageable pageable) {
        Page<ManufacturerDTO> manufacturers = manufacturerService.getAllManufacturers(pageable);
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponse> getManufacturerById(@Valid @PathVariable Long id) {
        ManufacturerResponse manufacturer = manufacturerService.getManufacturerById(id);
        if (manufacturer != null) {
            return new ResponseEntity<>(manufacturer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ManufacturerDTO> createManufacturer(@Valid @RequestBody ManufacturerDTO manufacturerDto) {
        ManufacturerDTO createdManufacturer = manufacturerService.createManufacturer(manufacturerDto);
        return new ResponseEntity<>(createdManufacturer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(
            @Valid @PathVariable Long id, @Valid @RequestBody ManufacturerDTO manufacturerDto) {
        ManufacturerDTO updatedManufacturer = manufacturerService.updateManufacturer(id, manufacturerDto);
        if (updatedManufacturer != null) {
            return new ResponseEntity<>(updatedManufacturer, HttpStatus.OK);
        }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.ok().body("Fabricante deletado com sucesso!");
    }
}
