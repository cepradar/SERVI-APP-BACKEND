package com.inventory.config;

import com.inventory.model.CategoriaElectrodomestico;
import com.inventory.model.CategoryClient;
import com.inventory.model.Cliente;
import com.inventory.model.ClienteElectrodomestico;
import com.inventory.model.DocumentoTipo;
import com.inventory.model.MarcaElectrodomestico;
import com.inventory.model.User;
import com.inventory.repository.CategoriaElectrodomesticoRepository;
import com.inventory.repository.CategoryClientRepository;
import com.inventory.repository.ClienteElectrodomesticoRepository;
import com.inventory.repository.ClienteRepository;
import com.inventory.repository.DocumentoTipoRepository;
import com.inventory.repository.MarcaElectrodomesticoRepository;
import com.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(20)
public class ClientePruebaInitializer implements CommandLineRunner {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ClienteElectrodomesticoRepository clienteElectrodomesticoRepository;
    @Autowired private CategoryClientRepository categoryClientRepository;
    @Autowired private DocumentoTipoRepository documentoTipoRepository;
    @Autowired private MarcaElectrodomesticoRepository marcaElectrodomesticoRepository;
    @Autowired private CategoriaElectrodomesticoRepository categoriaElectrodomesticoRepository;
    @Autowired private UserRepository userRepository;

    private static final String CLIENTE_ID = "1000000001";
    private static final String TIPO_DOCUMENTO = "CC";
    private static final String NUMERO_SERIE = "SN-PRUEBA-001";

    @Override
    public void run(String... args) throws Exception {
        // 1. Crear cliente de prueba si no existe
        boolean clienteExiste = clienteRepository.existsByIdAndTipoDocumentoId(CLIENTE_ID, TIPO_DOCUMENTO);
        if (!clienteExiste) {
            CategoryClient categoria = categoryClientRepository.findById("PART").orElse(null);
            DocumentoTipo tipoDoc = documentoTipoRepository.findById(TIPO_DOCUMENTO).orElse(null);

            if (categoria == null || tipoDoc == null) {
                System.out.println("⚠️  ClientePruebaInitializer: categoría o tipo de documento no encontrado, omitiendo.");
                return;
            }

            Cliente cliente = new Cliente(
                    CLIENTE_ID, null, categoria, tipoDoc,
                    "Juan", "Prueba",
                    "3000000000", "Calle Falsa 123", true);
            clienteRepository.save(cliente);
            System.out.println("✅ Cliente de prueba creado: " + CLIENTE_ID);
        }

        // 2. Crear electrodoméstico de prueba asociado al cliente si no existe
        boolean electroExiste = clienteElectrodomesticoRepository.findByNumeroSerie(NUMERO_SERIE).isPresent();
        if (!electroExiste) {
            Cliente cliente = clienteRepository.findByIdAndTipoDocumentoId(CLIENTE_ID, TIPO_DOCUMENTO).orElse(null);
            User admin = userRepository.findById("ADMIN").orElse(null);

            if (cliente == null || admin == null) {
                System.out.println("⚠️  ClientePruebaInitializer: cliente o usuario admin no encontrado, omitiendo electrodoméstico.");
                return;
            }

            MarcaElectrodomestico marca = marcaElectrodomesticoRepository.findByNombre("Samsung").orElse(null);
            CategoriaElectrodomestico catElectro = categoriaElectrodomesticoRepository.findByNombre("Televisor").orElse(null);

            ClienteElectrodomestico electrodomestico = new ClienteElectrodomestico(cliente, NUMERO_SERIE, admin);
            electrodomestico.setElectrodomesticoTipo("Televisor");
            electrodomestico.setElectrodomesticoModelo("Smart TV 55\"");
            electrodomestico.setColorOFinish("Negro");
            electrodomestico.setMarcaElectrodomestico(marca);
            if (catElectro != null) {
                // categoriaElectrodomestico se guarda a través del tipo string
                electrodomestico.setElectrodomesticoTipo(catElectro.getNombre());
            }

            clienteElectrodomesticoRepository.save(electrodomestico);
            System.out.println("✅ Electrodoméstico de prueba creado: " + NUMERO_SERIE);
        }
    }
}
