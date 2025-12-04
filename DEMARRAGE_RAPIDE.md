# 🚀 Guide de Démarrage Rapide - Microservices E-Voting

## 📋 Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- 4 terminaux (ou utilisez un IDE avec plusieurs configurations de run)

## 🎯 Ordre de Démarrage (IMPORTANT)

### 1️⃣ Eureka Server (Port 8761) - **DÉMARRER EN PREMIER**

```bash
cd eureka-server
mvnw spring-boot:run
```

**Vérification :** Ouvrez http://localhost:8761
- Vous devriez voir le dashboard Eureka
- Aucun service ne sera encore enregistré

### 2️⃣ Voter Service (Port 8081)

```bash
cd voter-service
mvnw spring-boot:run
```

**Vérification :**
- Dans Eureka Dashboard : `VOTER-SERVICE` devrait apparaître
- Test : `curl http://localhost:8081/api/electors`

### 3️⃣ Vote Service (Port 8083)

```bash
cd vote-service
mvnw spring-boot:run
```

**Vérification :**
- Dans Eureka Dashboard : `VOTE-SERVICE` devrait apparaître
- Test : `curl http://localhost:8083/api/votes`

### 4️⃣ Result Service (Port 8082)

```bash
cd result-service
mvnw spring-boot:run
```

**Vérification :**
- Dans Eureka Dashboard : `RESULT-SERVICE` devrait apparaître
- Test : `curl http://localhost:8082/api/results`

## ✅ Vérification Complète

### 1. Vérifier Eureka Dashboard

Ouvrez http://localhost:8761

Vous devriez voir 3 services :
- **VOTER-SERVICE** (1 instance)
- **VOTE-SERVICE** (1 instance)
- **RESULT-SERVICE** (1 instance)

### 2. Tester les Endpoints

#### Voter Service
```bash
# Lister tous les électeurs
curl http://localhost:8081/api/electors

# Récupérer un électeur spécifique
curl http://localhost:8081/api/electors/1
```

#### Vote Service (appelle Voter Service via Feign)
```bash
# Créer un vote (vérifie l'électeur via voter-service)
curl -X POST http://localhost:8083/api/votes \
  -H "Content-Type: application/json" \
  -d '{
    "electorId": 1,
    "candidateId": 1
  }'

# Lister tous les votes
curl http://localhost:8083/api/votes
```

#### Result Service (appelle Vote Service via Feign)
```bash
# Obtenir les résultats (récupère les votes via vote-service)
curl http://localhost:8082/api/results

# Obtenir les statistiques
curl http://localhost:8082/api/results/statistics
```

## 🔍 Flux de Communication

```
1. Utilisateur → Vote Service
   POST /api/votes {electorId: 1, candidateId: 1}
   
2. Vote Service → Voter Service (via Feign)
   GET /api/electors/1
   (Vérifie que l'électeur existe)
   
3. Vote Service → Sauvegarde le vote
   (Enregistre dans sa base de données)
   
4. Utilisateur → Result Service
   GET /api/results
   
5. Result Service → Vote Service (via Feign)
   GET /api/votes
   (Récupère tous les votes)
   
6. Result Service → Calcule les résultats
   (Retourne les résultats calculés)
```

## 🐛 Dépannage

### Problème : Service non visible dans Eureka

**Vérifiez :**
1. Eureka Server est démarré en premier
2. Le service a `@EnableDiscoveryClient`
3. `spring.application.name` est correct dans `application.properties`
4. Le port Eureka est `8761` dans `application.properties`

### Problème : Erreur Feign "UnknownHostException"

**Vérifiez :**
1. Le service cible est bien enregistré dans Eureka
2. Le nom dans `@FeignClient(name = "...")` correspond à `spring.application.name`
3. `@EnableFeignClients` est présent dans l'application principale

### Problème : Port déjà utilisé

**Solution :** Arrêtez le processus qui utilise le port ou changez le port dans `application.properties`

## 📊 Ports Utilisés

| Service | Port | URL Dashboard |
|---------|------|---------------|
| Eureka Server | 8761 | http://localhost:8761 |
| Voter Service | 8081 | http://localhost:8081 |
| Vote Service | 8083 | http://localhost:8083 |
| Result Service | 8082 | http://localhost:8082 |

## 🎓 Conseils

1. **Toujours démarrer Eureka Server en premier**
2. **Attendre 10-15 secondes** après chaque démarrage pour que le service s'enregistre
3. **Vérifier les logs** si quelque chose ne fonctionne pas
4. **Utiliser le dashboard Eureka** pour voir l'état des services

## 📝 Notes Importantes

- Les données de test sont automatiquement insérées dans `voter-service` au démarrage
- Les bases de données H2 sont en mémoire (données perdues au redémarrage)
- Pour la production, utilisez des bases de données persistantes (PostgreSQL, MySQL, etc.)

---

**Bon développement ! 🎉**

