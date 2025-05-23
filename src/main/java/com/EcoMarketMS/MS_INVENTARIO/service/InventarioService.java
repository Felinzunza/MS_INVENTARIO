package com.EcoMarketMS.MS_INVENTARIO.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoMarketMS.MS_INVENTARIO.model.Inventario;
import com.EcoMarketMS.MS_INVENTARIO.repo.InventarioRepository;


@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    
    public Inventario guardar (Inventario inventario) {
        return inventarioRepository.save(inventario);
    }
    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    } 

    public List<Inventario> obtenerPorTienda(int idTienda) {
        return inventarioRepository.findByTiendaIdTienda(idTienda);
    }
    public List<Inventario> obtenerPorProducto(int codProducto) { 
        return inventarioRepository.findByProductoCodProducto(codProducto);
    }

    public Inventario obtenerPorId(int id) {
        return inventarioRepository.findById(id);
    }
    public void eliminar(int id) {
        inventarioRepository.deleteById(id);
    }

    public List<Inventario> obtenerInventarioConCamposNulos() {
    List<Inventario> todos = inventarioRepository.findAll();

    List<Inventario> incompletos = new ArrayList<>();
    for (Inventario i : todos) {
        if (i.getProducto() == null || i.getTienda() == null) {
            incompletos.add(i);
        }
    }

    return incompletos;
    }


    public List<Inventario> limpiarInventarioConCamposNulos() {
    List<Inventario> todos = inventarioRepository.findAll();

    List<Inventario> eliminados = new ArrayList<>();
    for (Inventario i : todos) {
        if (i.getProducto() == null || i.getTienda() == null) {
            eliminados.add(i);
            inventarioRepository.deleteById(i.getIdInventario());
        }
    }

    return eliminados;
    }
}