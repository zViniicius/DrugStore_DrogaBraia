package br.com.drogabraia.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartDTO {
    private Long id;
    @NotNull(message = "O campo 'userID' é obrigatório.")
    private Long userID;
    @NotNull(message = "O campo 'productIDs' é obrigatório.")
    private List<Long> productIDs;
}
