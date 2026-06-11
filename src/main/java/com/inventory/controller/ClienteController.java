package com.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventory.dto.ClienteDto;
import com.inventory.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDto) {
        try {
            return ResponseEntity.ok(clienteService.crearCliente(clienteDto));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteDto>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{nit}")
    public ResponseEntity<List<ClienteDto>> buscarClientesPorNit(@PathVariable String nit) {
        return ResponseEntity.ok(clienteService.buscarClientesPorNit(nit));
    }

    @GetMapping("/{nit}/{tipoDocumentoId}")
    public ResponseEntity<ClienteDto> buscarCliente(@PathVariable String nit, @PathVariable String tipoDocumentoId) {
        Optional<ClienteDto> cliente = clienteService.buscarCliente(nit, tipoDocumentoId);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/actualizar/{nit}/{tipoDocumentoId}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable String nit, @PathVariable String tipoDocumentoId, @RequestBody ClienteDto clienteDto) {
        return ResponseEntity.ok(clienteService.actualizarCliente(nit, tipoDocumentoId, clienteDto));
    }

    @DeleteMapping("/eliminar/{nit}/{tipoDocumentoId}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String nit, @PathVariable String tipoDocumentoId) {
        clienteService.eliminarCliente(nit, tipoDocumentoId);
        return ResponseEntity.ok().build();
    }
}
