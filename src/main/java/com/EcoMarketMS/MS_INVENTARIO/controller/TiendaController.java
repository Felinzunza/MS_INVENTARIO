package com.EcoMarketMS.MS_INVENTARIO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EcoMarketMS.MS_INVENTARIO.model.Tienda;
import com.EcoMarketMS.MS_INVENTARIO.service.TiendaService;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @GetMapping
    public ResponseEntity<List<Tienda>> getTiendas() {
        List<Tienda> tiendas = tiendaService.listarTiendas();
        if (tiendas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tiendas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tienda> getTiendaById(@PathVariable int id) {

        Tienda tienda = tiendaService.buscarTiendaxId(id);
        if (tienda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tienda, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Tienda> postTienda(@RequestBody Tienda tienda) {
        Tienda buscado = tiendaService.buscarTiendaxId(tienda.getIdTienda());
        if (buscado == null) {
            return new ResponseEntity<>(tiendaService.guardarTienda(tienda), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTienda(@PathVariable int id) {
        Tienda tienda = tiendaService.buscarTiendaxId(id);
        if (tienda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tiendaService.eliminarTienda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
