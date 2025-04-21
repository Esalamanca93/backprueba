package com.pruebaback.backpreuba.inventario.dto;

import lombok.Data;

@Data
public class ActualizarInventarioRequest {
    private Long productoId;
    private int nuevaCantidad;
}

