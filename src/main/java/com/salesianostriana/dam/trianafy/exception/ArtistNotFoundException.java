package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class ArtistNotFoundException extends EntityNotFoundException {

    public ArtistNotFoundException() {
        super("El artista no se ha encontrado");
    }

    public ArtistNotFoundException(Long id) {
        super(String.format("El artista con el id %d no se ha encontrado", id));
    }

}
