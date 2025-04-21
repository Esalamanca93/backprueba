package com.pruebaback.backpreuba.productos.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pruebaback.backpreuba.productos.dto.RespuestaProducto;
import com.pruebaback.backpreuba.productos.model.Producto;
import com.pruebaback.backpreuba.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> listarProductos() {
        return productoService.obtenerProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaProducto> obtener(@PathVariable Long id) {
        Optional<Producto> productoOpt = productoService.obtenerPorId(id);

        if (productoOpt.isPresent()) {
            return ResponseEntity.ok(new RespuestaProducto("Producto encontrado", productoOpt.get()));
        } else {
            return ResponseEntity
                    .status(404)
                    .body(new RespuestaProducto("Producto no encontrado", null));
        }
    }

    @Operation(summary = "Guardar un nuevo producto", 
    description = "Crea un nuevo producto en el sistema con nombre y precio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
             description = "Producto guardado correctamente",
             content = @Content(mediaType = "application/json",
             schema = @Schema(implementation = RespuestaProducto.class)))
    })
    @PostMapping("/guardar")
    public RespuestaProducto crear(@RequestBody Producto producto) {
        Producto productoOjb = productoService.guardar(producto);
        RespuestaProducto respuestaProducto = new RespuestaProducto();
        respuestaProducto.setMensaje("Producto guardado correctamente");
        respuestaProducto.setProducto(productoOjb);
        return respuestaProducto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaProducto> eliminar(@PathVariable Long id) {
        Optional<Producto> productoCodigo = productoService.obtenerPorId(id);

        if (productoCodigo.isPresent()) {
            productoService.eliminar(id);
            return ResponseEntity.ok(new RespuestaProducto("Producto eliminado", null));
        } else {
            return ResponseEntity.status(404).body(new RespuestaProducto("Producto no encontrado", null));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaProducto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);

        RespuestaProducto respuestaActuProducto = new RespuestaProducto();
        respuestaActuProducto.setMensaje("Producto actualizado");
        respuestaActuProducto.setProducto(productoActualizado);

        return ResponseEntity.ok(respuestaActuProducto);
    }

}
