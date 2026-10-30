package com.antxzzzm.sistemainventarioapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductoResponseDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private BigDecimal precio;
    private Boolean activo;
}
