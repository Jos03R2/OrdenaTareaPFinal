# 📌 OrdenaTareas – Sistema de Gestión de Tareas con Prioridad

Este proyecto implementa un sistema backend completo utilizando Java con Spring Boot, orientado a la gestión de tareas asignadas a usuarios. El sistema incluye un historial de acciones, estructuras de datos personalizadas y una arquitectura modular. Representa un modelo funcional similar al usado en bancos, clínicas o centros de servicio con turnos y prioridades.

---

## 🧱 Estructura del Proyecto

El sistema está dividido en **3 módulos Maven** independientes para mantener una arquitectura organizada y escalable:

### 1. `ordenatareas` (Módulo principal)
- Controladores REST: `Usuario`, `Tarea`, `Historial`, `Cola`
- Servicios con lógica en memoria usando estructuras personalizadas (`ColaDeTareas`)
- Integración con:
  - MySQL (base de datos)
  - RabbitMQ (mensajería)
  - Swagger (documentación de APIs)
- Configuración general en `application.properties`

### 2. `datostareas` (Módulo de persistencia JPA)
- Entidades:
  - `Usuario`
  - `Tarea`
  - `Historial`
- Repositorios correspondientes
- Conecta con la base de datos MySQL (`SystemPriority_db`)

### 3. `estructurastareasproyecto` (Estructuras de datos personalizadas)
- Implementación desde cero de:
  - `Cola`, `Pila`, `Lista`, `Árbol`
  - `NodoCola`, `NodoLista`, `NodoArbol`
- No se utilizan colecciones estándar (`java.util`)
- Estas estructuras se usan para manejar tareas, historial, jerarquía de subtareas, etc.

---

## 💾 Base de Datos: `SystemPriority_db` (MySQL)

- Tabla `usuario`: contiene datos como nombre y correo del usuario.
- Tabla `tarea`: incluye descripción, prioridad y asociación con un usuario.
- Tabla `historial`: registra automáticamente eventos del sistema (ej. cuando se encola una tarea).

---

## 🔗 Endpoints Disponibles (Swagger habilitado)

### 📁 Usuario
| Método | Endpoint | Acción |
|--------|----------|--------|
| `POST` | `/api/usuario` | Crear usuario |
| `GET`  | `/api/usuario` | Listar usuarios |
| `GET`  | `/api/usuario/{id}` | Obtener usuario por ID |
| `DELETE` | `/api/usuario/{id}` | Eliminar usuario |

### 📁 Tarea
| Método | Endpoint | Acción |
|--------|----------|--------|
| `POST` | `/api/tarea` | Crear tarea |
| `GET`  | `/api/tarea` | Listar tareas |
| `GET`  | `/api/tarea/{id}` | Obtener tarea por ID |
| `DELETE` | `/api/tarea/{id}` | Eliminar tarea |

### 📁 Historial
| Método | Endpoint | Acción |
|--------|----------|--------|
| `POST` | `/api/historial/usuario/{id}?descripcion=...` | Registrar evento |
| `GET`  | `/api/historial` | Listar historial completo |
| `GET`  | `/api/historial/{id}` | Obtener registro por ID |
| `DELETE` | `/api/historial/{id}` | Eliminar registro |

### 🔁 Cola (estructura personalizada)
| Método | Endpoint | Acción |
|--------|----------|--------|
| `POST` | `/api/cola/agregar` | Encolar nueva tarea |
| `GET`  | `/api/cola/obtener` | Desencolar primera tarea |
| `GET`  | `/api/cola/vacia` | Verificar si la cola está vacía |
| `GET`  | `/api/cola/ver` | Ver primera tarea sin removerla |
| `GET`  | `/api/cola/listar` | Listar todas las tareas encoladas |

---

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- MySQL 8
- RabbitMQ
- Swagger UI
- Postman (pruebas)
- MongoDB (opcional para logs futuros)

---

## ✅ Funcionalidades Completadas

- [x] CRUD de usuarios, tareas e historial
- [x] Registro automático de eventos en historial
- [x] Cola de tareas con estructura propia (`ColaDeTareas`)
- [x] Integración con RabbitMQ
- [x] Endpoints documentados con Swagger
- [x] Arquitectura modular (3 proyectos Maven)
- [x] Pruebas exitosas con Postman
- [x] Base de datos verificada en MySQL Workbench

---

## 🚀 Posibles Mejoras Futuras

- Almacenamiento de logs en MongoDB
- Exportación de reportes PDF o Excel
- Interfaz gráfica web (frontend)
- Autenticación de usuarios con Spring Security

---

## 🎓 Autor

**Universidad Mariano Gálvez de Guatemala**  
Estudiante: Byron Josue Salvador Rosario Lopez  
Carné: 9989-23-6328  
Proyecto Final – Estructuras de Datos II

---

