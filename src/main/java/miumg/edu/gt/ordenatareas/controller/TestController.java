/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.ordenatareas.controller;

import miumg.edu.gt.ordenatareas.producer.ColaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final ColaProducer colaProducer;

    @Autowired
    public TestController(ColaProducer colaProducer) {
        this.colaProducer = colaProducer;
    }

    @GetMapping("/enviar")
    public String enviar() {
        colaProducer.enviarMensaje("Mensaje de prueba");
        return "Mensaje enviado";
    }
}
