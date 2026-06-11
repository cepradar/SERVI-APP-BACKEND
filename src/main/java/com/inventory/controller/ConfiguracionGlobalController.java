package com.inventory.controller;

import com.inventory.model.ConfiguracionGlobal;
import com.inventory.repository.ConfiguracionGlobalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/configuracion-global")
public class ConfiguracionGlobalController {

    @Autowired
    private ConfiguracionGlobalRepository configuracionGlobalRepository;

    @GetMapping("/listar")
    public ResponseEntity<List<ConfiguracionGlobal>> listar() {
        return ResponseEntity.ok(configuracionGlobalRepository.findAll());
    }

    @GetMapping("/{clave}")
    public ResponseEntity<ConfiguracionGlobal> obtener(@PathVariable String clave) {
        return configuracionGlobalRepository.findById(clave)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{clave}")
    public ResponseEntity<ConfiguracionGlobal> actualizar(
            @PathVariable String clave,
            @RequestBody Map<String, String> body,
            Authentication auth) {

        String nuevoValor = body.get("valor");
        if (nuevoValor == null) {
            return ResponseEntity.badRequest().build();
        }

        ConfiguracionGlobal config = configuracionGlobalRepository.findById(clave)
                .orElse(new ConfiguracionGlobal(clave, nuevoValor, body.getOrDefault("descripcion", null)));

        config.setValor(nuevoValor);
        if (body.containsKey("descripcion")) config.setDescripcion(body.get("descripcion"));
        if (auth != null) config.setModificadoPor(auth.getName());

        return ResponseEntity.ok(configuracionGlobalRepository.save(config));
    }

    @PostMapping("/crear")
    public ResponseEntity<ConfiguracionGlobal> crear(@RequestBody ConfiguracionGlobal dto, Authentication auth) {
        if (dto.getClave() == null || dto.getClave().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (auth != null) dto.setModificadoPor(auth.getName());
        return ResponseEntity.ok(configuracionGlobalRepository.save(dto));
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> eliminar(@PathVariable String clave) {
        if (!configuracionGlobalRepository.existsById(clave)) {
            return ResponseEntity.notFound().build();
        }
        configuracionGlobalRepository.deleteById(clave);
        return ResponseEntity.ok().build();
    }
}
