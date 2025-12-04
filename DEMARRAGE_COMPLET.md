# 🚀 Guide de Démarrage Complet

## 📋 Données de Test Disponibles

### Voter Service (Port 8081)
- **8 électeurs** créés automatiquement au démarrage
- IDs : 1 à 8
- Un électeur (ID 6 - Julie Petit) a déjà voté

### Vote Service (Port 8083)
- **7 votes** créés automatiquement au démarrage
- Répartition :
  - Candidat 1 : 3 votes (Électeurs 1, 2, 7)
  - Candidat 2 : 2 votes (Électeurs 3, 4)
  - Candidat 3 : 2 votes (Électeurs 5, 8)

### Result Service (Port 8082)
- **3 résultats** créés automatiquement au démarrage
- Résultats pour les candidats 1, 2, 3
- Les résultats seront recalculés depuis vote-service lors de l'appel API

---

## 🎯 Démarrage depuis IntelliJ IDEA

### Étape 1 : Importer les Modules Maven

1. Ouvrez **File → Settings** (Ctrl+Alt+S)
2. Allez dans **Build, Execution, Deployment → Build Tools → Maven**
3. Cliquez sur **+** pour ajouter un nouveau projet Maven
4. Sélectionnez `eureka-server/pom.xml`
5. Cliquez sur **OK**
6. Répétez pour les autres services si nécessaire

**OU** utilisez le raccourci :
- Clic droit sur `eureka-server/pom.xml` → **Add as Maven Project**

### Étape 2 : Lancer Eureka Server

**Méthode 1 : Configuration de Run**
1. Ouvrez `eureka-server/src/main/java/evotingsystem/eurekaserver/EurekaServerApplication.java`
2. Cliquez avec le bouton droit sur la classe
3. Sélectionnez **Run 'EurekaServerApplication'**
4. IntelliJ créera automatiquement la configuration

**Méthode 2 : Menu Run**
1. En haut à droite, cliquez sur le menu déroulant
2. Sélectionnez **Edit Configurations...**
3. Cliquez sur **+** → **Spring Boot**
4. Configurez :
   - **Name** : `Eureka Server`
   - **Main class** : `evotingsystem.eurekaserver.EurekaServerApplication`
5. Cliquez sur **OK** puis **Run**

### Étape 3 : Lancer les Autres Services

Répétez l'étape 2 pour chaque service :

1. **Voter Service**
   - Classe : `evotingsystem.voterservice.VoterServiceApplication`
   - Port : 8081

2. **Vote Service**
   - Classe : `evotingsystem.voteservice.VoteServiceApplication`
   - Port : 8083

3. **Result Service**
   - Classe : `evotingsystem.resultservice.ResultServiceApplication`
   - Port : 8082

---

## ✅ Vérification

### 1. Eureka Dashboard
Ouvrez : http://localhost:8761

Vous devriez voir :
- **VOTER-SERVICE** (1 instance)
- **VOTE-SERVICE** (1 instance)
- **RESULT-SERVICE** (1 instance)

### 2. Test des APIs

#### Voter Service
```bash
# Lister tous les électeurs
curl http://localhost:8081/api/electors

# Récupérer un électeur
curl http://localhost:8081/api/electors/1
```

#### Vote Service
```bash
# Lister tous les votes
curl http://localhost:8083/api/votes

# Votes par candidat
curl http://localhost:8083/api/votes/candidate/1
```

#### Result Service
```bash
# Obtenir les résultats
curl http://localhost:8082/api/results

# Statistiques
curl http://localhost:8082/api/results/statistics
```

---

## 🔄 Flux de Communication Testé

### Test 1 : Vote Service → Voter Service
```bash
# Créer un vote (vote-service vérifie l'électeur via voter-service)
curl -X POST http://localhost:8083/api/votes \
  -H "Content-Type: application/json" \
  -d '{"electorId": 1, "candidateId": 1}'
```

### Test 2 : Result Service → Vote Service
```bash
# Obtenir les résultats (result-service récupère les votes via vote-service)
curl http://localhost:8082/api/results
```

---

## 📊 Résumé des Données de Test

| Service | Données | Description |
|---------|---------|-------------|
| Voter Service | 8 électeurs | IDs 1-8, divers noms et dates |
| Vote Service | 7 votes | Répartis sur 3 candidats |
| Result Service | 3 résultats | Un résultat par candidat |

---

## 🎓 Conseils

1. **Toujours démarrer Eureka Server en premier**
2. **Attendre 10-15 secondes** après chaque démarrage
3. **Vérifier les logs** pour confirmer l'enregistrement dans Eureka
4. **Utiliser le dashboard Eureka** pour voir l'état des services

---

**Tous les services ont maintenant des données de test prêtes à l'emploi ! 🎉**

