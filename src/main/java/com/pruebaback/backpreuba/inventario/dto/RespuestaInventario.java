package com.pruebaback.backpreuba.inventario.dto;

import com.pruebaback.backpreuba.productos.model.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaInventario {

    private Producto producto;
    private long cantidadDisponible;
}
