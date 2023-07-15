package br.com.drogabraia.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ManufacturerResponse {
    private Long id;
    private String razaoSocial;
    private String cnpj;
    private List<ProductResponse> products;

}
