package br.com.drogabraia.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ManufacturerDTO {

    private Long id;

    @NotNull @NotEmpty(message = "O campo RAZAOSOCIAL é obrigatório")
    private String razaoSocial;

    @NotEmpty(message = "O campo CNPJ é obrigatório")
    @Length(min = 14, max = 14, message = "O campo CNPJ deve conter 14 caracteres")
    private String cnpj;

    private String email;
}
