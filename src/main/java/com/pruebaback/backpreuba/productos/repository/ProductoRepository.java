package com.pruebaback.backpreuba.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pruebaback.backpreuba.productos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    
}
