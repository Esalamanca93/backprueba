package com.pruebaback.backpreuba.productos.dto;

import com.pruebaback.backpreuba.productos.model.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaGuardarProducto {
    private String mensaje;
    private Producto producto;
}
