package com.paq.lab.dto;

import jakarta.validation.constraints.NotBlank;

public class FormData {
    @NotBlank
    private String nombre;
    @NotBlank
    private String direccion;
    @NotBlank
    private String telefono;
    @NotBlank
    private String codigo;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
