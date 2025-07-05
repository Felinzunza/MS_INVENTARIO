package com.EcoMarketMS.MS_INVENTARIO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoMarketMS.MS_INVENTARIO.model.Inventario;
import com.EcoMarketMS.MS_INVENTARIO.model.Tienda;
import com.EcoMarketMS.MS_INVENTARIO.repo.TiendaRepository;

@Service
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<Tienda> listarTiendas() {
        return tiendaRepository.findAll();
    }


    public Tienda buscarTiendaxId(int idTienda) {
        return tiendaRepository.findById(idTienda);
    }
    public void eliminarTienda(int idTienda) {
        tiendaRepository.deleteById(idTienda);
    }

    public boolean existeTienda(int idTienda) {
        return tiendaRepository.existsById(idTienda);
    }

    public Tienda guardarTienda(Tienda tienda) {
        if (tienda.getInventario() != null) { // Podrías validar que los productos referenciados existen antes de asignarlos.
            for (Inventario i : tienda.getInventario()) {
                i.setTienda(tienda);
            }
        }
        return tiendaRepository.save(tienda); // Si tienda.getInventario() es muy grande, podrías tener problemas de rendimiento o ciclos.
    }
    

    
}
