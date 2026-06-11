package com.inventory.controller;

import com.inventory.dto.ProveedorDto;
import com.inventory.model.Proveedor;
import com.inventory.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping("/listar")
    public ResponseEntity<List<ProveedorDto>> listar() {
        List<ProveedorDto> lista = proveedorRepository.findAll()
                .stream().map(ProveedorDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ProveedorDto>> listarActivos() {
        List<ProveedorDto> lista = proveedorRepository.findByActivoTrue()
                .stream().map(ProveedorDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDto> obtener(@PathVariable Long id) {
        return proveedorRepository.findById(id)
                .map(p -> ResponseEntity.ok(new ProveedorDto(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<ProveedorDto> crear(@RequestBody ProveedorDto dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Proveedor proveedor = dtoAEntidad(dto);
        Proveedor guardado = proveedorRepository.save(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProveedorDto(guardado));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProveedorDto> actualizar(@PathVariable Long id, @RequestBody ProveedorDto dto) {
        return proveedorRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setNit(dto.getNit());
            existente.setContactoNombre(dto.getContactoNombre());
            existente.setTelefono(dto.getTelefono());
            existente.setEmail(dto.getEmail());
            existente.setDireccion(dto.getDireccion());
            existente.setCiudad(dto.getCiudad());
            existente.setDepartamento(dto.getDepartamento());
            existente.setPais(dto.getPais());
            existente.setSitioWeb(dto.getSitioWeb());
            existente.setCondicionesPago(dto.getCondicionesPago());
            existente.setObservaciones(dto.getObservaciones());
            existente.setActivo(dto.isActivo());
            return ResponseEntity.ok(new ProveedorDto(proveedorRepository.save(existente)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return proveedorRepository.findById(id).map(p -> {
            p.setActivo(false);
            proveedorRepository.save(p);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Proveedor dtoAEntidad(ProveedorDto dto) {
        Proveedor p = new Proveedor();
        p.setNombre(dto.getNombre());
        p.setNit(dto.getNit());
        p.setContactoNombre(dto.getContactoNombre());
        p.setTelefono(dto.getTelefono());
        p.setEmail(dto.getEmail());
        p.setDireccion(dto.getDireccion());
        p.setCiudad(dto.getCiudad());
        p.setDepartamento(dto.getDepartamento());
        if (dto.getPais() != null) p.setPais(dto.getPais());
        p.setSitioWeb(dto.getSitioWeb());
        p.setCondicionesPago(dto.getCondicionesPago());
        p.setObservaciones(dto.getObservaciones());
        return p;
    }
}
