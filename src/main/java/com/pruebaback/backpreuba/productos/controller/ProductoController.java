package com.pruebaback.backpreuba.productos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebaback.backpreuba.productos.dto.RespuestaGuardarProducto;
import com.pruebaback.backpreuba.productos.model.Producto;
import com.pruebaback.backpreuba.productos.service.ProductoService;

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
    public Optional<Producto> obtener(@PathVariable Long id) {
        System.out.println("Buscando producto con ID: " + id);
        return productoService.obtenerPorId(id);
    }

    @PostMapping("/guardar")
    public RespuestaGuardarProducto crear(@RequestBody Producto producto) {
        Producto productoOjb = productoService.guardar(producto);
        RespuestaGuardarProducto respuestaProducto = new RespuestaGuardarProducto();
        respuestaProducto.setMensaje("Producto guardado correctamente");
        respuestaProducto.setProducto(productoOjb);
        return respuestaProducto;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }

}
