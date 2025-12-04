# Voter Service

## Description

Le **Voter Service** est un microservice faisant partie du système de vote électronique (E-Voting System). Il est responsable de la gestion des informations des électeurs, incluant leur création, récupération et suivi de leur statut de vote.

## Fonctionnalités

Ce service fournit les fonctionnalités suivantes :

- **Création d'électeurs** : Permet de créer de nouveaux électeurs dans le système
- **Récupération d'électeurs** : Permet de récupérer un électeur par son ID ou son identifiant sécurisé
- **Liste des électeurs** : Permet de récupérer la liste complète de tous les électeurs enregistrés
- **Gestion du statut de vote** : Permet de mettre à jour le statut de vote d'un électeur

## Architecture

Le service suit une architecture en couches :

- **Controller** : Gère les requêtes HTTP REST
- **Service** : Contient la logique métier
- **Repository** : Gère l'accès aux données
- **Entity** : Représente les entités de la base de données
- **DTO** : Objets de transfert de données pour les API

## Technologies utilisées

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** : Pour l'accès aux données
- **H2 Database** : Base de données en mémoire pour le développement
- **Spring Cloud Eureka** : Pour la découverte de services
- **Lombok** : Pour réduire le code boilerplate
- **Spring Validation** : Pour la validation des données



## Structure du projet

```
voter-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── evotingsystem/
│   │   │       └── voterservice/
│   │   │           ├── VoterServiceApplication.java
│   │   │           ├── controllers/
│   │   │           │   ├── ElectorController.java
│   │   │           │   └── GlobalExceptionHandler.java
│   │   │           ├── services/
│   │   │           │   ├── ElectorService.java
│   │   │           │   └── ElectorServiceImpl.java
│   │   │           ├── repositories/
│   │   │           │   └── ElectorRepository.java
│   │   │           ├── entities/
│   │   │           │   └── Elector.java
│   │   │           ├── dtos/
│   │   │           │   ├── ElectorDTO.java
│   │   │           │   ├── ElectorRequest.java
│   │   │           │   └── ElectorResponse.java
│   │   │           └── exceptions/
│   │   │               ├── ElectorNotFoundException.java
│   │   │               └── ElectorAlreadyExistsException.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── evotingsystem/
│               └── voterservice/
│                   └── VoterServiceApplicationTests.java
└── pom.xml
```

## Configuration

### Port par défaut

Le service s'exécute sur le port **8082** par défaut.

### Base de données

Le service utilise H2 Database en mémoire. La console H2 est accessible à l'adresse :
```
http://localhost:8082/h2-console
```

### Eureka

Le service s'enregistre automatiquement auprès d'Eureka Server (si disponible) à l'adresse :
```
http://localhost:8761/eureka
```

## API Endpoints

### 1. Créer un électeur

**POST** `/api/electors`

**Corps de la requête :**
```json
{
  "nom": "Dupont",
  "prenom": "Jean",
  "dateNaissance": "1990-01-15",
  "identifiantSecurise": "ID123456"
}
```

**Réponse (201 Created) :**
```json
{
  "idElector": 1,
  "nom": "Dupont",
  "prenom": "Jean",
  "dateNaissance": "1990-01-15",
  "identifiantSecurise": "ID123456",
  "aVote": false,
  "message": "Électeur créé avec succès"
}
```

### 2. Récupérer un électeur par ID

**GET** `/api/electors/{id}`

**Réponse (200 OK) :**
```json
{
  "idElector": 1,
  "nom": "Dupont",
  "prenom": "Jean",
  "dateNaissance": "1990-01-15",
  "identifiantSecurise": "ID123456",
  "aVote": false
}
```

### 3. Récupérer un électeur par identifiant sécurisé

**GET** `/api/electors/identifiant/{identifiantSecurise}`

**Réponse (200 OK) :**
```json
{
  "idElector": 1,
  "nom": "Dupont",
  "prenom": "Jean",
  "dateNaissance": "1990-01-15",
  "identifiantSecurise": "ID123456",
  "aVote": false
}
```

### 4. Lister tous les électeurs

**GET** `/api/electors`

**Réponse (200 OK) :**
```json
[
  {
    "idElector": 1,
    "nom": "Dupont",
    "prenom": "Jean",
    "dateNaissance": "1990-01-15",
    "identifiantSecurise": "ID123456",
    "aVote": false
  },
  {
    "idElector": 2,
    "nom": "Martin",
    "prenom": "Marie",
    "dateNaissance": "1985-05-20",
    "identifiantSecurise": "ID789012",
    "aVote": true
  }
]
```

## Codes de statut HTTP

- **200 OK** : Requête réussie
- **201 Created** : Ressource créée avec succès
- **400 Bad Request** : Données invalides
- **404 Not Found** : Électeur non trouvé
- **409 Conflict** : Électeur déjà existant
- **500 Internal Server Error** : Erreur interne du serveur

## Gestion des erreurs

Le service inclut un gestionnaire global d'exceptions qui retourne des réponses JSON structurées pour toutes les erreurs :

```json
{
  "error": "Type d'erreur",
  "message": "Message détaillé",
  "status": "Code HTTP"
}
```

## Compilation et exécution

### Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur

### Compilation

```bash
mvn clean compile
```

### Exécution

```bash
mvn spring-boot:run
```

### Tests

```bash
mvn test
```

## Intégration avec les autres services

Le service peut être appelé par d'autres microservices via :

- **Feign Client** : Le `vote-service` utilise un `ElectorClient` pour récupérer les informations des électeurs
- **API REST** : Tous les endpoints sont accessibles via HTTP REST

## Notes de développement

- Tous les fichiers sont bien documentés avec des commentaires JavaDoc
- Le code suit les bonnes pratiques Spring Boot
- La validation des données est effectuée automatiquement
- Les logs sont configurés pour faciliter le débogage

## Auteur

Équipe E-Voting System

## Version

1.0.0

