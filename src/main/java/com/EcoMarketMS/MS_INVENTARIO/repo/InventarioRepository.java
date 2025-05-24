package com.EcoMarketMS.MS_INVENTARIO.repo;

import java.util.List;
import com.EcoMarketMS.MS_INVENTARIO.model.Inventario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    
    List<Inventario> findByTiendaIdTienda(int idTienda);
    List<Inventario> findByProductoCodProducto(int codProducto); 
    Inventario findById(int id);   
}
