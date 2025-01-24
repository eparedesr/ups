package com.example.ups.poo.service;

import com.example.ups.poo.dto.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
        public List<Person> getAllPeople(){
            List<Person> personList = new ArrayList<>();
            Person p1 = new Person("Erick", "Paredes", 20, "0955456512");
            Person p2 = new Person("Samuel", "Martinez", 19, "1735456512");
            personList.add(p1);
            personList.add(p2);
            return personList;
        }
}
