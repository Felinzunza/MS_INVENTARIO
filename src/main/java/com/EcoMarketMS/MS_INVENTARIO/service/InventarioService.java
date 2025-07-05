package com.EcoMarketMS.MS_INVENTARIO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoMarketMS.MS_INVENTARIO.model.Inventario;
import com.EcoMarketMS.MS_INVENTARIO.model.TipoMov;
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

   public Inventario ajustarStockPorTiendaYProducto(int idTienda, int codProducto, TipoMov tipo, int cantidad) {
    Inventario inv = inventarioRepository.findByTiendaIdTiendaAndProductoCodProducto(idTienda, codProducto);

    if (inv == null || cantidad <= 0) {
        return null;
    }

    switch (tipo) {
        case INGRESO -> inv.setStock(inv.getStock() + cantidad);
        case SALIDA -> {
            if (inv.getStock() < cantidad) {
                return null;
            }
            inv.setStock(inv.getStock() - cantidad);
        }
        case AJUSTE -> inv.setStock(cantidad); //  override total
    }

    return guardar(inv);
}
    public Inventario ajustarStockXId(int id, TipoMov tipo, int cantidad) {
        Inventario inv = obtenerPorId(id);
        if (inv == null || cantidad <= 0) {
            return null;
        }

        switch (tipo) {
            case INGRESO -> inv.setStock(inv.getStock() + cantidad);
            case SALIDA -> {
                if (inv.getStock() < cantidad) {
                    return null;
                }
                inv.setStock(inv.getStock() - cantidad);
            }
            case AJUSTE -> inv.setStock(cantidad); //  override total
        }

        return guardar(inv);
    }

}


   
