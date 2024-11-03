package co.edu.javeriana.as.personapp.controller;

import co.edu.javeriana.as.personapp.adapter.ProfesionInputAdapterRest;
import co.edu.javeriana.as.personapp.model.request.ProfesionRequest;
import co.edu.javeriana.as.personapp.model.response.ProfesionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/profesion")
public class ProfesionControllerV1 {
    @Autowired
    private ProfesionInputAdapterRest profesionInputAdapterRest;

    @Operation(summary = "Obtener historial de profesiones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesiones retornadas exitosamente"),
            @ApiResponse(responseCode = "404", description = "Profesiones no encontradas")
    })
    @ResponseBody
    @GetMapping(path = "/{database}", produces = "application/json")
    public List<ProfesionResponse> historial(
            @Parameter(description = "Base de datos donde se quiere buscar", required = true, example = "myDatabase")
            @PathVariable String database) {
        log.info("Into profesiones REST API");
        return profesionInputAdapterRest.historial(database.toUpperCase());
    }

    @Operation(summary = "Crear profesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesión creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @ResponseBody
    @PostMapping(path = "", produces = "application/json", consumes = "application/json")
    public ProfesionResponse createProfession(
            @Parameter(description = "Solicitud de creación de profesión", required = true)
            @RequestBody ProfesionRequest request) {
        log.info("esta en el metodo crearProfesion en el controller del api");
        return profesionInputAdapterRest.createProfession(request);
    }

    @Operation(summary = "Buscar profesión por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesión encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Profesión no encontrada")
    })
    @ResponseBody
    @GetMapping(path = "/{database}/{id}", produces = "application/json")
    public ProfesionResponse findProfession(
            @Parameter(description = "Base de datos donde se quiere buscar", required = true, example = "myDatabase")
            @PathVariable String database,
            @Parameter(description = "ID de la profesión", required = true, example = "123")
            @PathVariable String id){
        log.info("Into profesionById REST API");
        return profesionInputAdapterRest.findOneProfession(database, id);
    }

    @Operation(summary = "Eliminar profesión por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesión eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Profesión no encontrada")
    })
    @ResponseBody
    @DeleteMapping(path = "/{database}/{id}", produces = "application/json")
    public ProfesionResponse deleteProfession(
            @Parameter(description = "Base de datos donde se quiere buscar", required = true, example = "myDatabase")
            @PathVariable String database,
            @Parameter(description = "ID de la profesión", required = true, example = "123")
            @PathVariable String id) {
        log.info("Into deleteProfesionById REST API");
        return profesionInputAdapterRest.eliminarProfesion(database, id);
    }


    @Operation(summary = "Actualizar profesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesión actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Profesión no encontrada")
    })
    @ResponseBody
    @PutMapping(path = "", produces = "application/json", consumes = "application/json")
    public ProfesionResponse updateProfession(
            @Parameter(description = "Solicitud de actualización de profesión", required = true)
            @RequestBody ProfesionRequest request) {
        log.info("Into updateProfesionById REST API");
        return profesionInputAdapterRest.editarProfesion(request);
    }
}
