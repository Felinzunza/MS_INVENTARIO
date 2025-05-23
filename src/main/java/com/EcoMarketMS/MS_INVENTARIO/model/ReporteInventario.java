package com.EcoMarketMS.MS_INVENTARIO.model;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteInventario {
    
    private List<Inventario> productos_Con_Menos_o_Mas_stock;
    private double valor_Total_Inventario_x_Tienda;
    private double valor_Promedio_x_Unidad_x_Tienda;

}





