package com.inventory.repository;

import com.inventory.model.StockSede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockSedeRepository extends JpaRepository<StockSede, Long> {

    List<StockSede> findBySedeCodigoSede(String codigoSede);

    List<StockSede> findByProductoId(String productoId);

    Optional<StockSede> findByProductoIdAndSedeCodigoSede(String productoId, String codigoSede);

    /** Devuelve todos los registros donde la cantidad está por debajo del stock mínimo. */
    @Query("SELECT s FROM StockSede s WHERE s.cantidad < s.stockMinimo")
    List<StockSede> findStockBajoMinimo();
}
