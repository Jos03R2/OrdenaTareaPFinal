package miumg.edu.gt.ordenatareas.controller;

import miumg.edu.gt.datostareas.entity.Tarea;
import miumg.edu.gt.ordenatareas.service.TareaService;
import miumg.edu.gt.ordenatareas.service.TareaColaServicio;
import miumg.edu.gt.estructurastareasproyecto.lista.ListaEnlazada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareaController {

    private final TareaService tareaService;
    private final TareaColaServicio colaServicio;

    @Autowired
    public TareaController(TareaService tareaService, TareaColaServicio colaServicio) {
        this.tareaService = tareaService;
        this.colaServicio = colaServicio;
    }

   
    @PostMapping("/usuario/{id}")
    public ResponseEntity<Tarea> crearTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        Tarea nueva = tareaService.crearTarea(id, tarea);
        return ResponseEntity.ok(nueva);
    }

    
    @PostMapping("/subtarea")
    public ResponseEntity<Tarea> crearSubtarea(@RequestParam Long idPadre,
                                                @RequestParam Long idUsuario,
                                                @RequestBody Tarea tarea) {
        Tarea subtarea = tareaService.crearSubtarea(idPadre, idUsuario, tarea);
        return ResponseEntity.ok(subtarea);
    }

    
    @GetMapping
    public List<Tarea> listarTareas() {
        return tareaService.obtenerTodas();
    }

    
    @GetMapping("/arbol")
    public void imprimirArbol() {
        tareaService.imprimirArbol();
    }

  
    @DeleteMapping("/{id}")
    public void eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
    }

   
    @PostMapping("/deshacer")
    public ResponseEntity<Tarea> deshacerEliminacion() {
        Tarea restaurada = tareaService.deshacerTareaEliminada();
        return ResponseEntity.ok(restaurada);
    }

   
    @GetMapping("/memoria")
    public ListaEnlazada<Tarea> obtenerDesdeMemoria() {
        return tareaService.obtenerTareasMemoria();
    }

    
    @PostMapping("/programar/{id}")
    public ResponseEntity<String> encolarTarea(@PathVariable Long id) {
        String respuesta = tareaService.encolarTareaProgramada(id);
        return ResponseEntity.ok(respuesta);
    }

 
    @GetMapping("/programada/atender")
    public ResponseEntity<Tarea> atenderTareaProgramada() {
        Tarea tarea = tareaService.atenderTareaProgramada();
        if (tarea == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tarea);
    }

    
    @GetMapping("/imprimir")
    public List<Tarea> imprimirCola() {
        return colaServicio.listarTareas();
    }
}
