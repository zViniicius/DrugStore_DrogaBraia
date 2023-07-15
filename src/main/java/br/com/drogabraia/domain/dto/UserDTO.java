package br.com.drogabraia.domain.dto;

import br.com.drogabraia.domain.enums.GenderEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {

    private Long id;

    @NotNull @NotEmpty(message = "O campo NOME é obrigatório")
    private String nome;

    @NotNull @NotEmpty(message = "O campo EMAIL é obrigatório")
    @Email(message = "O campo EMAIL deve ser válido")
    private String email;

    @NotNull @NotEmpty(message = "O campo SENHA é obrigatório")
    private String senha;

    @NotNull @NotEmpty(message = "O campo CPF é obrigatório")
    @Length(min=11,max = 11, message = "O campo CPF deve conter no 11 caracteres")
    private String cpf;

    @Length(max = 10, message = "O campo DATANASCIMENTO deve conter no máximo 10 caracteres")
    @NotEmpty(message = "O campo DATANASCIMENTO é obrigatório")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "O campo DATANASCIMENTO deve estar no formato DD/MM/YYYY")
    private String dataNascimento;

    @NotNull(message = "O campo GENERO é obrigatório")
    private GenderEnum genero;

    @Length(max = 8, message = "O campo CEP deve conter no máximo 8 caracteres")
    private String cep;
    @Length(max = 5, message = "O campo NUMERO deve conter no máximo 5 caracteres")
    private String numero;
}
