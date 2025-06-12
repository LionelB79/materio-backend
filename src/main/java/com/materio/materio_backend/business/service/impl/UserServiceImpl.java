package com.materio.materio_backend.business.service.impl;

import com.materio.materio_backend.business.service.UserService;
import com.materio.materio_backend.configs.UserDetailsImpl;
import com.materio.materio_backend.dto.User.UserProfileResponse;
import com.materio.materio_backend.dto.User.UserUpdateRequest;
import com.materio.materio_backend.jpa.entity.user.User;
import com.materio.materio_backend.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les utilisateurs
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Récupère le profil de l'utilisateur actuellement connecté
     */
    @Override
    public UserProfileResponse getCurrentUserProfile() {
        // Récupérer l'utilisateur connecté
        UserDetailsImpl userDetails = getCurrentUserDetails();

        // Rechercher l'utilisateur dans la base de données
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return mapUserToProfileResponse(user);
    }

    /**
     * Met à jour le profil de l'utilisateur actuellement connecté
     */
    @Transactional
    @Override
    public UserProfileResponse updateCurrentUserProfile(UserUpdateRequest updateRequest) {
        // Récupérer l'utilisateur connecté
        UserDetailsImpl userDetails = getCurrentUserDetails();

        // Rechercher l'utilisateur dans la base de données
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Mettre à jour les champs s'ils sont fournis
        if (updateRequest.getFirstName() != null) {
            user.setFirstName(updateRequest.getFirstName());
        }

        if (updateRequest.getLastName() != null) {
            user.setLastName(updateRequest.getLastName());
        }

        // Mettre à jour l'email s'il est fourni et unique
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre compte");
            }
            user.setEmail(updateRequest.getEmail());
        }

        // Mettre à jour le mot de passe s'il est fourni
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            // Vérifier le mot de passe actuel
            if (updateRequest.getCurrentPassword() == null ||
                    !passwordEncoder.matches(updateRequest.getCurrentPassword(), user.getPassword())) {
                throw new AccessDeniedException("Le mot de passe actuel est incorrect");
            }

            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        // Sauvegarder les modifications
        User updatedUser = userRepository.save(user);

        return mapUserToProfileResponse(updatedUser);
    }

    /**
     * Récupère un profil utilisateur par ID (admin seulement)
     */
    @Override
    public UserProfileResponse getUserProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        return mapUserToProfileResponse(user);
    }

    /**
     * Supprime un utilisateur par ID (admin seulement)
     */
    @Transactional
    @Override
    public void deleteUser(Long id) {
        // Vérifier que l'utilisateur existe
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'ID: " + id);
        }

        // Ne pas autoriser la suppression de son propre compte
        UserDetailsImpl currentUser = getCurrentUserDetails();
        if (currentUser.getId().equals(id)) {
            throw new AccessDeniedException("Vous ne pouvez pas supprimer votre propre compte");
        }

        // Supprimer l'utilisateur
        userRepository.deleteById(id);
    }

    /**
     * Récupère l'utilisateur actuellement connecté
     */
    private UserDetailsImpl getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            throw new AccessDeniedException("Vous devez être connecté pour accéder à cette ressource");
        }

        return (UserDetailsImpl) authentication.getPrincipal();
    }

    /**
     * Convertit une entité User en DTO UserProfileResponse
     */
    private UserProfileResponse mapUserToProfileResponse(User user) {
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                roles
        );
    }
}