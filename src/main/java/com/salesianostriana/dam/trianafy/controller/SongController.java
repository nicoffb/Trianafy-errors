package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import com.salesianostriana.dam.trianafy.dto.OneSongRequestDTO;
import com.salesianostriana.dam.trianafy.dto.OneSongResponseDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final ArtistService artistService;

    @Operation(summary = "Obtiene todas las canciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se han encontrado todas las canciones",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Song.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "Canciones no encontradas",
                    content = @Content) })
    @GetMapping("/song")
    public ResponseEntity<List<OneSongResponseDTO>>obtenerTodas(){
        List<Song> result = songService.findAll();
        if (result.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result.stream().map(OneSongResponseDTO::of).collect(Collectors.toList()));
        }
    }

    @Operation(summary = "Obtiene una canci??n a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se han encontrado la canci??n seleccionada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Song.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "Canci??n no encontrada",
                    content = @Content) })
    @GetMapping("/song/{id}")
    public ResponseEntity<OneSongResponseDTO> obtenerUna (@PathVariable Long id){
        Song result = songService.findById(id).orElse(null);
        if (result == null){
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.ok(songService.findById(id).map(OneSongResponseDTO::of).get());
        //ResponseEntity.of(songService.findById(id).map(SongInfoResponseDTO::of));  se puede poner en una linea
    }

    @Operation(summary = "Crea una canci??n en la lista de canciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se han encontrado la canci??n seleccionada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Song.class))
                    ) }),
            @ApiResponse(responseCode = "400", description = "Los datos introducidos son err??neos ",
                    content = @Content) })
    @PostMapping("/song/")
    public ResponseEntity<OneSongResponseDTO> nuevaCancion (@RequestBody OneSongRequestDTO nuevo){
        if(nuevo.getTitle() != "" && artistService.findById2(nuevo.getArtistId()).isPresent()){
            Song saved = songService.add(songService.toSong(nuevo));
            return ResponseEntity.status(HttpStatus.CREATED).body(OneSongResponseDTO.of(saved));
        }
        return ResponseEntity.badRequest().build();
    }


    @Operation(summary = "Edita una canci??n a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha realizado la modificaci??n",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Song.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "Canci??n no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Los datos introducidos son err??neos",
            content = @Content) })
    @PutMapping("/song/{id}")
    public ResponseEntity<OneSongResponseDTO> editarCancion (@RequestBody OneSongResponseDTO editar, @PathVariable Long id){
        return songService.findById(id).map(s -> {
            s.setTitle((editar.getTitle()));
            return ResponseEntity.ok(OneSongResponseDTO.of(songService.add(s)));
            //EL METODO OF transforma una SONG en una SONG INFO RESPONSE
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    @Operation(summary = "Elimina a una canci??n a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Canci??n eliminada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Song.class))
                    ) }), })
    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> borrarCancion(@PathVariable Long id){
        songService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
