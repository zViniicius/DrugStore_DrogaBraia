package br.com.drogabraia.controller.response;

import br.com.drogabraia.domain.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private LocalDateTime lastUpdate;
    private String nome;
    private String email;
    private String cpf;
    private String dataNascimento;
    private GenderEnum genero;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cep;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String numero;

}
