# Voter Service

## 🚀 Démarrage

**IMPORTANT : Démarrer Eureka Server en premier !**

```bash
cd voter-service
mvnw spring-boot:run
```

## ✅ Vérification

1. Vérifiez dans Eureka : http://localhost:8761 (service `VOTER-SERVICE` doit apparaître)
2. Testez l'API : `curl http://localhost:8081/api/electors`

## 📋 Configuration

- **Port** : 8081
- **Base de données** : H2 (en mémoire)
- **Console H2** : http://localhost:8081/h2-console

## 🔗 Endpoints

- `POST /api/electors` - Créer un électeur
- `GET /api/electors` - Lister tous les électeurs
- `GET /api/electors/{id}` - Récupérer un électeur par ID
- `GET /api/electors/identifiant/{identifiantSecurise}` - Récupérer par identifiant sécurisé

## 📝 Données de Test

8 électeurs de test sont automatiquement créés au démarrage.
