package com.antxzzzm.sistemainventarioapi.service;

import com.antxzzzm.sistemainventarioapi.dto.ProductoRequestDTO;
import com.antxzzzm.sistemainventarioapi.dto.ProductoResponseDTO;
import com.antxzzzm.sistemainventarioapi.entity.Producto;
import com.antxzzzm.sistemainventarioapi.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarProductos() {
        return productoRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return mapearAResponseDTO(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO crearProducto(ProductoRequestDTO requestDTO) {
        if (productoRepository.existsByCodigo(requestDTO.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con el código: " + requestDTO.getCodigo());
        }

        Producto producto = Producto.builder()
                .codigo(requestDTO.getCodigo())
                .nombre(requestDTO.getNombre())
                .descripcion(requestDTO.getDescripcion())
                .stock(requestDTO.getStock())
                .precio(requestDTO.getPrecio())
                .activo(true)
                .build();

        Producto productoGuardado = productoRepository.save(producto);
        return mapearAResponseDTO(productoGuardado);
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO requestDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        if (!producto.getCodigo().equals(requestDTO.getCodigo()) &&
                productoRepository.existsByCodigo(requestDTO.getCodigo())) {
            throw new RuntimeException("Ya existe otro producto con el código: " + requestDTO.getCodigo());
        }

        producto.setCodigo(requestDTO.getCodigo());
        producto.setNombre(requestDTO.getNombre());
        producto.setDescripcion(requestDTO.getDescripcion());
        producto.setStock(requestDTO.getStock());
        producto.setPrecio(requestDTO.getPrecio());

        Producto productoActualizado = productoRepository.save(producto);
        return mapearAResponseDTO(productoActualizado);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Eliminación lógica
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private ProductoResponseDTO mapearAResponseDTO(Producto producto) {
        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .stock(producto.getStock())
                .precio(producto.getPrecio())
                .activo(producto.getActivo())
                .build();
    }
}
