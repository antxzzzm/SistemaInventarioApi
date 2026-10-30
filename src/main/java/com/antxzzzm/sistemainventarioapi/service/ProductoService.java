package com.antxzzzm.sistemainventarioapi.service;

import com.antxzzzm.sistemainventarioapi.dto.ProductoRequestDTO;
import com.antxzzzm.sistemainventarioapi.dto.ProductoResponseDTO;

import java.util.List;


public interface ProductoService {
    List<ProductoResponseDTO> listarProductos();
    ProductoResponseDTO buscarPorId(Long id);
    ProductoResponseDTO crearProducto(ProductoRequestDTO requestDTO);
    ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO requestDTO);
    void eliminarProducto(Long id);
}
