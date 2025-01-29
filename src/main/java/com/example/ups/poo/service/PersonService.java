package com.example.ups.poo.service;

import com.example.ups.poo.dto.Person;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    List<Person> personList = new ArrayList<>();

    public ResponseEntity getAllPeople(){
        if(personList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person list is Empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personList);
    }
    //method that finds and returns person by id
    public ResponseEntity getPersonById(String id){
        for(Person person: personList){
            if(id.equalsIgnoreCase(person.getId())){
                return ResponseEntity.status(HttpStatus.OK).body(person);
            }
        }
        String message ="Person with id: " + id +" not found.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    public ResponseEntity createPerson(Person person){
        for(Person registeredPerson: personList){
            if(registeredPerson.getId().equalsIgnoreCase(person.getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person already exists");
            }
        }

    if(person.getName() == null || person.getName().isEmpty() || person.getName().isBlank()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please, Insert a name");
    } else if(person.getLastname() == null || person.getLastname().isEmpty() || person.getLastname().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please, Insert a last name");
    } else if(person.getAge() <= 0){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please, Insert an age");
    } else if(person.getId() == null || person.getId().isEmpty() || person.getId().isBlank()){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please, Insert an id");
    }
        personList.add(person);
        return ResponseEntity.status(HttpStatus.OK).body("Person Successfully Registered");
    }
}

