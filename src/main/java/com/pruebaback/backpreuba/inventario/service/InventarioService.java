package com.pruebaback.backpreuba.inventario.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.pruebaback.backpreuba.inventario.model.Inventario;
import com.pruebaback.backpreuba.inventario.repository.InventarioRepository;
import com.pruebaback.backpreuba.productos.dto.RespuestaProducto;
import com.pruebaback.backpreuba.productos.model.Producto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository inventarioRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<Inventario> buscarInventarioPorProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public Producto obtenerProductoDesdeMicroservicio(Long productoId) {
        String url = "http://localhost:8080/api/productos/" + productoId;
        RespuestaProducto respuestaProductoDto = restTemplate.getForObject(url, RespuestaProducto.class);
        return respuestaProductoDto.getProducto();
    }

    public Inventario crearInventario(Long productoId, int cantidad) {
        Inventario inventario = new Inventario();
        inventario.setProductoId(productoId);
        inventario.setCantidad(cantidad);
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizarCantidad(Long productoId, int nuevaCantidad) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findByProductoId(productoId);

        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setCantidad(nuevaCantidad);
            Inventario actualizado = inventarioRepository.save(inventario);

            System.out.println(
                    "Inventario actualizado para producto ID " + productoId + ": nueva cantidad = " + nuevaCantidad);

            return actualizado;
        } else {
            throw new RuntimeException("Inventario no encontrado para el producto ID: " + productoId);
        }
    }

}
