package com.EcoMarketMS.MS_INVENTARIO.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    private int cod_Producto;
    private String categoria; //es solo de prueba. Debe ser clase Categoria, no String
    private String nomProducto;
    private int precio;
    private int Stock;
    

}
