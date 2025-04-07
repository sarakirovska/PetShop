package com.example.petshopnew.service;

import com.example.petshopnew.entity.Dto.AuthRequest;
import com.example.petshopnew.entity.Dto.AuthResponse;


public interface AuthService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
}