package miumg.edu.gt.ordenatareas.service;

import miumg.edu.gt.datostareas.entity.Tarea;
import miumg.edu.gt.datostareas.entity.Historial;
import miumg.edu.gt.datostareas.repository.TareaRepository;
import miumg.edu.gt.datostareas.repository.HistorialRepository;
import miumg.edu.gt.estructurastareasproyecto.cola.ColaDeTareas;
import miumg.edu.gt.estructurastareasproyecto.cola.NodoCola;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TareaColaServicio {

    private final ColaDeTareas colaTareas = new ColaDeTareas();

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private HistorialRepository historialRepository;

    public String agregarTarea(Tarea tarea) {
        Tarea tareaGuardada = tareaRepository.save(tarea); 
        colaTareas.encolar(tareaGuardada); 

     
        if (tareaGuardada.getUsuario() != null) {
            historialRepository.save(new Historial(
                "Tarea encolada: " + tareaGuardada.getDescripcion(),
                tareaGuardada.getUsuario()
            ));
        }

        return "Tarea encolada: " + tareaGuardada.getDescripcion();
    }

    public Tarea obtenerSiguienteTarea() {
        return colaTareas.desencolar();
    }

    public boolean estaVacia() {
        return colaTareas.estaVacia();
    }

    public Tarea verPrimeraTarea() {
        return colaTareas.peek();
    }

    public List<Tarea> listarTareas() {
        
        List<Tarea> lista = new ArrayList<>();
        NodoCola<Tarea> actual = colaTareas.getFrente();
        while (actual != null) {
            lista.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return lista;
    }
}

