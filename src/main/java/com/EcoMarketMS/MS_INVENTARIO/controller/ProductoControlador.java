package com.EcoMarketMS.MS_INVENTARIO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcoMarketMS.MS_INVENTARIO.model.Producto;
import com.EcoMarketMS.MS_INVENTARIO.service.ProductoService;

@RestController
@RequestMapping("api/v1/productos")
public class ProductoControlador {
    @Autowired
    private ProductoService productoService;

    public ResponseEntity<List<Producto>>getProductos(){
        List<Producto>productos = productoService.findAll();

        if(productos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        };
        return new ResponseEntity<>(productoService.findAll(), HttpStatus.OK
        );
    }
}
