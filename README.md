# ConstruApp

**Aplicación móvil Android para la gestión de inventario de una ferretería.**

ConstruApp digitaliza el control de stock de la *Ferretería El Constructor*, reemplazando el registro manual en cuadernos y hojas de Excel por una aplicación nativa que permite registrar, consultar, editar y eliminar productos, con visibilidad del inventario en tiempo real.

---

## El problema

La ferretería lleva el control de su inventario de forma manual, lo que ocasiona:

- Sin información del stock disponible en tiempo real.
- Errores en el conteo de productos.
- Dificultad para identificar artículos con bajo inventario.
- Pérdida de tiempo revisando existencias manualmente.
- Ventas perdidas por desabastecimiento no detectado a tiempo.

**Objetivo:** ofrecer una herramienta móvil sencilla que centralice la gestión del inventario, mantenga el stock actualizado y alerte visualmente sobre productos con bajo nivel de existencias.

---

## Características principales

- **Gestión CRUD de productos** — crear, consultar, editar y eliminar.
- **Listado en tiempo real** del inventario.
- **Indicador visual de stock** — distingue *stock normal*, *stock bajo* y *sin stock*.
- **Confirmación al eliminar** mediante diálogo, para evitar borrados accidentales.
- **Almacenamiento local offline** con base de datos Room (SQLite).
- **Login local simple** para acceder a la aplicación.
- **Interfaz moderna** basada en Material Design 3, con identidad de marca en naranja construcción (`#F57C00`).

---

## Stack tecnológico

| Componente | Versión / Detalle |
|---|---|
| Lenguaje | Kotlin 2.0.21 |
| UI | Jetpack Compose + Material 3 (Compose BOM 2024.09.00) |
| Navegación | Navigation Compose 2.8.4 |
| Inyección de dependencias | Hilt 2.57 |
| Persistencia | Room 2.6.1 (SQLite) |
| Procesador de anotaciones | KSP 2.0.21-1.0.28 |
| Build | Android Gradle Plugin 8.10.1 · Gradle 8.13 |
| SDK | minSdk 24 (Android 7.0) · compileSdk / targetSdk 36 |
| Java | 11 |

---

## Arquitectura

El proyecto sigue el patrón **MVVM + Repository** recomendado por Google, con separación en capas y dependencias unidireccionales (cada capa solo conoce a la inferior).

```
┌──────────────────────────────────────────────┐
│                  UI (Compose)                  │
│   LoginScreen · DashboardScreen · Product…    │
└───────────────────────┬────────────────────────┘
                        │  observa StateFlow
┌───────────────────────▼────────────────────────┐
│                   ViewModel                     │
│   LoginViewModel · ProductListViewModel · …    │
└───────────────────────┬────────────────────────┘
                        │  inyectado por Hilt
┌───────────────────────▼────────────────────────┐
│             Repository (interfaz)               │
│                ProductoRepository               │
└───────────────────────┬────────────────────────┘
                        │
┌───────────────────────▼────────────────────────┐
│               Data (Room / SQLite)              │
│      ProductoDao · ProductoEntity · Database    │
└──────────────────────────────────────────────┘
```

**Decisiones clave:**

- **Jetpack Compose** en lugar de Vistas/XML: UI declarativa y moderna.
- **MVVM + Repository**: separación de responsabilidades y código testeable; el repositorio abstrae la persistencia y facilita cambios futuros (p. ej. migrar a la nube).
- **Hilt**: inyección de dependencias con menos *boilerplate*.
- **Room**: ORM type-safe sobre SQLite, integrado con corrutinas y `Flow`.
- **Single Activity + Navigation Compose**: navegación fluida y manejo de estado simplificado.

---

## Estructura de paquetes

