package com.pruebaback.backpreuba.inventario.dto;

import lombok.Data;

@Data
public class CrearInventarioRequest {
    private Long productoId;
    private int cantidad;
}
