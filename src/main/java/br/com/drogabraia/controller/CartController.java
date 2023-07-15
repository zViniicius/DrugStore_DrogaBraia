package br.com.drogabraia.controller;

import br.com.drogabraia.controller.response.CartResponse;
import br.com.drogabraia.domain.dto.CartDTO;
import br.com.drogabraia.domain.service.CartService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Page<CartResponse>> getAllCarts(Pageable pageable) {
        Page<CartResponse> carts = cartService.getAllCarts(pageable);
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(@Valid @PathVariable Long cartId) {
        CartResponse cart = cartService.getCartById(cartId);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart(
            @Parameter(in = ParameterIn.DEFAULT, description = "Objeto carrinho que será adicionado. (ID is not required).", required = true, schema =
            @io.swagger.v3.oas.annotations.media.Schema())
            @Valid @RequestBody CartDTO cartDto) {
        CartDTO createdCart = cartService.createCart(cartDto);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartDTO> updateCart(
            @Valid @PathVariable Long cartId, @Valid @RequestBody CartDTO cartDto) {
        CartDTO updatedCart = cartService.updateCart(cartId, cartDto);
        if (updatedCart != null) {
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCart(@Valid @PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok().body("Produto adicionado ao carrinho com sucesso!");
    }

    @PostMapping("/{cartId}")
    @Transactional
    public ResponseEntity<String> handleProductToCart(
            @PathVariable Long cartId,
            @Parameter(in = ParameterIn.QUERY, name = "action", description = "Actions para incluir, remover e limpar produtos do carrinho. ",
                    required = true, schema =
            @io.swagger.v3.oas.annotations.media.Schema(allowableValues = {"add", "remove", "clear"})
            )
            @RequestParam String action,
            @Valid @RequestParam Long productId,
            UriComponentsBuilder uriBuilder) {

        cartService.handleProductToCart(cartId,action, productId);

        String cartUrl = uriBuilder.path("/carts/{id}").buildAndExpand(cartId).toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(cartUrl));

        return ResponseEntity.ok().headers(headers).body("Produto adicionado ao carrinho com sucesso!");
    }

}
