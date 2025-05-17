package com.EcoMarketMS.MS_INVENTARIO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.EcoMarketMS.MS_INVENTARIO.model.Producto;
import com.EcoMarketMS.MS_INVENTARIO.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
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
    public ResponseEntity<Producto>postProducto(@RequestBody Producto producto){ //CAMBIE REQUESTBODY POR REQUESTPARAM
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable int id) {
        Producto producto = productoService.findbyid(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

        // ✅ Método para ajustar stock
    @PatchMapping("/{id}/ajustar-stock")
    public ResponseEntity<Producto> ajustarStock(
        @PathVariable int id,
        @RequestParam String tipo,
        @RequestParam int cantidad) {

        Producto producto = productoService.findbyid(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (cantidad <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (tipo.equalsIgnoreCase("INGRESO")) {
            producto.setStock(producto.getStock() + cantidad);
        } else if (tipo.equalsIgnoreCase("SALIDA")) {
            if (producto.getStock() < cantidad) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }    
            // Verificar si hay suficiente stock para la salida         
            producto.setStock(producto.getStock() - cantidad);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        productoService.save(producto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }
}
