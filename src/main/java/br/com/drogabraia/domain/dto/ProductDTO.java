package br.com.drogabraia.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter @Setter
@NoArgsConstructor
public class ProductDTO {
    @JsonIgnore
    private Long id;

    @NotEmpty(message = "O campo DESCRIÇAO é obrigatório")
    private String descricao;

    @NotNull(message = "O campo PREÇO é obrigatório")
    private double preco;

    private double desconto;
    private String marca;
    private String imagem;

    @NotNull @NotEmpty(message = "O campo CNPJ FABRICANTE é obrigatório")
    @Length(min = 14, max = 14, message = "O campo CNPJ FABRICANTE deve conter 14 caracteres")
    private String cnpjFabricante;

    @NotNull @NotEmpty(message = "O campo TIPO DE PRODUTO é obrigatório")
    private String tipoProduto;

    private String classificacao;
    private String viaAdministracao;
    private String principioAtivo;
    private String indicacao;

}
