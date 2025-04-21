package com.pruebaback.backpreuba.inventario.controller;

import com.pruebaback.backpreuba.inventario.dto.ActualizarInventarioRequest;
import com.pruebaback.backpreuba.inventario.dto.CrearInventarioRequest;
import com.pruebaback.backpreuba.inventario.model.Inventario;
import com.pruebaback.backpreuba.inventario.service.InventarioService;
import com.pruebaback.backpreuba.productos.model.Producto;
import com.pruebaback.backpreuba.inventario.dto.RespuestaInventario;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping("/{productoId}")
    public RespuestaInventario consultarInventario(@PathVariable Long productoId) {
        Optional<Inventario> cantidadInventario = inventarioService.buscarInventarioPorProductoId(productoId);
        if (cantidadInventario.isPresent()) {

            Inventario inventario = cantidadInventario.get();
            Producto producto = inventarioService.obtenerProductoDesdeMicroservicio(productoId);

            RespuestaInventario respuesta = new RespuestaInventario();
            respuesta.setProducto(producto);
            respuesta.setCantidadDisponible(inventario.getCantidad());

            return respuesta;
        } else {
            throw new RuntimeException("Inventario no encontrado");
        }

    }

    @PostMapping("/crea")
    public Inventario crearInventario(@RequestBody CrearInventarioRequest request) {
        return inventarioService.crearInventario(request.getProductoId(), request.getCantidad());
    }

    @PutMapping("/actualizar")
    public RespuestaInventario actualizarInventario(@RequestBody ActualizarInventarioRequest request) {
        Inventario inventario = inventarioService.actualizarCantidad(request.getProductoId(),
                request.getNuevaCantidad());
        Producto producto = inventarioService.obtenerProductoDesdeMicroservicio(request.getProductoId());

        RespuestaInventario respuesta = new RespuestaInventario();
        respuesta.setProducto(producto);
        respuesta.setCantidadDisponible(inventario.getCantidad());

        return respuesta;
    }

}
