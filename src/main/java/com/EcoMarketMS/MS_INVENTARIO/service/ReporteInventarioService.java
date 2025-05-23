package com.EcoMarketMS.MS_INVENTARIO.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoMarketMS.MS_INVENTARIO.model.Inventario;
import com.EcoMarketMS.MS_INVENTARIO.model.ReporteInventario;
import com.EcoMarketMS.MS_INVENTARIO.model.Tienda;
import com.EcoMarketMS.MS_INVENTARIO.repo.InventarioRepository;
import com.EcoMarketMS.MS_INVENTARIO.repo.TiendaRepository;

@Service
public class ReporteInventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

 
    public List<ReporteInventario> generarReporteTodasLasTiendas(int cantidad, boolean ascendente) {
    // Obtener todas las tiendas
    List<Tienda> tiendas = tiendaRepository.findAll();

    // Lista final de reportes
    List<ReporteInventario> reportes = new ArrayList<>();

    // Recorrer cada tienda
    for (Tienda tienda : tiendas) {
        // Obtener inventario por tienda
        List<Inventario> inventarios = inventarioRepository.findByTiendaIdTienda(tienda.getIdTienda());

        // Si no hay inventario, saltar esta tienda
        if (inventarios.isEmpty()) continue;

        // Ordenar inventarios por stock
        inventarios.sort((a, b) -> {
            if (ascendente) return Integer.compare(a.getStock(), b.getStock());
            else return Integer.compare(b.getStock(), a.getStock());
        });

        // Filtrar los primeros N productos
        List<Inventario> productosFiltrados = new ArrayList<>();
        int limite = Math.min(cantidad, inventarios.size());

        for (int i = 0; i < limite; i++) {
            productosFiltrados.add(inventarios.get(i));
        }

        //Total y promedio
        double valorTotal = 0;
        int totalUnidades = 0;

        for (Inventario inv : inventarios) {
            double precio = inv.getProducto().getPrecio();
            int stock = inv.getStock();

            valorTotal += precio * stock;
            totalUnidades += stock;
        }

         double valorPromedioUnidad = (totalUnidades == 0) ? 0 : valorTotal / totalUnidades;


        // Crear el reporte y agregarlo a la lista
        ReporteInventario reporte = new ReporteInventario(
            
            productosFiltrados,  // ← lista de Inventario directamente
            valorTotal,
            valorPromedioUnidad
        );

        reportes.add(reporte);
    }

    return reportes;
}




/********************************************* */
public ReporteInventario generarReportePorTienda(int idTienda, int cantidad, boolean ascendente) {
    Tienda tienda = tiendaRepository.findById(idTienda);
    if (tienda == null) return null;

    List<Inventario> inventarios = inventarioRepository.findByTiendaIdTienda(idTienda);
    if (inventarios.isEmpty()) return null;

    // Ordenar por stock
    inventarios.sort((a, b) -> {
        if (ascendente) return Integer.compare(a.getStock(), b.getStock());
        else return Integer.compare(b.getStock(), a.getStock());
    });

    // Filtrar los top N inventarios
    List<Inventario> productosFiltrados = new ArrayList<>();
    int limite = Math.min(cantidad, inventarios.size());

    for (int i = 0; i < limite; i++) {
        productosFiltrados.add(inventarios.get(i));
    }

    //Total y promedio
    double valorTotal = 0;
    int totalUnidades = 0;

    for (Inventario inv : inventarios) {
        double precio = inv.getProducto().getPrecio();
        int stock = inv.getStock();

        valorTotal += precio * stock;
        totalUnidades += stock;
        }

        double valorPromedioUnidad = (totalUnidades == 0) ? 0 : valorTotal / totalUnidades;

    return new ReporteInventario(
        
        productosFiltrados,  
        valorTotal,
        valorPromedioUnidad
    );
}




}
