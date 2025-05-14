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

import com.EcoMarketMS.MS_INVENTARIO.model.Categoria;
import com.EcoMarketMS.MS_INVENTARIO.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaControlador {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping 
    public ResponseEntity<List<Categoria>> getCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        if (categorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Categoria> postCategoria(@RequestBody Categoria categoria) {
        Categoria buscado = categoriaService.findById(categoria.getIdCategoria());
        if (buscado == null) {
            return new ResponseEntity<>(categoriaService.save(categoria), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

        @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable int id) {
        Categoria categoria = categoriaService.findById(id);
        if (categoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoriaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





    
    
}
