package com.EcoMarketMS.MS_INVENTARIO.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcoMarketMS.MS_INVENTARIO.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {
    
    Tienda findById(int idTienda);

}
