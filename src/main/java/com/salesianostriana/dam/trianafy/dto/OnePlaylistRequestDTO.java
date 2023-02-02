package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;


@Builder
@Value
public class OnePlaylistRequestDTO {

    @NotNull
    private String name;
    private String description;

    public Playlist toPlaylist(){
        return Playlist.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }

}
