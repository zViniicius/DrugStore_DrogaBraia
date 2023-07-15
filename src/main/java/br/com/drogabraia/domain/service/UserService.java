package br.com.drogabraia.domain.service;

import br.com.drogabraia.controller.response.UserResponse;
import br.com.drogabraia.domain.dto.UserDTO;
import br.com.drogabraia.domain.model.User;
import br.com.drogabraia.domain.repository.UserRepository;
import br.com.drogabraia.exceptions.ApiExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::convertToResponse);
    }

    public UserResponse getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return convertToResponse(user);
        }
        else {
            throw new ApiExceptionHandler.GenericException(HttpStatus.NOT_FOUND,"Usuário não encontrado.");
        }
    }

    public UserResponse createUser(UserDTO userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ApiExceptionHandler.GenericException(HttpStatus.CONFLICT,"O Email informado já está cadastrado no sistema.");
        } else if (userRepository.existsByCpf(userDto.getCpf())) {
            throw new ApiExceptionHandler.GenericException(HttpStatus.CONFLICT,"O CPF informado já está cadastrado no sistema.");
        }
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            BeanUtils.copyProperties(userDto, existingUser,"id");
            User updatedUser = userRepository.save(existingUser);
            return convertToDto(updatedUser);
        }
        else {
            throw new ApiExceptionHandler.GenericException(HttpStatus.NOT_FOUND,"Usuário não encontrado.");
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    protected UserDTO convertToDto(User user) {
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    protected User convertToEntity(UserDTO userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user,"id");
        user.setSenha(encryptPassword(userDto.getSenha()));
        return user;
    }

    protected UserResponse convertToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        userResponse.setLastUpdate(user.getDataAtualizacao());
        return userResponse;
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = hash.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
