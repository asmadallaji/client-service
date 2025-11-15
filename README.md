# Client Service API - Spring Boot Backend

Backend REST API pour la gestion des clients avec Spring Boot 3.2, Java 17, et MySQL.

## 📋 Fonctionnalités

- ✅ Enregistrement de nouveaux clients (POST `/api/clients/register`)
- ✅ Validation complète des champs obligatoires
- ✅ Vérification d'unicité de l'email et du username
- ✅ Hachage des mots de passe avec BCrypt
- ✅ Authentification avec login/password
- ✅ Gestion des rôles (CLIENT, ADMIN)
- ✅ Gestion du statut (ACTIVE, INACTIVE, SUSPENDED)
- ✅ CRUD complet pour les clients
- ✅ Spring Security configuré

## 🔧 Configuration Requise

- **Java 17** ou supérieur
- **Maven 3.6+**
- **MySQL 8.0+**

## 📦 Installation

### 1. Cloner le dépôt

```bash
git clone https://github.com/asmadallaji/client-service.git
cd client-service
```

### 2. Configuration de la base de données MySQL

Créez une base de données MySQL :

```sql
CREATE DATABASE client_service;
USE client_service;
```

### 3. Configurer les variables d'environnement

Modifiez `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/client_service?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your_super_secret_jwt_key_that_must_be_at_least_32_characters_long_for_hs512
```

### 4. Compiler et lancer l'application

```bash
mvn clean install
mvn spring-boot:run
```

L'application démarre sur `http://localhost:8080/api`

## 🚀 Endpoints API

### 1. Enregistrement (Public)
**POST** `/api/clients/register`

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "+1 (555) 123-4567",
  "username": "johndoe",
  "password": "SecurePassword123",
  "address": "123 Main St, City",
  "role": "CLIENT"
}
```

**Réponse (201 Created):**
```json
{
  "success": true,
  "message": "Client registered successfully",
  "user": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "role": "CLIENT",
    "status": "ACTIVE",
    "createdAt": "2024-01-15T10:30:00"
  }
}
```

### 2. Login (Public)
**POST** `/api/clients/login`

```json
{
  "username": "johndoe",
  "password": "SecurePassword123"
}
```

### 3. Récupérer tous les clients
**GET** `/api/clients`

### 4. Récupérer un client par ID
**GET** `/api/clients/{id}`

### 5. Récupérer par email (Public)
**GET** `/api/clients/email/{email}`

### 6. Récupérer par username (Public)
**GET** `/api/clients/username/{username}`

### 7. Mettre à jour un client
**PUT** `/api/clients/{id}`

### 8. Supprimer un client
**DELETE** `/api/clients/{id}`

### 9. Changer le mot de passe
**POST** `/api/clients/change-password/{id}`

```json
{
  "oldPassword": "OldPassword123",
  "newPassword": "NewPassword456"
}
```

### 10. Récupérer par rôle
**GET** `/api/clients/role/{role}` (CLIENT ou ADMIN)

### 11. Récupérer par statut
**GET** `/api/clients/status/{status}` (ACTIVE, INACTIVE, SUSPENDED)

## 🔐 Sécurité

- **Authentification HTTP Basic** configurée
- **CORS** activé pour toutes les origines
- **BCrypt** pour le hachage des mots de passe
- **Validation JSR-303** sur tous les inputs
- **Exception handling** global

## 📊 Structure du projet

```
src/main/
├── java/com/clientservice/
│   ├── ClientServiceApplication.java      # Main entry point
│   ├── controller/
│   │   └── ClientController.java          # REST endpoints
│   ├── service/
│   │   └── ClientService.java             # Business logic
│   ├── repository/
│   │   └── ClientRepository.java          # Database access
│   ├── entity/
│   │   ├── Client.java                    # JPA Entity
│   │   ├── UserRole.java                  # Enum: CLIENT, ADMIN
│   │   └── UserStatus.java                # Enum: ACTIVE, INACTIVE, SUSPENDED
│   ├── dto/
│   │   └── ClientDTO.java                 # Data Transfer Object
│   ├── mapper/
│   │   └── ClientDTOMapper.java           # Entity <-> DTO mapping
│   ├── config/
│   │   └── SecurityConfig.java            # Spring Security configuration
│   └── exception/
│       ├── GlobalExceptionHandler.java    # Exception handling
│       ├── ResourceNotFoundException.java
│       └── ResourceAlreadyExistsException.java
└── resources/
    └── application.properties              # Configuration
```

## 🧪 Tests avec cURL

### Enregistrer un nouveau client
```bash
curl -X POST http://localhost:8080/api/clients/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane@example.com",
    "phone": "+1 (555) 987-6543",
    "username": "janesmith",
    "password": "SecurePass123",
    "address": "456 Oak Ave",
    "role": "CLIENT"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/clients/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "janesmith",
    "password": "SecurePass123"
  }'
```

### Récupérer tous les clients (authentification requise)
```bash
curl -X GET http://localhost:8080/api/clients \
  -u root:password
```

## 🐛 Troubleshooting

### Erreur: "Unable to locate the Java Compiler in:"
Assurez-vous que `JAVA_HOME` pointe vers le JDK (pas JRE) :
```bash
export JAVA_HOME=/path/to/jdk17
```

### Erreur de connexion MySQL
Vérifiez que MySQL est démarré et que les identifiants sont corrects dans `application.properties`

### Port 8080 déjà utilisé
Modifiez le port dans `application.properties` :
```properties
server.port=8081
```

## 📝 Changelog

### v1.0.0
- ✅ Configuration initiale Spring Boot 3.2
- ✅ Endpoint d'enregistrement complet
- ✅ CRUD pour les clients
- ✅ Authentification basique
- ✅ Hachage BCrypt des mots de passe

## 📄 Licence

Ce projet est sous licence MIT.

## 👤 Auteur

AsmaD - Client Service API

## 📞 Support

Pour toute question ou problème, créez une issue sur GitHub.