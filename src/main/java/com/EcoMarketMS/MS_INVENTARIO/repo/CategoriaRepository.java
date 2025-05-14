package com.EcoMarketMS.MS_INVENTARIO.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcoMarketMS.MS_INVENTARIO.model.Categoria;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    Categoria findById(int id); 
  
}
