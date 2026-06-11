package com.inventory.service;

import com.inventory.dto.AuditoriaDto;
import com.inventory.model.Auditoria;
import com.inventory.model.Evento;
import com.inventory.model.User;
import com.inventory.repository.AuditoriaRepository;
import com.inventory.repository.EventoRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.UserRepository;
import com.inventory.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuditoriaService {
    private static final Logger logger = LoggerFactory.getLogger(AuditoriaService.class);

    @Autowired private AuditoriaRepository auditoriaRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private VentaRepository ventaRepository;
    @Autowired private EventoRepository eventoRepository;

    public AuditoriaDto registrarMovimiento(String productId,
                                            Integer cantidadInicial, Integer cantidadFinal,
                                            BigDecimal precioInicial, BigDecimal precioFinal,
                                            String tipo, String descripcion,
                                            String usuarioUsername, String ventaId) {
        String eventoId = mapearTipoEvento(tipo);
        Evento evento = eventoRepository.findById(Objects.requireNonNull(eventoId))
                .orElseThrow(() -> new RuntimeException("Evento no encontrado: " + eventoId));
        User usuario = userRepository.findByUsernameIgnoreCase(
                Objects.requireNonNull(usuarioUsername, "usuarioUsername"))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Auditoria auditoria = new Auditoria(evento, descripcion, usuario,
                buildEstado(cantidadInicial, precioInicial),
                buildEstado(cantidadFinal, precioFinal));

        if (productId != null && !productId.startsWith("CE-") && !productId.startsWith("OS-"))
            productRepository.findById(productId).ifPresent(auditoria::setProducto);

        if (ventaId != null && !ventaId.isBlank())
            ventaRepository.findById(ventaId).ifPresent(auditoria::setVenta);

        Auditoria guardada = auditoriaRepository.save(auditoria);
        logger.info("Auditoria registrada ID: {}, evento: {}", guardada.getId(), guardada.getEvento().getId());
        return new AuditoriaDto(guardada);
    }

    private String buildEstado(Integer cantidad, BigDecimal precio) {
        if (cantidad == null && precio == null) return null;
        StringBuilder sb = new StringBuilder();
        if (cantidad != null) sb.append("cantidad=").append(cantidad);
        if (precio != null) { if (sb.length() > 0) sb.append(", "); sb.append("precio=").append(precio); }
        return sb.toString();
    }

    private String mapearTipoEvento(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return "MA";
        switch (tipo.toUpperCase().trim()) {
            case "INGRESO": return "ME";
            case "SALIDA":  return "MS";
            case "AJUSTE":  return "MA";
            case "VENTA":   return "V";
            default:        return tipo.toUpperCase().trim();
        }
    }

    public List<AuditoriaDto> obtenerTodosMovimientos() {
        return auditoriaRepository.findAllByOrderByFechaDesc().stream().map(AuditoriaDto::new).collect(Collectors.toList());
    }

    public List<AuditoriaDto> obtenerMovimientosProducto(String productId) {
        return auditoriaRepository.findByProductoId(productId).stream().map(AuditoriaDto::new).collect(Collectors.toList());
    }

    public List<AuditoriaDto> obtenerMovimientosUsuario(String username) {
        return auditoriaRepository.findByUsuarioUsernameIgnoreCase(username).stream().map(AuditoriaDto::new).collect(Collectors.toList());
    }

    public List<AuditoriaDto> obtenerMovimientosPorTipo(String eventoId) {
        return auditoriaRepository.findByEventoId(eventoId).stream().map(AuditoriaDto::new).collect(Collectors.toList());
    }

    public List<AuditoriaDto> obtenerMovimientosEnRango(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return auditoriaRepository.findByFechaBetween(fechaInicio, fechaFin).stream().map(AuditoriaDto::new).collect(Collectors.toList());
    }

    public List<AuditoriaDto> obtenerMovimientosEnCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) return List.of();
        String cat = categoria.toUpperCase();
        if ("ORDEN".equals(cat) || "ORDENES".equals(cat) || "SERVICIO".equals(cat) || "SERVICIOS".equals(cat))
            return obtenerMovimientosOrdenes();
        return auditoriaRepository.findByEventoCategoria(cat).stream().map(AuditoriaDto::new).collect(Collectors.toList());
    }

    public List<AuditoriaDto> obtenerMovimientosOrdenes() {
        return auditoriaRepository.findMovimientosOrdenes().stream().map(AuditoriaDto::new).collect(Collectors.toList());
    }

    public AuditoriaDto obtenerMovimientoPorId(Long auditoriaId) {
        return auditoriaRepository.findById(Objects.requireNonNull(auditoriaId))
                .map(AuditoriaDto::new)
                .orElseThrow(() -> new RuntimeException("Auditoria no encontrada"));
    }
}