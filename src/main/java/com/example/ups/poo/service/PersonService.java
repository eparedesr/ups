package com.example.ups.poo.service;

import com.example.ups.poo.dto.PersonDTO;
import com.example.ups.poo.entity.Person;
import com.example.ups.poo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public ResponseEntity getAllPeople(){
        Iterable<Person> personIterable = personRepository.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();

        for(Person per: personIterable){
            PersonDTO personDTO = new PersonDTO();
            personDTO.setName(per.getName() + " " + per.getLastname());
            personDTO.setAge(per.getAge());
            personDTO.setId(per.getPersonId());
            personDTOList.add(personDTO);
        }

        if(personDTOList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PersonDTO list is Empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personDTOList);
    }

//    //method that finds and returns person by id
//    public ResponseEntity getPersonById(String id){
//        for(PersonDTO personDTO : personDTOList){
//            if(id.equalsIgnoreCase(personDTO.getId())){
//                return ResponseEntity.status(HttpStatus.OK).body(personDTO);
//            }
//        }
//        String message ="PersonDTO with id: " + id +" not found.";
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
//    }
//
//    public ResponseEntity createPerson(PersonDTO personDTO){
//        for(PersonDTO registeredPersonDTO : personDTOList){
//            if(registeredPersonDTO.getId().equalsIgnoreCase(personDTO.getId())){
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PersonDTO already exists");
//            }
//        }
//
//        if(personDTO.getName() == null || personDTO.getLastname() == null || personDTO.getAge() <= 0 || personDTO.getId() == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please, Insert all data");
//        }
//
//        personDTOList.add(personDTO);
//        return ResponseEntity.status(HttpStatus.OK).body("PersonDTO Successfully Registered");
//    }
//
//    public ResponseEntity updatePerson(PersonDTO personDTO) {
//        for (PersonDTO per : personDTOList) {
//            if (per.getId().equalsIgnoreCase(personDTO.getId())) {
//                if (personDTO.getName() != null) {
//                    per.setName(personDTO.getName());
//                }
//                if (personDTO.getLastname() != null) {
//                    per.setLastname(personDTO.getLastname());
//                }
//                if (personDTO.getAge() > 0) {
//                    per.setAge(personDTO.getAge());
//                }
//                String message = ("PersonDTO with id: " + personDTO.getId() + " was successfully updated.");
//                return ResponseEntity.status(HttpStatus.OK).body(message);
//            }
//        }
//        String message2 = ("PersonDTO with id: " + personDTO.getId() + " was not found.");
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message2);
//    }
//
//    public ResponseEntity deletePersonById(String id){
//        if (id != null && id.length()<10){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("PersonDTO with id: " + id + " doesn't have the required length (10 chars min.)");
//        }
//        for (PersonDTO personDTO : personDTOList){
//            if(id.equalsIgnoreCase(personDTO.getId())){
//                personDTOList.remove(personDTO);
//                return ResponseEntity.status(HttpStatus.OK)
//                        .body("PersonDTO with id: " + id + " was successfully deleted.");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("PersonDTO with id: " + id + " was not found.");
//    }
}

