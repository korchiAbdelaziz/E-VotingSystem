# Guide Complet : Configuration des Microservices avec Eureka et Feign

Ce guide explique comment configurer et lancer les 3 microservices avec Eureka Server pour la découverte de services et Feign pour la communication inter-services.

## 📋 Architecture des Services

```
┌─────────────────┐
│  Eureka Server  │ (Port 8761)
│   (Discovery)   │
└────────┬────────┘
         │
    ┌────┴────┬──────────────┬──────────────┐
    │         │              │              │
┌───▼───┐ ┌──▼───┐      ┌───▼───┐      ┌───▼───┐
│Voter  │ │Vote  │      │Result │      │...    │
│Service │ │Service│      │Service│      │       │
│:8081  │ │:8083 │      │:8082 │      │       │
└───┬───┘ └──┬───┘      └───┬───┘      └───────┘
    │        │              │
    │        └──────┬────────┘
    │               │
    └───────────────┘
    Communication via Feign
```

## 🔧 Étape 1 : Créer le Eureka Server

### 1.1 Créer le projet Eureka Server

Créez un nouveau module `eureka-server` dans votre projet.

### 1.2 Structure du Eureka Server

```
eureka-server/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── evotingsystem/
│       │       └── eurekaserver/
│       │           └── EurekaServerApplication.java
│       └── resources/
│           └── application.properties
```

### 1.3 pom.xml pour Eureka Server

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>

    <groupId>evotingsystem</groupId>
    <artifactId>eureka-server</artifactId>
    <version>1.0.0</version>
    <name>eureka-server</name>

    <properties>
        <java.version>17</java.version>
        <spring-cloud.version>2023.0.0</spring-cloud.version>
    </properties>

    <dependencies>
        <!-- Eureka Server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <!-- Spring Boot Starter Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### 1.4 EurekaServerApplication.java

```java
package evotingsystem.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

### 1.5 application.properties pour Eureka Server

```properties
# Configuration Eureka Server
spring.application.name=eureka-server
server.port=8761

# Désactiver l'enregistrement automatique (le serveur ne s'enregistre pas lui-même)
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

## 🔧 Étape 2 : Configuration des Ports

Assurez-vous que chaque service utilise un port unique :

- **Eureka Server** : `8761`
- **Voter Service** : `8081` ✅ (déjà corrigé)
- **Vote Service** : `8083` ✅ (déjà correct)
- **Result Service** : `8082` ✅ (déjà correct)

## 🔧 Étape 3 : Améliorer les Feign Clients

### 3.1 Voter Service - Exposer les endpoints nécessaires

Le `voter-service` doit exposer les endpoints que `vote-service` appelle. Vérifiez que ces endpoints existent :

- ✅ `GET /api/electors/{id}` - Existe déjà
- ✅ `GET /api/electors` - Existe déjà

### 3.2 Vote Service - Améliorer ElectorClient

Le `ElectorClient` dans `vote-service` doit correspondre exactement aux endpoints du `voter-service`.

**Fichier actuel :** `vote-service/.../feignclients/ElectorClient.java`

```java
@FeignClient(name = "voter-service")
public interface ElectorClient {
    @GetMapping("/api/electors/{id}")
    Elector getElectorById(@PathVariable("id") Long id);
}
```

**Amélioration suggérée :** Ajouter la gestion d'erreurs et un fallback.

### 3.3 Result Service - Vérifier VoteClient

Le `VoteClient` dans `result-service` doit correspondre aux endpoints du `vote-service`.

**Vérifiez que `vote-service` expose :**
- ✅ `GET /api/votes` - Existe déjà
- ✅ `GET /api/votes/candidate/{candidateId}` - Existe déjà

## 🚀 Étape 4 : Ordre de Démarrage

### Ordre recommandé :

1. **Eureka Server** (Port 8761)
   ```bash
   cd eureka-server
   mvnw spring-boot:run
   ```
   Vérifiez : http://localhost:8761

2. **Voter Service** (Port 8081)
   ```bash
   cd voter-service
   mvnw spring-boot:run
   ```

3. **Vote Service** (Port 8083)
   ```bash
   cd vote-service
   mvnw spring-boot:run
   ```

4. **Result Service** (Port 8082)
   ```bash
   cd result-service
   mvnw spring-boot:run
   ```

## ✅ Étape 5 : Vérification

