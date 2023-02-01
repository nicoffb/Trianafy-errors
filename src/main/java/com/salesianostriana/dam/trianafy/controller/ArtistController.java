package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;



@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @Operation(summary = "Obtiene todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se han encontrado todos los artistas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Artist.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "Artistas no encontrados",
                    content = @Content) })
   @GetMapping("/artist")
   public List<Artist> obtenerTodos(){
       return artistService.findAll();
   }




    @Operation(summary = "Obtiene un artista a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado el artista",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Artist.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "Artistas no encontrados",
                    content = @Content) })
   @GetMapping("/artist/{id}")
    public Artist obtenerUno (@PathVariable Long id){
       return artistService.findById(id);
    }








    //POST
    @Operation(summary = "Crea un artista en la lista de artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se ha creado el artista",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Artist.class))
                    ) }),
            @ApiResponse(responseCode = "400", description = "Los datos introducidos son erróneos",
                    content = @Content) })
   @PostMapping("/artist")
    public ResponseEntity<Artist> nuevoArtista (@Valid @RequestBody Artist nuevo){
        //falta quitar esta comprobación mediante el valid
       if(nuevo.getName() == ""){
           return ResponseEntity.badRequest().build();
       }
       Artist saved = artistService.add(nuevo);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId()).toUri();

       return ResponseEntity.created(createdURI).body(saved); //la uri se verá en HEADERS, location
   }





   //PUT
    @Operation(summary = "Edita un artista a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha realizado la modificación",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Artist.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "Artista no encontrado",
                    content = @Content) })
   @PutMapping("/artist/{id}")
    public Artist editarArtista (@PathVariable Long id,@RequestBody Artist editar){
        return artistService.edit(id,editar);
   }





   //DELETE
    @Operation(summary = "Elimina a un artista a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artista eliminado",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Artist.class))
                    ) }), })
   @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> borrarArtista(@PathVariable Long id){

       artistService.deleteById(id);
       return ResponseEntity.noContent().build();
   }

}
