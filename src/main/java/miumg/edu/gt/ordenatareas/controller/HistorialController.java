package miumg.edu.gt.ordenatareas.controller;

import miumg.edu.gt.ordenatareas.service.HistorialService;
import miumg.edu.gt.datostareas.entity.Historial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "*")
public class HistorialController {

    private final HistorialService historialService;

    @Autowired
    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    
    @PostMapping("/usuario/{usuarioId}")
    public Historial registrarEvento(@PathVariable Long usuarioId, @RequestParam String descripcion) {
        return historialService.registrarEvento(usuarioId, descripcion);
    }

    
    @GetMapping
    public List<Historial> obtenerTodos() {
        return historialService.obtenerTodos();
    }

    
    @GetMapping("/{id}")
    public Optional<Historial> obtenerPorId(@PathVariable Long id) {
        return historialService.obtenerPorId(id);
    }

    
    @DeleteMapping("/{id}")
    public void eliminarHistorial(@PathVariable Long id) {
        historialService.eliminarHistorial(id);
    }
}
