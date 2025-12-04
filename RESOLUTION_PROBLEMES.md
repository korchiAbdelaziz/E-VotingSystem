# 🔧 Résolution des Problèmes

## ❌ Erreur : "Port 8082 was already in use"

### Solution 1 : Arrêter le processus

**Windows PowerShell :**
```powershell
# Trouver le processus qui utilise le port 8082
netstat -ano | findstr :8082

# Arrêter le processus (remplacer PID par le numéro trouvé)
taskkill /PID <PID> /F
```

**Ou arrêter tous les processus Java :**
```powershell
Get-Process java | Stop-Process -Force
```

### Solution 2 : Changer le port

Modifiez `application.properties` du service concerné :
```properties
server.port=8084  # Utilisez un port libre
```

---

## ❌ Erreur : "Connection refused" sur Eureka (localhost:8761)

### Cause
Eureka Server n'est pas démarré.

### Solution
1. **Démarrer Eureka Server en premier** :
   ```bash
   cd eureka-server
   mvnw spring-boot:run
   ```

2. **Attendre** que le message "Started EurekaServerApplication" apparaisse

3. **Vérifier** : http://localhost:8761 doit être accessible

4. **Ensuite** démarrer les autres services

---

## ❌ Service non visible dans Eureka Dashboard

### Vérifications

1. **Eureka Server est démarré** ✅
2. **Attendre 10-15 secondes** après le démarrage du service
3. **Vérifier les logs** pour les erreurs
4. **Vérifier** que `spring.application.name` est correct dans `application.properties`
5. **Vérifier** que le port Eureka est `8761` dans `application.properties`

---

## ❌ Erreur Feign : "UnknownHostException"

### Cause
Le service cible n'est pas enregistré dans Eureka.

### Solution

1. **Vérifier** que le service cible est démarré
2. **Vérifier** dans Eureka Dashboard que le service est enregistré
3. **Vérifier** que le nom dans `@FeignClient(name = "...")` correspond à `spring.application.name`

---

## 📋 Checklist de Démarrage

- [ ] Eureka Server démarré (Port 8761)
- [ ] Voter Service démarré (Port 8081)
- [ ] Vote Service démarré (Port 8083)
- [ ] Result Service démarré (Port 8082)
- [ ] Tous les services visibles dans Eureka Dashboard
- [ ] Aucune erreur dans les logs

---

## 🛠️ Commandes Utiles

### Vérifier les ports utilisés
```powershell
netstat -ano | findstr :8081
netstat -ano | findstr :8082
netstat -ano | findstr :8083
netstat -ano | findstr :8761
```

### Arrêter tous les services Java
```powershell
Get-Process java | Stop-Process -Force
```

### Vérifier les services dans Eureka
Ouvrir : http://localhost:8761

