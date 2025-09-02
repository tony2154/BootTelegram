package com.paq.lab.controller;

import com.paq.lab.dto.FormData;
import com.paq.lab.service.TelegramService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FormController {

    private final TelegramService telegramService;

    public FormController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping(value = "/api/submit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> submitJson(@Valid @RequestBody FormData data, HttpServletRequest request) {
        String message = TelegramService.buildMessage(
                data.getNombre(), data.getDireccion(), data.getTelefono(), data.getCodigo(),
                request.getRemoteAddr(), request.getHeader("User-Agent")
        );
        telegramService.sendToTelegram(message);

        Map<String, Object> body = new HashMap<>();
        body.put("ok", true);
        body.put("message", "Datos recibidos (LAB).");
        return ResponseEntity.ok(body);
    }

    // Fallback for classic form posts (application/x-www-form-urlencoded)
    @PostMapping(value = "/form/submit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView submitForm(FormData data, HttpServletRequest request) {
        String message = TelegramService.buildMessage(
                data.getNombre(), data.getDireccion(), data.getTelefono(), data.getCodigo(),
                request.getRemoteAddr(), request.getHeader("User-Agent")
        );
        telegramService.sendToTelegram(message);
        return new RedirectView("/thanks.html");
    }
}