```
com.example.construapp
├── ConstruAppApplication.kt      // @HiltAndroidApp
├── MainActivity.kt               // @AndroidEntryPoint, host de navegación
├── data/                         // capa de datos
│   ├── local/
│   │   ├── ProductoEntity.kt
│   │   ├── ProductoDao.kt
│   │   └── ConstruAppDatabase.kt
│   └── repository/
│       ├── ProductoRepository.kt      // interfaz
│       └── ProductoRepositoryImpl.kt
├── domain/                       // modelos de dominio (sin frameworks)
│   └── model/
│       └── Producto.kt
├── di/                           // módulos Hilt
│   ├── DatabaseModule.kt
│   └── RepositoryModule.kt
└── ui/                           // capa de presentación (Compose)
    ├── navigation/               // Screen.kt · ConstruAppNavHost.kt
    ├── theme/                    // Color.kt · Theme.kt · Type.kt
    ├── components/               // ConstruAppTopBar.kt
    ├── login/
    ├── dashboard/
    ├── productlist/
    ├── productdetail/
    └── productform/
```

---

## Modelo de datos

Entidad principal: **Producto** (tabla `productos`).

| Atributo | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador único autogenerado |
| `nombre` | String | Nombre del producto (obligatorio) |
| `descripcion` | String | Descripción del producto |
| `precio` | Double | Precio unitario (debe ser positivo) |
| `cantidad` | Int | Unidades disponibles en stock (≥ 0) |

**Estados de stock** (derivados de `cantidad`):

| Estado | Condición |
|---|---|
| Sin stock | `cantidad = 0` |
| Stock bajo | `cantidad ≤ 5` |
| Stock normal | `cantidad > 5` |

---

## Pantallas y navegación

| Pantalla | Ruta de navegación | Propósito |
|---|---|---|
| Login | `login` | Acceso a la aplicación con nombre de usuario |
| Dashboard | `dashboard` | Menú principal y accesos rápidos |
| Listado | `products` | Lista de productos con búsqueda e indicadores de stock |
| Detalle | `products/{productId}` | Información completa del producto |
| Registro | `products/new` | Formulario para crear un producto |
| Edición | `products/{productId}/edit` | Formulario precargado para editar |

**Flujo de navegación:**

```
Login ──▶ Dashboard ──┬──▶ Listado ──▶ Detalle ──▶ Edición
                      │                  │
                      │                  └──▶ (diálogo Eliminar)
                      │
                      └──▶ Registro (FAB / acción rápida)
```

---

## Historias de usuario

| # | Historia | Pantalla | Prioridad |
|---|---|---|---|
| HU01 | Iniciar sesión en la aplicación | Login | Alta |
| HU02 | Acceder al menú principal | Dashboard | Alta |
| HU03 | Registrar un nuevo producto | Registro | Alta |
| HU04 | Consultar el listado de productos | Listado | Alta |
| HU05 | Ver el detalle de un producto | Detalle | Alta |
| HU06 | Editar la información de un producto | Edición | Alta |
| HU07 | Eliminar un producto del inventario | Detalle (diálogo) | Media |
| HU08 | Identificar productos con stock bajo | Listado | Media |

**Validaciones previstas:**

- *Nombre*: obligatorio.
- *Precio*: número positivo.
- *Cantidad*: número entero ≥ 0.
- *Usuario* (login): obligatorio.

---

## Compilación y ejecución

Requisitos: Android Studio (versión compatible con AGP 8.10.1) y JDK 11.

Desde la raíz del proyecto, en PowerShell:

```powershell
# Compilar el APK de depuración
.\gradlew assembleDebug

# Instalar en un emulador o dispositivo conectado
.\gradlew installDebug
```

También puedes abrir el proyecto en **Android Studio** y ejecutar la app con el botón **Run ▶** sobre un emulador o dispositivo físico.

---

## Estado del proyecto

Proyecto académico desarrollado por entregas (unidades).

**Estado actual:**

- Base técnica completa: Kotlin + Jetpack Compose + Material 3.
- Arquitectura MVVM + Repository con Hilt y Room configurados.
- Navegación implementada entre las 6 pantallas.
- Pantallas funcionando con datos de ejemplo (*mock*).

**Próximas fases:**

- Integración del CRUD real conectando los ViewModels con `ProductoRepository`.
- Validaciones visuales en los formularios de registro y edición.
- Indicador visual de stock bajo en el listado.
- Pruebas unitarias y de UI (Compose Test).
- Refinamiento de la experiencia de usuario.
