package com.EcoMarketMS.MS_INVENTARIO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.EcoMarketMS.MS_INVENTARIO.model.Producto;
import com.EcoMarketMS.MS_INVENTARIO.repo.ProductoRepository;

public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto>findAll(){
        return productoRepository.findAll();
    }
}
