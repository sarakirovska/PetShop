package com.example.petshopnew.service.impl;


import com.example.petshopnew.config.JwtUtil;
import com.example.petshopnew.entity.Cart;
import com.example.petshopnew.entity.Dto.AuthRequest;
import com.example.petshopnew.entity.Dto.AuthResponse;
import com.example.petshopnew.entity.PetShopUser;
import com.example.petshopnew.entity.enums.UserRole;
import com.example.petshopnew.repository.CartRepository;
import com.example.petshopnew.repository.PetShopUserRepository;
import com.example.petshopnew.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PetShopUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CartRepository cartRepository;


    @Override
    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }


        UserRole userRole = UserRole.User;
        if (request.getEmail().equals("admin@example.com")) {
            userRole = UserRole.Admin;
        }


        PetShopUser user = PetShopUser.builder()
                .email(request.getEmail())
                .firstName(request.getEmail())
                .lastName(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();

        PetShopUser savedUser = userRepository.save(user);


        if (savedUser.getId() == null) {
            throw new RuntimeException("Failed to save user");
        }


        Cart cart = new Cart();
        cart.setPetShopUser(savedUser);
        cart.setCartItems(List.of());
        cartRepository.save(cart);

        String token = jwtUtil.generateJwtToken(savedUser.getEmail(), savedUser.getRole());

        return new AuthResponse(token, savedUser.getId(), savedUser.getRole().name());
    }






    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        PetShopUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateJwtToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getId(), user.getRole().name());
    }
}









