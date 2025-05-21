package com.EcoMarketMS.MS_INVENTARIO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.EcoMarketMS.MS_INVENTARIO.model.ReporteInventario;
import com.EcoMarketMS.MS_INVENTARIO.service.ReporteInventarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/reporte")
public class ReporteControlador {

    @Autowired
    private ReporteInventarioService reporteService;

    // Ejemplo: http://localhost:8081/api/reporte?cantidad=3&menosStock=true
    @GetMapping
    public ResponseEntity<List<ReporteInventario>> generarReporte(
            @RequestParam(defaultValue = "3") int cantidad,
            @RequestParam(defaultValue = "true") boolean menosStock) {

        List<ReporteInventario> reporte = reporteService.generarReporteTodasLasTiendas(cantidad, menosStock);
        if (reporte.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }


    // Ejemplo: http://localhost:8081/api/reporte/tienda/1?cantidad=3&menosStock=true
    @GetMapping("/tienda/{idTienda}")
    public ResponseEntity<ReporteInventario> getReportePorTienda(
            @PathVariable int idTienda,
            @RequestParam(defaultValue = "3") int cantidad,
            @RequestParam(defaultValue = "true") boolean menosStock) {

        ReporteInventario reporte = reporteService.generarReportePorTienda(idTienda, cantidad, menosStock);
        if (reporte == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }

}
