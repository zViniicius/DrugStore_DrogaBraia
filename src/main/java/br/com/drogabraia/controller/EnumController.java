package br.com.drogabraia.controller;

import br.com.drogabraia.domain.enums.AdministrationRouteEnum;
import br.com.drogabraia.domain.enums.ClassificationEnum;
import br.com.drogabraia.domain.enums.GenderEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EnumController {

    @GetMapping("/enum/admin-routes")
    public ResponseEntity<List<String>> getViaAdministracao() {
        List<String> viaAdministracao = Arrays.stream(AdministrationRouteEnum.values())
                .map(AdministrationRouteEnum::name)
                .collect(Collectors.toList());
        if (viaAdministracao.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viaAdministracao);
    }

    @GetMapping("/enum/classifications")
    public ResponseEntity<List<String>> getClassificacao() {
        List<String> classificacao = Arrays.stream(ClassificationEnum.values())
                .map(ClassificationEnum::name)
                .collect(Collectors.toList());
        if (classificacao.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(classificacao);
    }

    @GetMapping("/enum/genders")
    public ResponseEntity<List<String>> getGeros() {
        List<String> generos = Arrays.stream(GenderEnum.values())
                .map(GenderEnum::name)
                .collect(Collectors.toList());
        if (generos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(generos);
    }
}
