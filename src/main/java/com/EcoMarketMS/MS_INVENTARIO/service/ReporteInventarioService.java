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

   /*public List<ReporteInventario> generarTodosReportes(int cantidad, boolean masStock) {
        List<ReporteInventario> reporte = new ArrayList<>();
        List<Tienda> tiendas = tiendaRepository.findAll();

        for (Tienda tienda : tiendas) {
            // Obtener inventario de esta tienda
            List<Inventario> inventarios = inventarioRepository.findByTiendaIdTienda(tienda.getIdTienda());

            if (inventarios.isEmpty()) {
                continue;
            }

            // Ordenar por cantidad (stock)
            Comparator<Inventario> comparador;
            if (masStock) {
                comparador = Comparator.comparingInt(Inventario::getStock).reversed();
            } else {
                comparador = Comparator.comparingInt(Inventario::getStock);
            }

            inventarios.sort(comparador);

            // Obtener los productos top según cantidad solicitada
            List<Producto> topProductos = new ArrayList<>();
            for (int i = 0; i < cantidad && i < inventarios.size(); i++) {
                topProductos.add(inventarios.get(i).getProducto());
            }

            // Calcular el valor total del inventario (precio × cantidad)
            double valorTotal = 0;
            for (Inventario inv : inventarios) {
                valorTotal += inv.getProducto().getPrecio() * inv.getStock();
            }

            double valorPromedio = valorTotal / inventarios.size();

            // Agregar al reporte
            ReporteInventario reporteTienda = new ReporteInventario(tienda, topProductos, valorTotal, valorPromedio);
            reporte.add(reporteTienda);
        }

        return reporte;
    }


    // Generar reporte para una tienda específica
    public ReporteInventario generarReportePorTienda(int idTienda, int cantidad, boolean masStock) {
    Tienda tienda = tiendaRepository.findById(idTienda);
    if (tienda == null) {
        return null;
    }

    List<Inventario> inventarios = inventarioRepository.findByTiendaIdTienda(idTienda);

    if (inventarios.isEmpty()) {
        return null;
    }

    // Ordenar por cantidad
    Comparator<Inventario> comparador = masStock
        ? Comparator.comparingInt(Inventario::getStock).reversed()
        : Comparator.comparingInt(Inventario::getStock);

    inventarios.sort(comparador);

    List<Producto> topProductos = new ArrayList<>();
    for (int i = 0; i < cantidad && i < inventarios.size(); i++) {
        topProductos.add(inventarios.get(i).getProducto());
    }

    double valorTotal = 0;
    for (Inventario inv : inventarios) {
        valorTotal += inv.getProducto().getPrecio() * inv.getStock();
    }

    double valorPromedio = valorTotal / inventarios.size();

    return new ReporteInventario(tienda, topProductos, valorTotal, valorPromedio);
} */
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
                productoMap.put("idProducto", inv.getProducto().getCod_Producto());
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
            productoMap.put("idProducto", inv.getProducto().getCod_Producto());
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
