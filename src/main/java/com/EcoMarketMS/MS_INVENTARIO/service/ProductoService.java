package com.EcoMarketMS.MS_INVENTARIO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoMarketMS.MS_INVENTARIO.model.Producto;
import com.EcoMarketMS.MS_INVENTARIO.repo.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto>findAll(){
        return productoRepository.findAll();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public Producto findbyid(int Id){
        
        return productoRepository.findById(Id);
    }

    public void delete(int id){
        productoRepository.deleteById(id);
    }

    public boolean existeProducto(int id){
        return productoRepository.existsById(id);
    }

  
}
