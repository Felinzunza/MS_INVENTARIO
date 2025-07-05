package com.EcoMarketMS.MS_INVENTARIO.model;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tienda")
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTienda;
    
    @Column(length = 50, nullable = false)
    private String direccionTienda;

    @Column(length = 15, nullable = false)
    private String telefonoTienda;

    @Column(length = 50, nullable = true)
    private String correoTienda;

    @Column(nullable = false)
    private LocalTime horaAperturaTienda;

    @Column(nullable = false)
    private LocalTime horaCierreTienda;

    @Column(nullable = false)
    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Inventario>inventario;

}
