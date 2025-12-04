# 🚀 Ordre de Démarrage des Services

## ⚠️ IMPORTANT : Respecter cet ordre !

### 1️⃣ Eureka Server (Port 8761) - **OBLIGATOIRE EN PREMIER**

```bash
cd eureka-server
mvnw spring-boot:run
```

**Attendre** que le message "Started EurekaServerApplication" apparaisse.

**Vérifier** : http://localhost:8761

---

### 2️⃣ Voter Service (Port 8081)

```bash
cd voter-service
mvnw spring-boot:run
```

**Attendre** 10-15 secondes pour l'enregistrement dans Eureka.

**Vérifier** : 
- Eureka Dashboard : `VOTER-SERVICE` doit apparaître
- API : `curl http://localhost:8081/api/electors`

---

### 3️⃣ Vote Service (Port 8083)

```bash
cd vote-service
mvnw spring-boot:run
```

**Attendre** 10-15 secondes pour l'enregistrement dans Eureka.

**Vérifier** :
- Eureka Dashboard : `VOTE-SERVICE` doit apparaître
- API : `curl http://localhost:8083/api/votes`

---

### 4️⃣ Result Service (Port 8082)

```bash
cd result-service
mvnw spring-boot:run
```

**Attendre** 10-15 secondes pour l'enregistrement dans Eureka.

**Vérifier** :
- Eureka Dashboard : `RESULT-SERVICE` doit apparaître
- API : `curl http://localhost:8082/api/results`

---

## 📊 Résumé des Ports

| Service | Port | URL |
|---------|------|-----|
| Eureka Server | 8761 | http://localhost:8761 |
| Voter Service | 8081 | http://localhost:8081 |
| Vote Service | 8083 | http://localhost:8083 |
| Result Service | 8082 | http://localhost:8082 |

---

## 🐛 Problèmes Courants

### Port déjà utilisé

**Solution** : Arrêter le processus qui utilise le port ou changer le port dans `application.properties`

### Service non visible dans Eureka

**Vérifier** :
1. Eureka Server est démarré
2. Attendre 10-15 secondes
3. Vérifier les logs pour les erreurs

### Erreur "Connection refused" sur Eureka

**Solution** : Démarrer Eureka Server en premier et attendre qu'il soit complètement démarré.

