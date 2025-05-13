package com.EcoMarketMS.MS_INVENTARIO.repo;


import org.springframework.stereotype.Repository;

import com.EcoMarketMS.MS_INVENTARIO.model.Producto;



import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {



    Producto findById(int id);




    }

    