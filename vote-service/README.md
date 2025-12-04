# Vote Service

## 🚀 Démarrage

**IMPORTANT : Démarrer Eureka Server et Voter Service en premier !**

```bash
cd vote-service
mvnw spring-boot:run
```

## ✅ Vérification

1. Vérifiez dans Eureka : http://localhost:8761 (service `VOTE-SERVICE` doit apparaître)
2. Testez l'API : `curl http://localhost:8083/api/votes`

## 📋 Configuration

- **Port** : 8083
- **Base de données** : H2 (en mémoire)
- **Console H2** : http://localhost:8083/h2-console

## 🔗 Endpoints

- `POST /api/votes` - Soumettre un vote
- `GET /api/votes` - Lister tous les votes
- `GET /api/votes/candidate/{candidateId}` - Votes par candidat

## 🔄 Communication

Ce service appelle **Voter Service** via Feign pour vérifier les électeurs.

