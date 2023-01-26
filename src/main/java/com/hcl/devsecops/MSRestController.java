package com.hcl.devsecops;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.devsecops.ContainerEvent;



@RestController
public class MSRestController {

    private final ContainerEventRepository eventRepository;


   MSRestController(ContainerEventRepository repository) {
    this.eventRepository = repository;
   }


    @PostMapping("/events")
    public void addEvent(@RequestBody ContainerEvent containerEvent) {
      eventRepository.save(containerEvent);

    }

    @GetMapping("/events")
    public List<ContainerEvent> getAllContainerEvent() {
       return eventRepository.findAll();
    }

  }