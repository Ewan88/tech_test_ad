package com.answerdigital.colourstest.controller;

import com.answerdigital.colourstest.model.Colour;
import com.answerdigital.colourstest.repository.ColoursRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/colours")
public class ColoursController {

    @Autowired
    private ColoursRepository coloursRepository;

    @GetMapping
    public ResponseEntity<List<Colour>> getColours() {
        return new ResponseEntity(coloursRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colour> getColour(@PathVariable("id") Long id) {
        Optional<Colour> colour = coloursRepository.findById(id);

        if (colour.isPresent()) {
            return new ResponseEntity(colour, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // TODO OPTIONAL
    @PostMapping("/")
    public ResponseEntity<Colour> addColour(@RequestBody Colour colour, UriComponentsBuilder componentsBuilder) {
        coloursRepository.save(colour);
        Optional<Colour> expected = coloursRepository.findById(colour.getId());
        if (expected.isPresent()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(componentsBuilder.path("/{id}").buildAndExpand(colour.getId()).toUri());
            return new ResponseEntity(colour, httpHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}
