package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.exception.ArtistNotFoundException;
import com.salesianostriana.dam.trianafy.exception.EmptyArtistListException;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository repository;


    public Artist add(Artist artist) {
        return repository.save(artist);
    }




    public Artist edit(Artist artist) {
        return repository.save(artist);
    }

    public void delete(Artist artist) {
        repository.delete(artist);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

//NUEVOS
    public List<Artist> findAll() {
        List<Artist> result = repository.findAll();

        if (result.isEmpty()) {
            throw new EmptyArtistListException();
        }

        return result;

    }

    public Artist findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException(id));
    }

    public Optional<Artist> findById2(Long id) {
        return repository.findById(id);
    }

}
