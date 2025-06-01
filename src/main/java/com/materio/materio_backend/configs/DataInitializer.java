package com.materio.materio_backend.configs;


import com.materio.materio_backend.jpa.entity.user.EnumRole;
import com.materio.materio_backend.jpa.entity.user.Role;
import com.materio.materio_backend.jpa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Classe d'initialisation des données de base
 * Exécutée au démarrage de l'application pour s'assurer que les rôles existent
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        // Initialisation des rôles s'ils n'existent pas déjà
        initRoles();
    }

    private void initRoles() {
        Arrays.stream(EnumRole.values()).forEach(role -> {
            if (roleRepository.findByName(role).isEmpty()) {
                roleRepository.save(new Role(role));
            }
        });
    }
}
