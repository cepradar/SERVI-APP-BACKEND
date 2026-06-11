package com.inventory.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.dto.ClienteDto;
import com.inventory.model.CategoryClient;
import com.inventory.model.Cliente;
import com.inventory.model.DocumentoTipo;
import com.inventory.repository.CategoryClientRepository;
import com.inventory.repository.ClienteRepository;
import com.inventory.repository.DocumentoTipoRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CategoryClientRepository categoryClientRepository;

    @Autowired
    private DocumentoTipoRepository documentoTipoRepository;

    public ClienteDto crearCliente(ClienteDto clienteDto){
        String nit = clienteDto.getNit() != null ? clienteDto.getNit().trim() : null;
        String tipoDocumentoId = clienteDto.getTipoDocumentoId() != null ? clienteDto.getTipoDocumentoId().trim() : null;
        if (nit == null || nit.isEmpty() || tipoDocumentoId == null || tipoDocumentoId.isEmpty()) {
            throw new IllegalArgumentException("Nit y tipo de documento son obligatorios");
        }
        if (clienteRepository.existsByNitAndTipoDocumentoId(nit, tipoDocumentoId)) {
            throw new IllegalStateException("Cliente ya existe con el mismo nit y tipo de documento");
        }

        Cliente cliente = convertirDtoAEntidad(clienteDto);
        // id es auto-generado, no se asigna manualmente
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return new ClienteDto(clienteGuardado);
    }

    public List<ClienteDto> listarClientes(){
        return clienteRepository.findAll().stream()
                .map(ClienteDto::new)
                .collect(Collectors.toList());
    }

    public List<ClienteDto> buscarClientesPorNit(String nit){
        if (nit == null || nit.trim().isEmpty()) {
            return List.of();
        }
        return clienteRepository.findByNit(nit).stream()
                .map(ClienteDto::new)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDto> buscarCliente(String nit, String tipoDocumentoId){
        if (nit == null || nit.trim().isEmpty() || tipoDocumentoId == null || tipoDocumentoId.trim().isEmpty()) {
            return Optional.empty();
        }
        Optional<Cliente> cli = clienteRepository.findByNitAndTipoDocumentoId(nit, tipoDocumentoId);
        return cli.map(ClienteDto::new);
    }

    public ClienteDto actualizarCliente(String nit, String tipoDocumentoId, ClienteDto clienteDto){
        if (nit == null || nit.trim().isEmpty() || tipoDocumentoId == null || tipoDocumentoId.trim().isEmpty()) {
            throw new RuntimeException("Nit y tipo de documento son obligatorios");
        }
        Cliente clienteExistente = clienteRepository.findByNitAndTipoDocumentoId(nit, tipoDocumentoId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        // Actualizar campos
        clienteExistente.setNombre(clienteDto.getNombre());
        clienteExistente.setApellido(clienteDto.getApellido());
        clienteExistente.setTelefono(clienteDto.getTelefono());
        clienteExistente.setDireccion(clienteDto.getDireccion());
        clienteExistente.setActivo(clienteDto.getActivo());
        if (clienteDto.getEmail() != null) clienteExistente.setEmail(clienteDto.getEmail());
        
        // Actualizar categoría si cambió
        if (clienteDto.getCategoryId() != null) {
            String categoryId = Objects.requireNonNull(clienteDto.getCategoryId(), "categoryId");
            CategoryClient category = categoryClientRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            clienteExistente.setCategory(category);
        }

        // Actualizar tipo documento si cambió
        if (clienteDto.getTipoDocumentoId() != null) {
            String tipoDocId = Objects.requireNonNull(clienteDto.getTipoDocumentoId(), "tipoDocumentoId");
            DocumentoTipo tipoDoc = documentoTipoRepository.findById(tipoDocId)
                    .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
            clienteExistente.setTipoDocumento(tipoDoc);
        }
        
        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        return new ClienteDto(clienteActualizado);
    }

    public void eliminarCliente(String nit, String tipoDocumentoId){
        if (nit == null || nit.trim().isEmpty() || tipoDocumentoId == null || tipoDocumentoId.trim().isEmpty()) {
            throw new RuntimeException("Nit y tipo de documento son obligatorios");
        }
        Cliente clienteExistente = clienteRepository.findByNitAndTipoDocumentoId(nit, tipoDocumentoId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.delete(clienteExistente);
    }

    private Cliente convertirDtoAEntidad(ClienteDto clienteDto){
        Cliente cliente = new Cliente();
        cliente.setNit(clienteDto.getNit());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido(clienteDto.getApellido());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setDireccion(clienteDto.getDireccion());
        if (clienteDto.getEmail() != null) cliente.setEmail(clienteDto.getEmail());
        cliente.setActivo(true);
        
        // Buscar y asignar la categoría desde el formulario o la primera disponible
        CategoryClient category;
        if (clienteDto.getCategoryId() != null && !clienteDto.getCategoryId().trim().isEmpty()) {
            String categoryId = Objects.requireNonNull(clienteDto.getCategoryId(), "categoryId");
            category = categoryClientRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Categoría de cliente no encontrada"));
        } else {
            category = categoryClientRepository.findAll().stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No hay categorías de cliente disponibles"));
        }
        cliente.setCategory(category);
        
        // Buscar y asignar tipo documento por defecto "CC" o el primero disponible
        DocumentoTipo tipoDoc;
        if (clienteDto.getTipoDocumentoId() != null && !clienteDto.getTipoDocumentoId().isEmpty()) {
            String tipoDocId = Objects.requireNonNull(clienteDto.getTipoDocumentoId(), "tipoDocumentoId");
            tipoDoc = documentoTipoRepository.findById(tipoDocId)
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
        } else {
            tipoDoc = documentoTipoRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay tipos de documento disponibles"));
        }
        cliente.setTipoDocumento(tipoDoc);
        
        return cliente;
    }
}
