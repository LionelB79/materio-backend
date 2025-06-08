package com.materio.materio_backend.business.service;


import com.materio.materio_backend.configs.JwtUtils;
import com.materio.materio_backend.configs.UserDetailsImpl;
import com.materio.materio_backend.dto.User.JwtResponse;
import com.materio.materio_backend.dto.User.LoginRequest;
import com.materio.materio_backend.dto.User.SignupRequest;
import com.materio.materio_backend.jpa.entity.user.EnumRole;
import com.materio.materio_backend.jpa.entity.user.Role;
import com.materio.materio_backend.jpa.entity.user.User;
import com.materio.materio_backend.jpa.repository.RoleRepository;
import com.materio.materio_backend.jpa.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service pour gérer l'authentification et l'inscription des utilisateurs
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;


    // Méthode pour initialiser les rôles avec des IDs spécifiques
    @PostConstruct
    public void initRoles() {
        try {
            // Vérifier chaque rôle individuellement et le créer seulement s'il n'existe pas déjà
            if (roleRepository.findByName(EnumRole.ROLE_USER).isEmpty()) {
                Role userRole = new Role(EnumRole.ROLE_USER);
                roleRepository.save(userRole);
            }

            if (roleRepository.findByName(EnumRole.ROLE_ADMIN).isEmpty()) {
                Role adminRole = new Role(EnumRole.ROLE_ADMIN);
                roleRepository.save(adminRole);
            }
        } catch (Exception e) {
            // Log l'erreur mais ne pas faire échouer l'initialisation de l'application
            System.err.println("Erreur lors de l'initialisation des rôles: " + e.getMessage());
        }
    }

    /**
     * Authentifie un utilisateur et génère un token JWT
     */
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                roles);
    }

    /**
     * Inscrit un nouvel utilisateur
     */
    @Transactional
    public void registerUser(SignupRequest signUpRequest) throws Exception {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new Exception("Ce nom d'utilisateur est déjà pris!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new Exception("Cet email est déjà utilisé!");
        }

        // Création du nouvel utilisateur
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());

        // Attribution des rôles en utilisant les IDs
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            // Rôle par défaut - utilisateur (ID 1)
            Role userRole = roleRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        // Rôle admin (ID 2)
                        Role adminRole = roleRepository.findById(2)
                                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
                        roles.add(adminRole);
                        break;
                    default:
                        // Rôle utilisateur (ID 1)
                        Role userRole = roleRepository.findById(1)
                                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
    }
}