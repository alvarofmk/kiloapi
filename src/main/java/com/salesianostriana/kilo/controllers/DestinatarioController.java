package com.salesianostriana.kilo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.DestinatarioResponseDTO;
import com.salesianostriana.kilo.dtos.destinatarios.CreateDestinatarioDTO;
import com.salesianostriana.kilo.entities.Destinatario;
import com.salesianostriana.kilo.services.DestinatarioService;
import com.salesianostriana.kilo.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/destinatario/")
@Tag(name = "Destinatario", description = "Este es el controlador para gestionar las cajas")
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    @Operation(summary = "Muestra todos los destinatarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hay destinatarios, aquí tienes los datos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DestinatarioResponseDTO.class)),
                            examples = @ExampleObject(value = """
                                    [
                                        {
                                                 "nombre": "Mi abuela",
                                                 "direccion": "La giralda basicamente",
                                                 "personaContacto": "Pues mi abuela",
                                                 "telefono": "686 567 397",
                                                 "kilosTotales": 0.0,
                                                 "numerosDeCajas": []
                                        },
                                        {
                                                 "nombre": "Blizzard",
                                                 "direccion": "Algún sitio de china",
                                                 "personaContacto": "Jeff Kaplan",
                                                 "telefono": "666 666 666",
                                                 "kilosTotales": 0.0,
                                                 "numerosDeCajas": [
                                                     7
                                                 ]
                                        },
                                    ]
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "No hay destinatarios",
                    content = @Content) })
    @JsonView(View.DestinatarioView.AllDestinatarioView.class)
    @GetMapping("/")
    public ResponseEntity<List<DestinatarioResponseDTO>> getAllDestinatarios(){

        List<DestinatarioResponseDTO> destinatarios = destinatarioService.getAllDestinatarios();

        if(destinatarios.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(destinatarios);
    }

    @Operation(summary = "Borra un destinatario por su id",
            description = "Si el destinatario a borrar ya tiene cajas asociadas, " +
                    "el método setea el destinatario a nulo en todas esas cajas antes de borrar definitivamente.")
    @ApiResponse(responseCode = "204", description = "Destinatario borrado con éxito",
            content = @Content)
    @Parameter(description = "El id del destinatario a borrar", name = "id", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDestinatario(@PathVariable Long id){
        destinatarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Muestra un destinatario y un resumen de las cajas asignadas a él")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado al destinatario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DestinatarioResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "nombre": "Blizzard",
                                        "direccion": "Algún sitio de china",
                                        "personaContacto": "Jeff Kaplan",
                                        "telefono": "666 666 666",
                                        "numeroCajas": 1,
                                        "kilosTotales": 6.0
                                    }
                                    """))}),
            @ApiResponse(responseCode = "404", description = "No se encuentra al destinatario",
                    content = @Content)
    })
    @Parameter(description = "El id del destinatario a buscar", name = "id", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<DestinatarioResponseDTO> getDestinatarioSummary(@PathVariable Long id) {
        return ResponseEntity.of(destinatarioService.getSummary(id));
    }

    @Operation(summary = "Muestra un destinatario y el detalle de las cajas asignadas a él")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado al destinatario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DestinatarioResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "nombre": "Blizzard",
                                        "direccion": "Algún sitio de china",
                                        "personaContacto": "Jeff Kaplan",
                                        "telefono": "666 666 666",
                                        "cajas": [
                                            {
                                                "numCaja": 7,
                                                "kilosTotales": 1.0,
                                                "contenido": [
                                                    {
                                                        "nombre": "Chocolate",
                                                        "kg": 1.0
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                    """))}),
            @ApiResponse(responseCode = "404", description = "No se encuentra al destinatario",
                    content = @Content)
    })
    @Parameter(description = "El id del destinatario a buscar", name = "id", required = true)
    @JsonView(View.DestinatarioView.DetailedDestinatarioView.class)
    @GetMapping("/{id}/detalle")
    public ResponseEntity<DestinatarioResponseDTO> getDestinatarioDetail(@PathVariable Long id){
        return ResponseEntity.of(destinatarioService.findById(id).map(DestinatarioResponseDTO::of));
    }

    @Operation(summary = "Crea un nuevo destinatario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Destinatario creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateDestinatarioDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "nombre": "Hijas de la caridad",
                                        "direccion": "Calle Sin nombre Nº7",
                                        "personaContacto": "Sor María",
                                        "telefono": "689624528",
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400", description = "Los datos proporcionados no son correctos",
                    content = @Content) })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Datos del nuevo destinatario",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Destinatario.class),
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "nombre": "Hijas de la caridad",
                                "direccion": "Calle Sin nombre Nº7",
                                "personaContacto": "Sor María",
                                "telefono": "689624528",
                                "cajas": []
                            }
                            """)
            )}
    )
    @PostMapping("/")
    public ResponseEntity<Destinatario> createDestinatario(@RequestBody CreateDestinatarioDTO newDest){
        if(newDest.getNombre()!= null && newDest.getDireccion()!= null)
            return ResponseEntity.status(HttpStatus.CREATED).body(destinatarioService.createDestinatario(newDest));
        else
            return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Edita un destinatario especificado por el id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destinatario editado con éxito",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DestinatarioResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": 10,
                                        "nombre": "Nietas de la Caridad",
                                        "direccion": "Calle Con Nombre Nº7",
                                        "personaContacto": "Sor María II",
                                        "telefono": "689547563",
                                    }
                                    """)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Los datos proporcionados no son correctos",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Datos del destinatario actualizados",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreateDestinatarioDTO.class),
                    examples = @ExampleObject( value = """
                                {
                                    "nombre": "Nietas de la Caridad",
                                    "direccion": "Calle Con Nombre Nº7",
                                    "personaContacto": "Sor María II",
                                    "telefono": "689547563",
                                }
                            """)
            )}
    )
    @Parameter(description = "Id del destinatario a modificar", name = "id", required = true)
    @PutMapping("/{id}")
    @JsonView(View.DestinatarioView.JustDestinatarioView.class)
    public ResponseEntity<DestinatarioResponseDTO> editDestinatario(@PathVariable Long id, @RequestBody CreateDestinatarioDTO editDest){
        Optional<Destinatario> destinatarioEditado = destinatarioService.editDestinatario(id, editDest);

        return destinatarioEditado.isPresent()? ResponseEntity.ok(destinatarioEditado.map(DestinatarioResponseDTO::of).get()) : ResponseEntity.badRequest().build();
    }

}
