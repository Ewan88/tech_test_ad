package com.answerdigital.colourstest.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.answerdigital.colourstest.dto.PersonUpdateDTO;
import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.repository.PeopleRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRespository;

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        // TODO STEP 1
        //
        // Implement a JSON endpoint that returns the full list
        // of people from the PeopleRepository. If there are zero
        // people returned from PeopleRepository then an empty
        // JSON array should be returned.

        return new ResponseEntity(peopleRespository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        // TODO: Step 2
        //
        // Implement a JSON endpoint that returns a single person
        // from the PeopleRepository based on the id parameter.
        // If null is returned from the PeopleRepository with
        // the supplied id then a NotFound should be returned.

        Optional<Person> person = peopleRespository.findById(id);

        if (person.isPresent()) {
            return new ResponseEntity(person, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody PersonUpdateDTO personUpdate) {
        // TODO STEP 3
        //
        // Implement an endpoint that receives a JSON object to
        // update a person using the PeopleRepository based on
        // the id parameter. Once the person has been successfully
        // updated, the person should be returned from the endpoint.
        // If null is returned from the PeopleRepository then a
        // NotFound should be returned.

        Optional<Person> personResponse = peopleRespository.findById(id);
        if (personResponse.isPresent()) {
            Person personObject = personResponse.get();
            personObject.setAuthorised(personUpdate.isAuthorised());
            personObject.setEnabled(personUpdate.isEnabled());
            personObject.setColours(personUpdate.getColours());
            peopleRespository.save(personObject);
            return new ResponseEntity(personObject, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    // TODO OPTIONAL
    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) throws URISyntaxException {

        if (person != null) {
            return new ResponseEntity(peopleRespository.save(person), HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }



    }

}
