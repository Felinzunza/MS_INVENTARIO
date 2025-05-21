package com.EcoMarketMS.MS_INVENTARIO.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteInventario {
    private Tienda tienda;
    private List<Map<String, Object>> productosConMasOMenosStock;
    private double valorTotalInventarioxTienda;
    private double valorPromedioInventarioxTienda;

}





