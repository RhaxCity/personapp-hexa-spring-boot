package co.edu.javeriana.as.personapp.controller;

import co.edu.javeriana.as.personapp.adapter.EstudioInputAdapterRest;
import co.edu.javeriana.as.personapp.model.request.EstudioRequest;
import co.edu.javeriana.as.personapp.model.response.EstudioResponse;
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
@RequestMapping("/api/v1/estudio")
public class EstudioControllerV1 {
    @Autowired
    private EstudioInputAdapterRest estudioInputAdapterRest;

    @Operation(summary = "Obtener historial de estudios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudios retornados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudios no encontrados")
    })
    @ResponseBody
    @GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EstudioResponse> historial(
            @Parameter(description = "Base de datos donde se quiere buscar", required = true, example = "1")
            @PathVariable String database) {
        log.info("Into estudios REST API");
        return estudioInputAdapterRest.historial(database.toUpperCase());
    }

    @Operation(summary = "Crear estudio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudio creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @ResponseBody
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EstudioResponse crearEstudio(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Solicitud de creación de estudio", required = true)
            @RequestBody EstudioRequest request) {
        log.info("esta en el metodo crearEstudio en el controller del api");
        return estudioInputAdapterRest.createStudy(request);
    }
    
}

