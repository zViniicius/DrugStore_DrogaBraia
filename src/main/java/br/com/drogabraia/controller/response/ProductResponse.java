package br.com.drogabraia.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
    private long id;
    private String tipoProduto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String principioAtivo;

    private String descricao;
    private String marca;
    private String fabricante;
    private double preco;
    private double desconto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String classificacao;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String viaAdministracao;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String indicacao;

    private String imagem;


}
