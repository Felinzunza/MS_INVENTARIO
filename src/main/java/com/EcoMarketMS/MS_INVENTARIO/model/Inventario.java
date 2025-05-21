package com.EcoMarketMS.MS_INVENTARIO.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario", uniqueConstraints = @UniqueConstraint(columnNames = {"idTienda", "cod_Producto"}))
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInventario;
    
    @ManyToOne 
    @JsonIgnoreProperties
    @JoinColumn(name = "idTienda", nullable = false) 
    private Tienda tienda; 

    @ManyToOne 
    @JsonIgnoreProperties
    @JoinColumn(name = "cod_Producto", nullable = false) 
    private Producto producto; 

    @Column(nullable = false)
    private int stock;
}
