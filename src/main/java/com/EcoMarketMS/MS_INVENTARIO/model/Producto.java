package com.EcoMarketMS.MS_INVENTARIO.model;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod_Producto;

    @ManyToOne 
    @JsonIgnore
    @JoinColumn(name = "idCategoria", nullable = false) 
    private Categoria categoria; 

    @Column(length = 50,  nullable = false)
    private String nomProducto;
    
    @Column(nullable = false)
    private int precio;

    @Column(nullable = false)
    private int Stock;


}