### 5.1 Vérifier Eureka Dashboard

Ouvrez : http://localhost:8761

Vous devriez voir les 3 services enregistrés :
- `VOTER-SERVICE`
- `VOTE-SERVICE`
- `RESULT-SERVICE`

### 5.2 Tester la Communication Inter-Services

#### Test 1 : Vote Service → Voter Service

```bash
# Créer un vote (vote-service appelle voter-service)
curl -X POST http://localhost:8083/api/votes \
  -H "Content-Type: application/json" \
  -d '{
    "electorId": 1,
    "candidateId": 1
  }'
```

#### Test 2 : Result Service → Vote Service

```bash
# Récupérer tous les votes (result-service appelle vote-service)
curl http://localhost:8082/api/results
```

#### Test 3 : Vérifier les électeurs

```bash
# Lister tous les électeurs
curl http://localhost:8081/api/electors

# Récupérer un électeur spécifique
curl http://localhost:8081/api/electors/1
```

## 🔍 Conseils et Bonnes Pratiques

### 1. Gestion des Erreurs avec Feign

Créez des classes de fallback pour gérer les erreurs :

```java
@Component
public class ElectorClientFallback implements ElectorClient {
    @Override
    public Elector getElectorById(Long id) {
        // Retourner une valeur par défaut ou lever une exception
        throw new RuntimeException("Service voter-service non disponible");
    }
}
```

Puis dans le FeignClient :
```java
@FeignClient(name = "voter-service", fallback = ElectorClientFallback.class)
```

### 2. Configuration Feign (Timeout, Retry)

Ajoutez dans `application.properties` :

```properties
# Configuration Feign
feign.client.config.default.connectTimeout=5000
feign.client.config.default.readTimeout=5000
feign.hystrix.enabled=false
```

### 3. Logging des Appels Feign

Ajoutez dans `application.properties` :

```properties
# Logging Feign
logging.level.evotingsystem.voteservice.feignclients=DEBUG
```

### 4. Health Checks

Ajoutez Spring Boot Actuator pour les health checks :

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```properties
# Actuator
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

### 5. Circuit Breaker (Optionnel)

Pour une meilleure résilience, utilisez Resilience4j ou Hystrix :

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
```

## 🐛 Dépannage

### Problème : Services non visibles dans Eureka

**Solutions :**
1. Vérifiez que Eureka Server est démarré en premier
2. Vérifiez les logs pour les erreurs de connexion
3. Vérifiez que `spring.application.name` est correct
4. Vérifiez que le port Eureka est `8761` dans tous les services

### Problème : Erreur "UnknownHostException" avec Feign

**Solutions :**
1. Vérifiez que le service est bien enregistré dans Eureka
2. Utilisez le nom du service (pas l'URL) dans `@FeignClient(name = "...")`
3. Vérifiez que `@EnableFeignClients` est présent dans l'application principale

### Problème : Timeout avec Feign

**Solutions :**
1. Augmentez les timeouts dans `application.properties`
2. Vérifiez que les services répondent rapidement
3. Ajoutez des retries si nécessaire

## 📝 Résumé des Endpoints

### Voter Service (Port 8081)
- `POST /api/electors` - Créer un électeur
- `GET /api/electors` - Lister tous les électeurs
- `GET /api/electors/{id}` - Récupérer un électeur par ID
- `GET /api/electors/identifiant/{identifiantSecurise}` - Récupérer par identifiant sécurisé

### Vote Service (Port 8083)
- `POST /api/votes` - Soumettre un vote
- `GET /api/votes` - Lister tous les votes
- `GET /api/votes/candidate/{candidateId}` - Votes par candidat

### Result Service (Port 8082)
- `GET /api/results` - Obtenir les résultats
- `GET /api/results/statistics` - Obtenir les statistiques
- `POST /api/results/publish` - Publier les résultats

## 🎯 Prochaines Étapes

1. ✅ Créer le Eureka Server
2. ✅ Vérifier les ports
3. ✅ Tester la communication inter-services
4. ⬜ Ajouter la gestion d'erreurs avec fallback
5. ⬜ Ajouter les health checks
6. ⬜ Ajouter le circuit breaker
7. ⬜ Configurer les logs centralisés

---

**Note :** Ce guide suppose que vous avez déjà configuré les services de base. Si vous rencontrez des problèmes, vérifiez les logs de chaque service et le dashboard Eureka.

