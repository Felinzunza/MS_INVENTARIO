package com.EcoMarketMS.MS_INVENTARIO.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.EcoMarketMS.MS_INVENTARIO.model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto>findAll();

    }

    