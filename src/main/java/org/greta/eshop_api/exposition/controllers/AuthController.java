package org.greta.eshop_api.exposition.controllers;

import org.greta.eshop_api.exposition.dtos.LoginUserRequestDTO;
import org.greta.eshop_api.exposition.dtos.LoginUserResponseDTO;
import org.greta.eshop_api.exposition.dtos.RegisterUserRequestDTO;
import org.greta.eshop_api.persistence.entities.UserEntity;
import org.greta.eshop_api.persistence.repositories.UserRepository;
import org.greta.eshop_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal UserEntity user){
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("email", user.getEmail());
        userInfo.put("role", user.getRole().name());
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterUserRequestDTO request){
        boolean alreadyExists = userRepository.existsByEmail(request.email());
        if(alreadyExists){
            return ResponseEntity.badRequest().body(Map.of("message", "Cet email est déjà utilisé !"));
        }

        UserEntity user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Utilisateur inscrit avec succès !"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> loginUser(@RequestBody LoginUserRequestDTO request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserEntity authenticatedUser = (UserEntity) authentication.getPrincipal();
        String token = jwtUtil.generateToken(authenticatedUser);

        LoginUserResponseDTO response = LoginUserResponseDTO.fromEntity(token, authenticatedUser);
        return ResponseEntity.ok(response);
    }
}
