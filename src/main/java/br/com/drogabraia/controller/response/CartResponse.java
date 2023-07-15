package br.com.drogabraia.controller.response;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CartResponse {
    private Long id;
    private LocalDateTime dataCriacao;
    private UserResponse userID;
    private List<ProductResponse> products;
}
