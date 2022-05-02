package com.pluralsight.conferencedemo.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getById(id);
    }

    @PostMapping
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        //Also need to check for children records before deleting.
        speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Session session){
        //because this is a PUT, we expect all attributes to be passed in.
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload

        Speaker existingSpeaker = speakerRepository.getById(id);
        BeanUtils.copyProperties(session, existingSpeaker, "speaker_id");

        return speakerRepository.saveAndFlush(existingSpeaker);
    }
}
