package com.codegym.controller;

import com.codegym.model.Smartphone;
import com.codegym.service.ISmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/smartphones")
public class SmartphoneController {
    @Autowired
    private ISmartphoneService smartphoneService;
    @PostMapping
    public ResponseEntity<Smartphone> createSmartphone(@RequestBody Smartphone smartphone){
        return new ResponseEntity<>(smartphoneService.save(smartphone), HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ModelAndView getAllSmartphonePage(){
        ModelAndView modelAndView = new ModelAndView("/phones/list");
        modelAndView.addObject("smartphones",smartphoneService.findAll());
        return modelAndView;
    }
    @GetMapping
    public ResponseEntity<Iterable<Smartphone>> allPhones(){
        return new ResponseEntity<>(smartphoneService.findAll(),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Smartphone> deleteSmartphone(@PathVariable Long id){
        Optional<Smartphone> smartphone = smartphoneService.findById(id);
        if (!smartphone.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartphoneService.remove(id);
        return new ResponseEntity<>(smartphone.get(),HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editSmartphonePage(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/phones/list");
        Optional<Smartphone> smartphone = smartphoneService.findById(id);
        System.out.println(id);
        mav.addObject("sPhone", smartphone.get());
        System.out.println(smartphone.get().getProducer());
        return mav;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Smartphone> findById(@PathVariable Long id){
        return new ResponseEntity<>(smartphoneService.findById(id).get(),HttpStatus.OK);
    }
   @PutMapping("/{id}")
    public ResponseEntity<Smartphone> editSmartphone(@PathVariable Long id,Smartphone smartphone){
        smartphone.setId(id);
        return new ResponseEntity<>(smartphoneService.save(smartphone),HttpStatus.OK);
   }

}
