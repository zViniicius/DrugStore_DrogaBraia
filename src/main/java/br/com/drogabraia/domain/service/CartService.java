package br.com.drogabraia.domain.service;

import br.com.drogabraia.controller.response.CartResponse;
import br.com.drogabraia.controller.response.ProductResponse;
import br.com.drogabraia.domain.dto.CartDTO;
import br.com.drogabraia.domain.model.Cart;
import br.com.drogabraia.domain.model.Product;
import br.com.drogabraia.domain.model.User;
import br.com.drogabraia.domain.repository.CartRepository;
import br.com.drogabraia.domain.repository.ProductRepository;
import br.com.drogabraia.domain.repository.UserRepository;
import br.com.drogabraia.exceptions.ApiExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductService productService;

    public Page<CartResponse> getAllCarts(Pageable pageable) {
        Page<Cart> carts = cartRepository.findAll(pageable);
        return carts.map(this::convertToResponse);
    }

    public CartResponse getCartById(Long id) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            return convertToResponse(cart);
        }
        return null;
    }

    public CartDTO createCart(CartDTO cartDto) {
        Cart cart = convertToEntity(cartDto);
        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    public CartDTO updateCart(Long id, CartDTO cartDto) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            existingCart.getProducts().clear();
            existingCart.getProducts().addAll(convertToEntity(cartDto).getProducts());
            Cart updatedCart = cartRepository.save(existingCart);
            return convertToDto(updatedCart);
        }
        return null;
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public void handleProductToCart(Long cartId, String action, Long productId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalCart.isPresent() && optionalProduct.isPresent() && !action.equalsIgnoreCase("clear")) {
            Cart cart = optionalCart.get();
            Product product = optionalProduct.get();
            switch (action.toLowerCase()){
                case "add":
                    cart.getProducts().add(product);
                    cartRepository.save(cart);
                    break;
                case "remove":
                    cart.getProducts().remove(product);
                    cartRepository.save(cart);
                    break;
                default:
                    break;
            }
        } else if (optionalCart.isPresent() && action.equalsIgnoreCase("clear")) {
            Cart cart = optionalCart.get();
            cart.getProducts().clear();
            cartRepository.save(cart);
        } else {
            throw new ApiExceptionHandler.GenericException(HttpStatus.BAD_REQUEST, "Carrinho ou produto não encontrado");
        }
    }

    private CartDTO convertToDto(Cart cart) {
        CartDTO cartDto = new CartDTO();
        BeanUtils.copyProperties(cart, cartDto, "user", "products");
        if (cart.getUser() != null) {
            cartDto.setUserID(userService.convertToDto(cart.getUser()).getId());
        }
        if (cart.getProducts() != null && !cart.getProducts().isEmpty()) {
            List<Long> productDtos = cart.getProducts().stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());
            cartDto.setProductIDs(productDtos);
    }
        return cartDto;
    }

    private Cart convertToEntity(CartDTO cartDto) {
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDto, cart, "user", "productIDs");
        if (cartDto.getUserID() != null) {
            User user = userRepository.findById(cartDto.getUserID()).orElse(null);
            cart.setUser(user);
        }
        if (cartDto.getProductIDs() != null && !cartDto.getProductIDs().isEmpty()) {
            List<Product> products = productRepository.findAllById(cartDto.getProductIDs());
            cart.setProducts(products);
        }
        return cart;
    }

    private CartResponse convertToResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        BeanUtils.copyProperties(cart, cartResponse, "user", "products");
        if (cart.getUser() != null) {
            cartResponse.setUserID(userService.convertToResponse(cart.getUser()));
        }
        if (cart.getProducts() != null && !cart.getProducts().isEmpty()) {
            List<ProductResponse> product = cart.getProducts().stream()
                    .map(productService::convertToResponse)
                    .collect(Collectors.toList());
            cartResponse.setProducts(product);
        }
        return cartResponse;
    }
}
