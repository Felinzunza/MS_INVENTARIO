package com.EcoMarketMS.MS_INVENTARIO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.EcoMarketMS.MS_INVENTARIO.model.Producto;
import com.EcoMarketMS.MS_INVENTARIO.service.CategoriaService;
import com.EcoMarketMS.MS_INVENTARIO.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

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
    public ResponseEntity<Producto> postProducto(@RequestBody Producto producto) { //CAMBIE REQUESTBODY POR REQUESTPARAM
        Producto buscado = productoService.findbyid(producto.getCodProducto());
        if( buscado == null){
        if (producto.getCategoria() != null){ 
            producto.setCategoria(
                categoriaService.findById(producto.getCategoria().getIdCategoria())
            );
        }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable int id) {
        Producto producto = productoService.findbyid(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable int id, @RequestBody Producto producto) {
        Producto productoExistente = productoService.findbyid(id);
        if (productoExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        producto.setCodProducto(id); // Asegúrate de que el ID se mantenga igual
        return new ResponseEntity<>(productoService.save(producto), HttpStatus.OK);
    }
}
