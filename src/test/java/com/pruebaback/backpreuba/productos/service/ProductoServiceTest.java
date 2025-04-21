package com.pruebaback.backpreuba.productos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pruebaback.backpreuba.productos.model.Producto;
import com.pruebaback.backpreuba.productos.repository.ProductoRepository;

public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerProductos() {
        List<Producto> productos = Arrays.asList(
            new Producto(1L, "Producto A", 100.0),
            new Producto(2L, "Producto B", 200.0)
        );

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.obtenerProductos();
        assertEquals(2, resultado.size());
        verify(productoRepository).findAll();
    }

    @Test
    void testObtenerPorIdExistente() {
        Producto producto = new Producto(1L, "Producto A", 100.0);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Producto A", resultado.get().getNombre());
    }

    @Test
    void testGuardarProducto() {
        Producto producto = new Producto(null, "Nuevo", 99.99);
        Producto guardado = new Producto(1L, "Nuevo", 99.99);

        when(productoRepository.save(producto)).thenReturn(guardado);

        Producto resultado = productoService.guardar(producto);
        assertEquals(1L, resultado.getId());
        assertEquals("Nuevo", resultado.getNombre());
    }

    @Test
    void testEliminarProducto() {
        Long id = 1L;
        productoService.eliminar(id);
        verify(productoRepository).deleteById(id);
    }

    @Test
    void testActualizarProductoExistente() {
        Producto existente = new Producto(1L, "Viejo", 100.0);
        Producto nuevo = new Producto(null, "Nuevo", 150.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Producto actualizado = productoService.actualizarProducto(1L, nuevo);

        assertEquals("Nuevo", actualizado.getNombre());
        assertEquals(150.0, actualizado.getPrecio());
    }

    @Test
    void testActualizarProductoNoExistente() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        Producto nuevo = new Producto(null, "Nuevo", 150.0);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            productoService.actualizarProducto(999L, nuevo)
        );

        assertEquals("Producto no encontrado", exception.getMessage());
    }
}
