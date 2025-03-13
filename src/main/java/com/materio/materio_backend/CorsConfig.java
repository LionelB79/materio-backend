package com.materio.materio_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // On définit quelles origines (domaines) sont autorisées à accéder à notre API backend
        config.addAllowedOrigin("http://localhost:5173"); // URL de développement Vite
        config.addAllowedOrigin("http://localhost:4173"); // URL de prévisualisation Vite

        // On définit quelles méthodes HTTP sont autorisées
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");

        // On autorise tous les en-têtes HTTP dans les requêtes
        config.addAllowedHeader("*");

        // Permet d'inclure des cookies dans les requêtes cross-origin
        // Important pour quand on voudra utiliser l'authentification basée sur les sessions
        config.setAllowCredentials(true);

        // Applique cette configuration à tous les endpoints commençant par "/api/"
        source.registerCorsConfiguration("/api/**", config);

        // Crée et retourne le filtre CORS avec cette configuration
        return new CorsFilter(source);
    }
}