# Result Service

## 🚀 Démarrage

**IMPORTANT : Démarrer Eureka Server et Vote Service en premier !**

```bash
cd result-service
mvnw spring-boot:run
```

## ✅ Vérification

1. Vérifiez dans Eureka : http://localhost:8761 (service `RESULT-SERVICE` doit apparaître)
2. Testez l'API : `curl http://localhost:8082/api/results`

## 📋 Configuration

- **Port** : 8082
- **Base de données** : H2 (en mémoire)
- **Console H2** : http://localhost:8082/h2-console

## 🔗 Endpoints

- `GET /api/results` - Obtenir les résultats
- `GET /api/results/statistics` - Obtenir les statistiques
- `POST /api/results/publish` - Publier les résultats

## 🔄 Communication

Ce service appelle **Vote Service** via Feign pour récupérer les votes.

