# Spécifications de l'Application Materio

## 1. Présentation Générale

**Materio** est une application de gestion du matériel conçue pour suivre les équipements, leur localisation et leur déplacement au sein d'une organisation. L'application permet de gérer une structure hiérarchique de localisations (Localité > Espace > Zone) où les équipements sont positionnés.

### 1.1 Environnement Technique

- **Langage**: Java 21
- **Framework**: Spring Boot 3.4.2
- **Base de données**: PostgreSQL

## 2. Structure de l'Application

L'application suit une architecture en couches:

### 2.1 Couches principales

1. **Couche Présentation** (`view.controller`): Gestion des requêtes HTTP et des réponses REST.
2. **Couche Métier** (`business.service`): Implémentation des règles métier.
3. **Couche Persistance** (`jpa.repository`): Accès aux données via Spring Data JPA.
4. **Couche Entité** (`jpa.entity`): Modèles de données correspondant aux tables en base.
5. **Couche DTO** (`dto`): Objets de transfert de données (BO, VO).

## 3. Fonctionnalités Principales

### 3.1 Gestion des Localités

- **Créer une localité**: Ajouter un nouveau site avec nom, adresse, code postal et ville.
- **Consulter une localité**: Récupérer les détails d'une localité par son nom.
- **Modifier une localité**: Mettre à jour les informations d'une localité existante.
- **Supprimer une localité**: Retirer une localité (uniquement si elle ne contient pas d'équipements).
- **Lister toutes les localités**: Récupérer la liste complète des localités.

### 3.2 Gestion des Espaces

- **Créer un espace**: Ajouter un nouvel espace dans une localité spécifique.
- **Consulter un espace**: Récupérer les détails d'un espace par son nom et sa localité.
- **Modifier un espace**: Mettre à jour les informations d'un espace existant.
- **Supprimer un espace**: Retirer un espace (uniquement s'il ne contient pas d'équipements).
- **Lister les espaces par localité**: Récupérer tous les espaces d'une localité.

### 3.3 Gestion des Zones

- **Créer une zone**: Ajouter une nouvelle zone dans un espace spécifique.
- **Consulter une zone**: Récupérer les détails d'une zone par son nom, son espace et sa localité.
- **Modifier une zone**: Mettre à jour les informations d'une zone existante.
- **Supprimer une zone**: Retirer une zone (uniquement si elle ne contient pas d'équipements).
- **Lister les zones par espace**: Récupérer toutes les zones d'un espace.

### 3.4 Gestion des Équipements

- **Créer un équipement**: Ajouter un nouvel équipement avec sa référence, son numéro de série et sa localisation.
- **Consulter un équipement**: Récupérer les détails d'un équipement par sa référence et son numéro de série.
- **Modifier un équipement**: Mettre à jour les informations d'un équipement existant.
- **Supprimer un équipement**: Retirer un équipement du système.
- **Lister les équipements par zone/espace/localité**: Récupérer tous les équipements d'une zone, d'un espace ou d'une localité.
- **Lister tous les équipements**: Récupérer la liste complète des équipements.

### 3.5 Gestion des Références d'Équipement

- **Créer/récupérer une référence**: Ajouter ou récupérer une référence d'équipement.
- **Incrémenter la quantité**: Augmenter le compteur lors de l'ajout d'un équipement.
- **Décrémenter la quantité**: Diminuer le compteur lors de la suppression d'un équipement.

### 3.6 Gestion des Transferts d'Équipement

- **Effectuer un transfert**: Déplacer un ou plusieurs équipements vers une nouvelle zone.
- **Consulter l'historique des transferts**: Récupérer l'historique des mouvements d'un équipement.

## 4. Règles Métier Principales

1. **Unicité des noms**:
    - Les noms de localité doivent être uniques.
    - Les noms d'espace doivent être uniques au sein d'une même localité.
    - Les noms de zone doivent être uniques au sein d'un même espace.

2. **Contraintes de suppression**:
    - Une localité ne peut être supprimée si elle contient des équipements.
    - Un espace ne peut être supprimé s'il contient des zones avec des équipements.
    - Une zone ne peut être supprimée si elle contient des équipements.

3. **Identification des équipements**:
    - Un équipement est identifié par la combinaison de sa référence et de son numéro de série.
    - Cette combinaison doit être unique.

4. **Transfert d'équipements**:
    - Tous les équipements à transférer doivent provenir de la même localité source.
    - La position de l'équipement doit correspondre à celle indiquée dans la demande de transfert.

## 5. Structure des Données

### 5.1 Localité (t_locality)
- **Attributs**: id, name, address, cp, city, created_at, updated_at
- **Relations**: One-to-Many avec Space

### 5.2 Espace (t_space)
- **Attributs**: id, name, locality_id, created_at, updated_at
- **Relations**: Many-to-One avec Locality, One-to-Many avec Zone

### 5.3 Zone (t_zone)
- **Attributs**: id, name, description, space_id, created_at, updated_at
- **Relations**: Many-to-One avec Space, One-to-Many avec Equipment

### 5.4 Équipement (t_equipment)
- **Attributs**: equipment_reference_name, equipment_serial_number, purchase_date, mark, description, zone_id, tag, barcode, created_at, updated_at
- **Relations**: Many-to-One avec Zone

### 5.5 Référence d'Équipement (t_reference)
- **Attributs**: id, name, quantity, created_at, updated_at

### 5.6 Transfert d'Équipement (t_transfer)
- **Attributs**: id, equipment_reference_name, equipment_serial_number, from_zone, to_zone, from_space, to_space, from_locality, to_locality, transfer_date, transfer_details, created_at, updated_at
- **Relations**: Many-to-One avec Equipment

## 6. API REST

L'application expose les endpoints REST suivants pour interagir avec les différentes ressources:

### 6.1 Gestion des Localités
- `POST /api/locality`: Créer une localité
- `GET /api/locality/{name}`: Récupérer une localité
- `PUT /api/locality/{name}`: Mettre à jour une localité
- `DELETE /api/locality/{name}`: Supprimer une localité
- `GET /api/localities`: Récupérer toutes les localités

### 6.2 Gestion des Espaces
- `POST /api/space`: Créer un espace
- `DELETE /api/space/{spaceName}?locality={localityName}`: Supprimer un espace
- `GET /api/spaces?localityName={localityName}`: Récupérer les espaces d'une localité
- `GET /api/space?localityName={localityName}&spaceName={spaceName}`: Récupérer un espace spécifique

### 6.3 Gestion des Zones
- `POST /api/zone`: Créer une zone
- `PUT /api/zone/{localityName}/{spaceName}/{zoneName}`: Mettre à jour une zone
- `DELETE /api/zone/{localityName}/{spaceName}/{zoneName}`: Supprimer une zone
- `GET /api/zone/{localityName}/{spaceName}/{zoneName}`: Récupérer une zone
- `GET /api/zones/{localityName}/{spaceName}`: Récupérer toutes les zones d'un espace

### 6.4 Gestion des Équipements
- `POST /api/equipment`: Créer un équipement
- `PUT /api/equipment/{referenceName}/{serialNumber}`: Mettre à jour un équipement
- `DELETE /api/equipment/{referenceName}/{serialNumber}`: Supprimer un équipement
- `GET /api/equipment/{referenceName}/{serialNumber}`: Récupérer un équipement
- `GET /api/equipments/zone?localityName={localityName}&spaceName={spaceName}&zoneName={zoneName}`: Récupérer les équipements d'une zone
- `GET /api/equipments/space?localityName={localityName}&spaceName={spaceName}`: Récupérer les équipements d'un espace
- `GET /api/equipments/locality/{localityName}`: Récupérer les équipements d'une localité
- `GET /api/equipments`: Récupérer tous les équipements

### 6.5 Gestion des Transferts
- `POST /api/transfers`: Effectuer un transfert d'équipements
- `GET /api/transfers/{referenceName}/{serialNumber}`: Récupérer l'historique des transferts d'un équipement

## 7. Validation des Données

L'application utilise Jakarta Validation pour garantir l'intégrité des données:

- Validation des champs obligatoires
- Validation des formats (code postal, etc.)
- Validation des règles métier (unicité, contraintes de suppression, etc.)

## 8. Gestion des Exceptions

L'application définit plusieurs types d'exceptions métier pour gérer les cas d'erreur:

- Exceptions liées aux localités
- Exceptions liées aux espaces
- Exceptions liées aux zones
- Exceptions liées aux équipements
- Exceptions liées aux transferts
- Exceptions liées aux références

Diagrame Mermaid url -> https://www.mermaidchart.com/play :

erDiagram
t_locality ||--o{ t_space : "contient"
t_space ||--o{ t_zone : "contient"
t_zone ||--o{ t_equipment : "contient"
t_equipment }o--o{ t_transfer : "a un historique"
t_reference }|--o{ t_equipment : "référence"

    t_locality {
        bigint id PK
        varchar name UK "Nom unique"
        varchar address "Adresse"
        int cp "Code postal"
        varchar city "Ville"
        timestamp created_at
        timestamp updated_at
    }
    
    t_space {
        bigint id PK
        varchar name "Nom"
        bigint locality_id FK "Référence vers t_locality"
        timestamp created_at
        timestamp updated_at
    }
    
    t_zone {
        bigint id PK
        varchar name "Nom"
        varchar description "Description facultative"
        bigint space_id FK "Référence vers t_space"
        timestamp created_at
        timestamp updated_at
    }
    
    t_equipment {
        varchar equipment_reference_name PK "Partie de clé composite"
        varchar equipment_serial_number PK "Partie de clé composite"
        date purchase_date "Date d'achat"
        varchar mark "Marque"
        varchar description "Description"
        bigint zone_id FK "Référence vers t_zone"
        varchar tag "Tag facultatif"
        int barcode "Code-barres facultatif"
        timestamp created_at
        timestamp updated_at
    }
    
    t_reference {
        bigint id PK
        varchar name UK "Nom unique"
        int quantity "Quantité totale"
        timestamp created_at
        timestamp updated_at
    }
    
    t_transfer {
        bigint id PK
        varchar equipment_reference_name FK "Référence vers equipment"
        varchar equipment_serial_number FK "Référence vers equipment"
        varchar from_zone "Zone source"
        varchar to_zone "Zone destination"
        varchar from_space "Espace source"
        varchar to_space "Espace destination"
        varchar from_locality "Localité source"
        varchar to_locality "Localité destination"
        timestamp transfer_date "Date du transfert"
        varchar transfer_details "Détails optionnels"
        timestamp created_at
        timestamp updated_at
    }