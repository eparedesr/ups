package com.example.ups.poo.service;

import com.example.ups.poo.dto.PersonDTO;
import com.example.ups.poo.entity.Person;
import com.example.ups.poo.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    private PersonDTO mapPersonToPersonDTO(Person person){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName() + " " + person.getLastname());
        personDTO.setAge(person.getAge());
        personDTO.setId(person.getPersonId());
        return personDTO;
    }

    private Person mapPersonDTOtoPerson(PersonDTO personDTO){
        String[] nameArray = personDTO.getName().split(" ");
        Person person = new Person();
        if (nameArray.length < 2) {
            return null;
        }

        person.setName(nameArray[0]);
        person.setLastname(nameArray[1]);
        person.setPersonId(personDTO.getId());
        person.setAge(personDTO.getAge());
        return person;
    }


    private List<PersonDTO> fetchAllPeopleRecords(){
        Iterable<Person> personIterable = personRepository.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();

        for(Person per: personIterable){
            PersonDTO personDTO = mapPersonToPersonDTO(per);
            personDTOList.add(personDTO);
        }
        return personDTOList;
    }

    public ResponseEntity getAllPeople(){
        List<PersonDTO> personDTOList = fetchAllPeopleRecords();

        if(personDTOList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PersonDTO list is Empty.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personDTOList);
    }

    //method that finds and returns person by id
    public ResponseEntity getPersonById(String id){
        Optional<Person> personOptional = personRepository.findByPersonId(id);
        if(personOptional.isPresent()){
            PersonDTO personDTO = mapPersonToPersonDTO(personOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(personDTO);
        } else{
            String message ="PersonDTO with id: " + id +" not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    public ResponseEntity createPerson(PersonDTO personDTO) {
        if (personRepository.findByPersonId(personDTO.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The ID is already registered.");
        }

        if (personDTO.getName() == null || personDTO.getName().split(" ").length != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format. Please provide exactly one name and lastname separated by a space.");
        }

        if (personDTO.getAge() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age is required.");
        }

        Person person = mapPersonDTOtoPerson(personDTO);
        personRepository.save(person);

        return ResponseEntity.status(HttpStatus.CREATED).body("Person successfully registered.");
    }

    public ResponseEntity updatePerson(PersonDTO personDTO) {
        Optional<Person> personOptional = personRepository.findByPersonId(personDTO.getId());
        if (personDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please, insert an ID.");
        }

        if (personOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with ID: " + personDTO.getId() + " was not found");
        }

        Person personToUpdate = personOptional.get();

        if (personDTO.getName() != null) {
            String[] nameArray = personDTO.getName().split(" ");
            if (nameArray.length != 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format. Please provide exactly one name and lastname separated by a space.");
            }
            personToUpdate.setName(nameArray[0]);
            personToUpdate.setLastname(nameArray[1]);
        }

        if (personDTO.getAge() > 0) {
            personToUpdate.setAge(personDTO.getAge());
        }
        personRepository.save(personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Person with ID: " + personDTO.getId() + " updated successfully.");
    }

    public ResponseEntity deletePersonById(String id){
        Optional<Person> personOptional = personRepository.findByPersonId(id);
        if(personOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person with ID: " + id + " was not found.");
        }

        personRepository.delete(personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully..!");
    }
}

