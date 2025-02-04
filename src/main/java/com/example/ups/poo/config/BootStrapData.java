package com.example.ups.poo.config;

import com.example.ups.poo.entity.Person;
import com.example.ups.poo.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

     private final PersonRepository personRepository;

     public BootStrapData(PersonRepository personRepository){
         this.personRepository=personRepository;
     }

    @Override
    public void run(String... args) throws Exception {
        Person p1 = new Person();
        p1.setName("Erick");
        p1.setLastname("Paredes");
        p1.setAge(20);
        p1.setPersonId("0955456512");

        Person p2 = new Person();
        p2.setName("Karina");
        p2.setLastname("Rivera");
        p2.setAge(30);
        p2.setPersonId("0912456511");

        personRepository.save(p1);
        personRepository.save(p2);

    }
}
