package com.EcoMarketMS.MS_INVENTARIO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EcoMarketMS.MS_INVENTARIO.model.Producto;
import com.EcoMarketMS.MS_INVENTARIO.service.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoControlador {
    @Autowired
    private ProductoService productoService;

    // @GetMapping("/productos")

    @GetMapping
    public ResponseEntity<List<Producto>> getProductos() {
    List<Producto> productos = productoService.findAll();
    if (productos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producto>postProducto(@RequestParam Producto producto){
        Producto buscado = productoService.findbyid(producto.getCod_Producto());
        if( buscado == null){
             return new ResponseEntity<>(productoService.save(producto), HttpStatus.CREATED);
        }
        return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        Producto producto = productoService.findbyid(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }
}
