/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.ordenatareas.controller;

import miumg.edu.gt.ordenatareas.producer.ColaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cola")
@CrossOrigin(origins = "*")
public class ColaController {

    private final ColaProducer colaProducer;

    @Autowired
    public ColaController(ColaProducer colaProducer) {
        this.colaProducer = colaProducer;
    }

    @PostMapping("/enviar")
    public String enviarMensaje(@RequestParam String mensaje) {
        colaProducer.enviarMensaje(mensaje);
        return "Mensaje enviado a RabbitMQ: " + mensaje;
    }
}

