/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.ordenatareas.controller;

import miumg.edu.gt.datostareas.entity.Tarea;
import miumg.edu.gt.ordenatareas.service.TareaColaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cola")
@CrossOrigin(origins = "*")
public class TareaColaController {

    private final TareaColaServicio colaServicio;

    @Autowired
    public TareaColaController(TareaColaServicio colaServicio) {
        this.colaServicio = colaServicio;
    }

    @PostMapping("/encolar")
    public String encolarTarea(@RequestBody Tarea tarea) {
        return colaServicio.agregarTarea(tarea);
    }

    @GetMapping("/desencolar")
    public Tarea desencolarTarea() {
        return colaServicio.obtenerSiguienteTarea();
    }

    @GetMapping("/ver")
    public Tarea verPrimera() {
        return colaServicio.verPrimeraTarea();
    }

    @GetMapping("/vacia")
    public boolean estaVacia() {
        return colaServicio.estaVacia();
    }
}
