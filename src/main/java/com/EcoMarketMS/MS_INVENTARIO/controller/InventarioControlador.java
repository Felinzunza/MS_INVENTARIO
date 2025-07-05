package com.EcoMarketMS.MS_INVENTARIO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.EcoMarketMS.MS_INVENTARIO.model.Inventario;
import com.EcoMarketMS.MS_INVENTARIO.model.Producto;
import com.EcoMarketMS.MS_INVENTARIO.model.Tienda;
import com.EcoMarketMS.MS_INVENTARIO.model.TipoMov;
import com.EcoMarketMS.MS_INVENTARIO.service.InventarioService;
import com.EcoMarketMS.MS_INVENTARIO.service.ProductoService;
import com.EcoMarketMS.MS_INVENTARIO.service.TiendaService;

@RestController
@RequestMapping("/api/inventario")
public class InventarioControlador {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TiendaService tiendaService;

    // Obtener todos los registros ordenados por idTienda
    @GetMapping
    public ResponseEntity<List<Inventario>> getTodos() {
        List<Inventario> lista = inventarioService.obtenerTodos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Obtener registros por tienda
    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<List<Inventario>> getPorTienda(@PathVariable int idTienda) {
        List<Inventario> lista = inventarioService.obtenerPorTienda(idTienda);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Obtener registros por código de producto (No considerado en el momento)
   @GetMapping("/producto/{codProducto}")
    public ResponseEntity<List<Inventario>> getPorProducto(@PathVariable int codProducto) {
        List<Inventario> lista = inventarioService.obtenerPorProducto(codProducto);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getPorId(@PathVariable int id) {
        Inventario inventario = inventarioService.obtenerPorId(id);
        if (inventario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    // Guardar nuevo registro
    @PostMapping
    public ResponseEntity<Inventario> guardar(@RequestBody Inventario inventario) {
        
    int idProd = inventario.getProducto().getCodProducto();
    int idTienda = inventario.getTienda().getIdTienda();

    if (!productoService.existeProducto(idProd) || !tiendaService.existeTienda(idTienda)) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Producto producto = productoService.findbyid(idProd);
    inventario.setProducto(producto);
    Tienda tienda = tiendaService.buscarTiendaxId(idTienda);
    inventario.setTienda(tienda);

    if (inventario.getStock() < 0) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(inventarioService.guardar(inventario), HttpStatus.CREATED);
}


    // Eliminar por ID de inventario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        Inventario inv = inventarioService.obtenerPorId(id);
        if (inv == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        inventarioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Ajustar stock por ID de inventario, http://localhost:8081/api/inventario/1/ajustar-stock?tipo=AJUSTE&cantidad=10
    @PatchMapping("/{id}/ajustar-stock")
    public ResponseEntity<Inventario> ajustarStockXIdInventario(
            @PathVariable int id,
            @RequestParam TipoMov tipo,
            @RequestParam int cantidad) {

        Inventario actualizado = inventarioService.ajustarStock(id, tipo, cantidad);

        if (actualizado == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }


}