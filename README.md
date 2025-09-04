# 🏨 Application de Réservation - Backend (Spring Boot + PostgreSQL)

## 📌 Description
Ce projet représente le backend d'une application de réservation de salles ou de chambres d'hôtel. Il est développé avec **Spring Boot**, **Spring Security (JWT)**, et utilise **PostgreSQL** comme base de données.

L'application gère :
- Authentification et autorisation (JWT)
- Gestion des utilisateurs (Admin & Client)
- Gestion des hôtels et des chambres
- Réservations avec statuts (PENDING, CONFIRMED, CANCELED)

---

## 🛠️ Technologies Utilisées
- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Security + JWT**
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger OpenAPI** (Documentation API)
- **Maven**

---

## ✅ Prérequis
Avant de démarrer, assurez-vous d'avoir :
- **Java 21** installé
- **PostgreSQL** installé et en cours d'exécution
- **Maven** installé
- **Un outil comme Postman** pour tester les endpoints

---

## ⚙️ Configuration
### 1. Base de données PostgreSQL
Créer une base de données :
```sql
CREATE DATABASE reservation_app;
```

### 2. Fichier `application.properties`
Configurer PostgreSQL :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/reservation_app
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
jwt.secret=
.....
```

---

## 🏗️ Structure du Projet
```
src/main/java/com/example/reservation
├── config           # Configurations (Security, Swagger, etc.)
├── controller       # Controllers REST
├── dto             # Data Transfer Objects
├── entity          # Entités JPA
├── enums           # Enumérations (ReservationStatus, Role)
├── repository      # Interfaces JPA Repository
├── service         # Services métiers
└── security        # JWT, Filters, Authentication
```

---

## 👥 Rôles et Permissions
- **ADMIN**
    - Gérer les hôtels et les chambres (CRUD)
    - Voir toutes les réservations
    - Confirmer / Annuler une réservation
- **CLIENT**
    - Réserver une chambre
    - Voir ses propres réservations
    - Annuler une réservation

---

## 🔐 Authentification & Autorisation
- **Login & Register** via `/api/v1/auth/**`
- Génération d'un token JWT
- Protection des endpoints avec **Spring Security** :
```
/api/v1/auth/** → Public
/rooms → GET (ADMIN, CLIENT), POST/PUT/DELETE (ADMIN)
/hotels → CRUD (ADMIN)
/reservations → POST (CLIENT), GET (ADMIN & CLIENT), PUT (ANNULER / CONFIRMER)
```

---

## 🔗 Endpoints Principaux
### Authentification
- `POST /api/v1/auth/register` → Inscription
- `POST /api/v1/auth/login` → Connexion

### Gestion des hôtels & chambres
- `GET /rooms` → Liste des chambres
- `POST /rooms` → Ajouter une chambre (**ADMIN**)
- `PUT /rooms/{id}` → Modifier (**ADMIN**)
- `DELETE /rooms/{id}` → Supprimer (**ADMIN**)

### Réservations
- `POST /reservations` → Créer une réservation (**CLIENT**)
- `GET /reservations/me` → Voir ses réservations (**CLIENT**)
- `GET /reservations` → Voir toutes les réservations (**ADMIN**)
- `PUT /reservations/{id}/confirm` → Confirmer (**ADMIN**)
- `PUT /reservations/{id}/cancel` → Annuler (**ADMIN** ou **CLIENT**)

---

## ▶️ Lancer l'application
```bash
mvn spring-boot:run
```
Swagger sera disponible sur :
```
http://localhost:8080/swagger-ui.html
```

---

## ✅ Tests avec Postman
- Importer la collection Postman (disponible dans `/postman_collection`)
- Tester l'inscription, la connexion, la création de chambres, et les réservations.

---

## 🛡️ Sécurité
- **JWT Token** pour toutes les requêtes sécurisées
- Les mots de passe sont hachés avec **BCrypt**
- Protection CSRF désactivée pour API REST

---

## 📂 Liens Utiles
- [Documentation Swagger](http://localhost:8080/swagger-ui.html)
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [PostgreSQL Docs](https://www.postgresql.org/docs/)

---

### ✅ Auteur
**Développé par :** Aymane IMLIHI
