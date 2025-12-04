# 🚀 Guide : Lancer Eureka Server depuis IntelliJ IDEA

## Méthode 1 : Configuration de Run (Recommandée)

### Étape 1 : Vérifier que le module est reconnu

1. Ouvrez **File → Project Structure** (Ctrl+Alt+Shift+S)
2. Allez dans **Modules**
3. Vérifiez que `eureka-server` est listé
4. Si ce n'est pas le cas, cliquez sur **+** et ajoutez le module `eureka-server/pom.xml`

### Étape 2 : Créer une configuration de Run

1. Cliquez sur le menu déroulant en haut à droite (à côté du bouton Run)
2. Sélectionnez **Edit Configurations...**
3. Cliquez sur **+** en haut à gauche
4. Sélectionnez **Spring Boot**
5. Configurez :
   - **Name** : `Eureka Server`
   - **Main class** : `evotingsystem.eurekaserver.EurekaServerApplication`
   - **Module** : Sélectionnez le module du projet
   - **Working directory** : `$PROJECT_DIR$/eureka-server`
6. Cliquez sur **OK**

### Étape 3 : Lancer Eureka Server

1. Sélectionnez **Eureka Server** dans le menu déroulant
2. Cliquez sur le bouton **Run** (▶️) ou appuyez sur **Shift+F10**

### Étape 4 : Vérifier

Ouvrez votre navigateur : http://localhost:8761

Vous devriez voir le dashboard Eureka.

---

## Méthode 2 : Lancer directement depuis la classe

1. Ouvrez le fichier `eureka-server/src/main/java/evotingsystem/eurekaserver/EurekaServerApplication.java`
2. Cliquez avec le bouton droit sur la classe
3. Sélectionnez **Run 'EurekaServerApplication'**

---

## ⚠️ Important

- **Démarrer Eureka Server EN PREMIER** avant tous les autres services
- Attendre que le message "Started EurekaServerApplication" apparaisse
- Vérifier que le port 8761 est libre

---

## 🐛 Problèmes Courants

### Le module n'est pas reconnu

**Solution** :
1. **File → Invalidate Caches / Restart**
2. **File → Reload Project from Disk**
3. Recharger les modules Maven : Clic droit sur le projet → **Maven → Reload Project**

### Port 8761 déjà utilisé

**Solution** :
```powershell
# Trouver le processus
netstat -ano | findstr :8761

# Arrêter le processus
taskkill /PID <PID> /F
```

### Erreur "Cannot find main class"

**Solution** :
1. Vérifiez que le fichier `EurekaServerApplication.java` existe
2. Recompilez le projet : **Build → Rebuild Project**
3. Vérifiez que le module est bien configuré dans Project Structure

