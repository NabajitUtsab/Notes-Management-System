# 📝 Notes Management API

A secure, RESTful Spring Boot API for creating and managing personal notes. Features JWT-based authentication, role-based access control, and full CRUD operations — with no UI required.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot |
| Security | Spring Security + JWT (JJWT) |
| Database | MySQL |
| ORM | Spring Data JPA / Hibernate |
| Validation | Jakarta Validation (`@NotBlank`, `@Valid`) |
| Boilerplate | Lombok |
| Build Tool | Maven |

---

## 📁 Project Structure

```
src/main/java/com/example/Notes_Management_Api/
│
├── configuration/
│   ├── JwtUtil.java            # JWT generation & validation
│   ├── JwtFilter.java          # JWT request filter
│   └── SecurityConfig.java     # Security chain configuration
│
├── controller/
│   ├── RegistrationController.java   # Auth endpoints (register, login)
│   ├── UserController.java           # Note CRUD for users
│   └── AdminController.java          # Admin note management
│
├── service/
│   ├── RegistrationService.java
│   ├── LoginService.java
│   ├── UserService.java
│   ├── AdminService.java
│   └── CustomUserDetailsService.java
│
├── entity/
│   ├── AppUser.java
│   └── Note.java
│
├── dto/
│   ├── RegisterRequest.java
│   ├── NoteRequest.java
│   ├── NoteResponse.java
│   ├── RegistrationResponse.java
│   └── CommonResponse.java
│
├── repository/
│   ├── AppUserRepo.java
│   └── NoteRepo.java
│
└── exceptionHandler/
    └── GlobalExceptionHandler.java
```

---

## ⚙️ Setup & Configuration

### 1. Clone the repository

```bash
git clone https://github.com/NabajitUtsab/Notes-Management-System.git
cd notes-management-api
```

### 2. Create the MySQL database

```sql
CREATE DATABASE notes_management_db;
```

### 3. Configure `application.properties`

```properties
# Server
server.port=8080

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/notes_management_db
spring.datasource.username=root
spring.datasource.password=your_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret.key=your_secret_key_here
jwt.expiration=86400000
```

> ⚠️ Never commit real credentials. Use environment variables or a `.env` file in production.

### 4. Run the application

```bash
mvn spring-boot:run
```

The API will start at `http://localhost:8080`.

---

## 🔐 Authentication Flow

This API uses **stateless JWT authentication**.

```
1. Register  →  POST /api/auth/register/user
2. Login     →  POST /api/auth/login  →  receive JWT token
3. Use token →  Add to every request header:
                Authorization: Bearer <your_token>
```

---

## 📡 API Reference

### Auth Endpoints — `/api/auth` (public)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register/user` | Register a new user |
| `POST` | `/api/auth/register/admin` | Register a new admin |
| `POST` | `/api/auth/login` | Login and receive JWT token |

**Register / Login request body:**
```json
{
  "name": "john",
  "password": "secret123"
}
```

**Login response:**
```json
{
  "username": "john",
  "message": "Login successful. Token: eyJhbGci..."
}
```

---

### Notes Endpoints — `/api/notes` (ROLE_USER only)

> All requests require: `Authorization: Bearer <token>`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/notes` | Get all notes for the logged-in user |
| `GET` | `/api/notes/{id}` | Get a specific note by ID |
| `POST` | `/api/notes` | Create a new note |
| `PUT` | `/api/notes/{id}` | Update an existing note |
| `DELETE` | `/api/notes/{id}` | Delete a note |

**Create / Update request body:**
```json
{
  "title": "My Note",
  "content": "This is the note content."
}
```

**Note response:**
```json
{
  "id": 1,
  "title": "My Note",
  "content": "This is the note content."
}
```

---

### Admin Endpoints — `/api/admin` (ROLE_ADMIN only)

> All requests require: `Authorization: Bearer <admin_token>`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/admin/notes` | Get all notes from all users |
| `DELETE` | `/api/admin/notes/{id}` | Delete any note by ID |

---

## ✅ Validation Rules

| Field | Rule |
|---|---|
| `name` (username) | Must not be blank |
| `password` | Must not be blank |
| `title` | Must not be blank |
| `content` | Must not be blank |

Validation errors return HTTP `400` with a clear message:
```json
{
  "timestamp": "2025-01-01T00:00:00Z",
  "error": "Validation Error",
  "message": "title: Title must not be empty"
}
```

---

## ⚠️ Error Responses

All errors return a consistent JSON body:

```json
{
  "timestamp": "2025-01-01T12:00:00Z",
  "error": "Not Found",
  "message": "Note not found"
}
```

| HTTP Status | Scenario |
|---|---|
| `400` | Validation failure |
| `401` | Missing or invalid JWT |
| `403` | Accessing another user's note |
| `404` | Note or user not found |
| `500` | Unexpected server error |

---

## 🔒 Security Notes

- Passwords are hashed with **BCrypt** before storage.
- JWT tokens are signed with **HS256** and expire after 24 hours (configurable).
- Users can only read, update, or delete **their own notes**.
- Admins can view and delete **any note** across all users.
- Sessions are **stateless** — no server-side session storage.

---

## 🧪 Testing with Postman

1. Register a user via `POST /api/auth/register/user`
2. Login via `POST /api/auth/login` — copy the token from the response
3. In Postman, go to the **Authorization** tab → select **Bearer Token** → paste your token
4. Call any `/api/notes/**` endpoint

---

