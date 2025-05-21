package com.EcoMarketMS.MS_INVENTARIO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.EcoMarketMS.MS_INVENTARIO.model.Inventario;
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
   /*  @GetMapping("/producto/{codProducto}")
    public ResponseEntity<List<Inventario>> getPorProducto(@PathVariable int codProducto) {
        List<Inventario> lista = inventarioService.obtenerPorProducto(codProducto);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }*/

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
    public ResponseEntity<?> guardar(@RequestBody Inventario inventario) { //el signo ? significa que no se sabe que tipo de objeto es
        
    int idProd = inventario.getProducto().getCod_Producto();
    int idTienda = inventario.getTienda().getIdTienda();

    if (!productoService.existeProducto(idProd) || !tiendaService.existeTienda(idTienda)) {
        return new ResponseEntity<>("Producto o tienda no existen", HttpStatus.BAD_REQUEST);
    }

    if (inventario.getStock() < 0) {
        return new ResponseEntity<>("El stock no puede ser negativo", HttpStatus.BAD_REQUEST);
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

    // Ajustar stock por ID de inventario
    @PatchMapping("/{id}/ajustar-stock") //http://localhost:8081/api/inventario/1/ajustar-stock?tipo=+&cantidad=10
    public ResponseEntity<Inventario> ajustarStock(
            @PathVariable int id,
            @RequestParam String tipo,
            @RequestParam int cantidad) {

        Inventario inv = inventarioService.obtenerPorId(id);
        if (inv == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (cantidad <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (tipo.equalsIgnoreCase("INGRESO")) {
            inv.setStock(inv.getStock() + cantidad);
        } else if (tipo.equalsIgnoreCase("SALIDA")) {
            if (inv.getStock() < cantidad) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            inv.setStock(inv.getStock() - cantidad);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(inventarioService.guardar(inv), HttpStatus.OK);
    }

    // Buscar inventario con campos nulos
    @GetMapping("/nulos")
    public ResponseEntity<List<Inventario>> getInventarioConCamposNulos() {
        List<Inventario> lista = inventarioService.obtenerInventarioConCamposNulos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @DeleteMapping("/nulos")
    public ResponseEntity<List<Inventario>> eliminarInventarioConCamposNulos() {
        List<Inventario> eliminados = inventarioService.limpiarInventarioConCamposNulos();
        if (eliminados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(eliminados, HttpStatus.OK);
    }
}