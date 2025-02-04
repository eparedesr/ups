package com.example.ups.poo.service;

import com.example.ups.poo.dto.Person;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        
        if(person.getName() == null || person.getLastname() == null || person.getAge() <= 0 || person.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please, Insert all data");
        }

        personList.add(person);
        return ResponseEntity.status(HttpStatus.OK).body("Person Successfully Registered");
    }

    public ResponseEntity updatePerson(Person person) {
        for (Person per : personList) {
            if (per.getId().equalsIgnoreCase(person.getId())) {
                if (person.getName() != null) {
                    per.setName(person.getName());
                }
                if (person.getLastname() != null) {
                    per.setLastname(person.getLastname());
                }
                if (person.getAge() > 0) {
                    per.setAge(person.getAge());
                }
                String message = ("Person with id: " + person.getId() + " was successfully updated.");
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }
        }
        String message2 = ("Person with id: " + person.getId() + " was not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message2);
    }

    public ResponseEntity deletePersonById(String id){
        if (id != null && id.length()<10){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Person with id: " + id + " doesn't have the required length (10 chars min.)");
        }
        for (Person person: personList){
            if(id.equalsIgnoreCase(person.getId())){
                personList.remove(person);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Person with id: " + id + " was successfully deleted.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Person with id: " + id + " was not found.");
    }
}

