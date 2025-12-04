# 🚀 Guide Complet - E-Voting System avec MySQL et React

## 📋 Vue d'Ensemble

Ce projet comprend :
- **4 Microservices Spring Boot** (Eureka Server, Voter Service, Vote Service, Result Service)
- **Base de données MySQL** (persistante avec relations)
- **Front-end React** (interface d'administration moderne)
- **Communication inter-services** via Feign et Eureka

---

## 🗄️ Étape 1 : Configuration MySQL

### 1.1 Installer MySQL

1. Télécharger MySQL : https://dev.mysql.com/downloads/installer/
2. Installer MySQL Server
3. Noter le mot de passe root

### 1.2 Configurer les Services

**Modifiez le mot de passe dans chaque `application.properties` si nécessaire :**

- `voter-service/src/main/resources/application.properties`
- `vote-service/src/main/resources/application.properties`
- `result-service/src/main/resources/application.properties`

**Configuration actuelle :**
```properties
spring.datasource.username=root
spring.datasource.password=root  # ⚠️ Changez si votre MySQL utilise un autre mot de passe
```

### 1.3 Bases de Données

Les bases seront créées automatiquement :
- `voter_db` - Électeurs
- `vote_db` - Votes
- `result_db` - Résultats

---

## 🚀 Étape 2 : Démarrage des Services

### Ordre de Démarrage

1. **MySQL** - Démarrer le service MySQL
2. **Eureka Server** (Port 8761)
3. **Voter Service** (Port 8081)
4. **Vote Service** (Port 8083)
5. **Result Service** (Port 8082)

### Depuis IntelliJ IDEA

1. **Eureka Server** :
   - Ouvrir `EurekaServerApplication.java`
   - Clic droit → Run

2. **Autres Services** :
   - Répéter pour chaque service
   - Vérifier dans Eureka Dashboard : http://localhost:8761

---

## ⚛️ Étape 3 : Démarrage du Front-end React

### 3.1 Installation

```bash
cd e-voting-frontend
npm install
```

### 3.2 Lancement

```bash
npm start
```

L'application s'ouvrira sur : http://localhost:3000

### 3.3 Connexion

- **Username** : `admin`
- **Password** : `admin`

---

## 🎯 Fonctionnalités du Front-end

### 👥 Gestion des Électeurs
- ✅ Lister tous les électeurs
- ✅ Créer de nouveaux électeurs
- ✅ Voir le statut de vote
- ✅ Recherche par identifiant

### ✅ Gestion des Votes
- ✅ Lister tous les votes
- ✅ Soumettre de nouveaux votes
- ✅ Voir les votes par candidat
- ✅ Vérification automatique des électeurs

### 📊 Résultats
- ✅ Visualisation en temps réel
- ✅ Graphiques de résultats
- ✅ Statistiques de participation
- ✅ Publication des résultats

---

## 🔄 Flux de Communication

```
Front-end React (Port 3000)
    │
    ├──→ Voter Service (8081) - Gestion des électeurs
    │
    ├──→ Vote Service (8083) - Gestion des votes
    │       │
    │       └──→ Voter Service (via Feign) - Vérification électeur
    │
    └──→ Result Service (8082) - Résultats
            │
            └──→ Vote Service (via Feign) - Récupération des votes
```

---

## 📊 Données de Test

### Voter Service
- **8 électeurs** créés automatiquement au démarrage
- IDs : 1 à 8

### Vote Service
- **7 votes** créés automatiquement
- Répartis sur 3 candidats

### Result Service
- **3 résultats** créés automatiquement
- Un résultat par candidat

---

## 🎨 Interface Utilisateur

### Caractéristiques
- ✅ Design moderne avec dégradés
- ✅ Animations fluides
- ✅ Responsive design
- ✅ Tableaux interactifs
- ✅ Graphiques de résultats
- ✅ Gestion d'erreurs
- ✅ Feedback visuel

### Sections
1. **Dashboard** - Vue d'ensemble avec statistiques
2. **Électeurs** - Gestion complète des électeurs
3. **Votes** - Gestion et soumission des votes
4. **Résultats** - Visualisation et publication

---

## 🔧 Configuration

### URLs des Services (dans `e-voting-frontend/src/services/api.ts`)

```typescript
const API_BASE_URLS = {
  voter: 'http://localhost:8081/api',
  vote: 'http://localhost:8083/api',
  result: 'http://localhost:8082/api',
};
```

### CORS

Tous les services ont la configuration CORS activée pour permettre les appels depuis React.

---

## 🐛 Dépannage

### Front-end ne peut pas appeler les APIs

**Vérifier :**
1. Les services backend sont démarrés
2. Les URLs dans `api.ts` sont correctes
3. CORS est configuré dans chaque service

### Erreur MySQL "Access denied"

**Solution :** Modifier le mot de passe dans `application.properties`

### Erreur "Connection refused" sur Eureka

**Solution :** Démarrer Eureka Server en premier

---

## 📝 Notes Importantes

1. **MySQL doit être démarré** avant les services Spring Boot
2. **Eureka Server doit être démarré en premier**
3. **Les bases de données** seront créées automatiquement
4. **Les données sont persistantes** dans MySQL
5. **Le front-end** fonctionne indépendamment (projet séparé)

---

## 🎉 Prêt à l'Emploi !

Tout est configuré et prêt. Suivez les étapes ci-dessus pour démarrer l'application complète !

