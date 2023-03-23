package com.example.contacts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.contacts.entities.Contact;
import com.example.contacts.repositories.ContactRepository;

@RestController
@RequestMapping({"/contacts"})
public class ContactController {
    @Autowired
    private ContactRepository repository;

    @GetMapping
    public List<Contact> list() {
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        return repository.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public void save(@RequestBody Contact contact) {
        repository.save(contact);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody Contact contact) {
       return repository.findById(id)
        .map(record -> {
            record.setName(contact.getName());
            record.setEmail(contact.getEmail());
            record.setPhone(contact.getPhone());
            Contact updated = repository.save(record);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path={"/{id}"})
    @ResponseBody
    public ResponseEntity <?> delete(@PathVariable long id) {
       return repository.findById(id)
        .map(record -> {
            repository.deleteById(id);
            return ResponseEntity.ok().body("Contato excluído com sucesso.");
        }).orElse(ResponseEntity.notFound().build());
    }

}
