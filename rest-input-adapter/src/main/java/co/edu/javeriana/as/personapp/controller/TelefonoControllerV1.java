package co.edu.javeriana.as.personapp.controller;

import co.edu.javeriana.as.personapp.adapter.TelefonoInputAdapterRest;
import co.edu.javeriana.as.personapp.model.request.TelefonoRequest;
import co.edu.javeriana.as.personapp.model.response.TelefonoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/telefono")
public class TelefonoControllerV1 {
    @Autowired
    private TelefonoInputAdapterRest telefonoInputAdapterRest;

    @Operation(summary = "Obtener historial de teléfonos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teléfonos retornados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Teléfonos no encontrados")
    })
    @ResponseBody
    @GetMapping(path="/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TelefonoResponse> historial(
            @Parameter(description = "Base de datos donde se quiere buscar", required = true, example = "myDatabase")
            @PathVariable String database){
        log.info("Into telefonos REST API");
        return telefonoInputAdapterRest.historial(database.toUpperCase());
    }

    @ResponseBody
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TelefonoResponse crearTelefono(@RequestBody TelefonoRequest request) {
        log.info("esta en el metodo crearTelefono en el controller del api");
        return telefonoInputAdapterRest.createPhone(request);
    }

    @Operation(summary = "Buscar teléfono por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teléfono encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Teléfono no encontrado")
    })
    @ResponseBody
    @GetMapping(path = "/{database}/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TelefonoResponse findPhoneById(
            @Parameter(description = "Base de datos donde se quiere buscar", required = true, example = "myDatabase")
            @PathVariable String database,
            @Parameter(description = "Número de teléfono", required = true, example = "123456789")
            @PathVariable String num){
        log.info("Into telefonoById REST API");
        return telefonoInputAdapterRest.findOne(database, num);
    }

    @Operation(summary = "Eliminar teléfono por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teléfono eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Teléfono no encontrado")
    })
    @ResponseBody
    @DeleteMapping(path = "/{database}/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TelefonoResponse deletePhone(
            @Parameter(description = "Base de datos donde se quiere buscar", required = true, example = "myDatabase")
            @PathVariable String database,
            @Parameter(description = "Número de teléfono", required = true, example = "123456789")
            @PathVariable String num) {
        log.info("Into deleteTelefonoById REST API");
        return telefonoInputAdapterRest.deletePhone(database, num);
    }

    @Operation(summary = "Actualizar teléfono")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teléfono actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @ResponseBody
    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TelefonoResponse updatePhone(
            @Parameter(description = "Solicitud de actualización de teléfono", required = true)
            @RequestBody TelefonoRequest request) {
        log.info("Into updateTelefonoById REST API");
        return telefonoInputAdapterRest.editPhone(request);
    }
}
