package com.EcoMarketMS.MS_INVENTARIO.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    List<Tienda> tiendas = tiendaRepository.findAll();
    List<ReporteInventario> reportes = new ArrayList<>();

    for (Tienda tienda : tiendas) {
        List<Inventario> inventarios = inventarioRepository.findByTiendaIdTienda(tienda.getIdTienda());
        if (inventarios.isEmpty()) continue;

        Comparator<Inventario> comparator = Comparator.comparingInt(Inventario::getStock);
        if (!ascendente) comparator = comparator.reversed();

        List<Map<String, Object>> productosFiltrados = inventarios.stream()
            .sorted(comparator)
            .limit(cantidad)
            .map(inv -> {
                Map<String, Object> productoMap = new LinkedHashMap<>();
                productoMap.put("idProducto", inv.getProducto().getCodProducto());
                productoMap.put("nomProducto", inv.getProducto().getNomProducto());
                productoMap.put("precio", inv.getProducto().getPrecio());
                productoMap.put("cantidad", inv.getStock());
                return productoMap;
            })
            .toList();

        double valorTotal = inventarios.stream()
            .mapToDouble(inv -> inv.getProducto().getPrecio() * inv.getStock())
            .sum();

        double valorPromedio = valorTotal / inventarios.size();

        ReporteInventario reporte = new ReporteInventario(
            tienda,
            productosFiltrados,
            valorTotal,
            valorPromedio
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

    Comparator<Inventario> comparator = Comparator.comparingInt(Inventario::getStock);
    if (!ascendente) comparator = comparator.reversed();

    List<Map<String, Object>> productosFiltrados = inventarios.stream()
        .sorted(comparator)
        .limit(cantidad)
        .map(inv -> {
            Map<String, Object> productoMap = new LinkedHashMap<>();
            productoMap.put("idProducto", inv.getProducto().getCodProducto());
            productoMap.put("nomProducto", inv.getProducto().getNomProducto());
            productoMap.put("precio", inv.getProducto().getPrecio());
            productoMap.put("cantidad", inv.getStock()); // ← stock real
            return productoMap;
        })
        .toList();

    double valorTotal = inventarios.stream()
        .mapToDouble(inv -> inv.getProducto().getPrecio() * inv.getStock())
        .sum();

    double valorPromedio = valorTotal / inventarios.size();

    return new ReporteInventario(
        tienda,
        productosFiltrados,
        valorTotal,
        valorPromedio
    );
}



}
