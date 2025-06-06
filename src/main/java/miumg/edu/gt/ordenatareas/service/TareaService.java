package miumg.edu.gt.ordenatareas.service;

import miumg.edu.gt.datostareas.entity.Usuario;
import miumg.edu.gt.datostareas.entity.Tarea;
import miumg.edu.gt.datostareas.entity.Historial;
import miumg.edu.gt.datostareas.repository.TareaRepository;
import miumg.edu.gt.datostareas.repository.UsuarioRepository;
import miumg.edu.gt.datostareas.repository.HistorialRepository;

import miumg.edu.gt.estructurastareasproyecto.lista.ListaEnlazada;
import miumg.edu.gt.estructurastareasproyecto.arbol.Arbol;
import miumg.edu.gt.estructurastareasproyecto.arbol.NodoArbol;
import miumg.edu.gt.estructurastareasproyecto.pila.Pila;
import miumg.edu.gt.estructurastareasproyecto.cola.Cola;
import miumg.edu.gt.estructurastareasproyecto.cola.NodoCola;
import miumg.edu.gt.ordenatareas.producer.ColaProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistorialRepository historialRepository;
    private final ColaProducer colaProducer;

    private final ListaEnlazada<Tarea> listaTareas = new ListaEnlazada<>();
    private final Arbol<Tarea> arbolTareas = new Arbol<>(null);
    private final Pila<Tarea> pilaTareasEliminadas = new Pila<>();
    private final Cola<Tarea> colaTareasProgramadas = new Cola<>();

    @Autowired
    public TareaService(
        TareaRepository tareaRepository,
        UsuarioRepository usuarioRepository,
        HistorialRepository historialRepository,
        ColaProducer colaProducer
    ) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
        this.historialRepository = historialRepository;
        this.colaProducer = colaProducer;
    }

    public List<Tarea> imprimirCola() {
        List<Tarea> lista = new ArrayList<>();
        NodoCola<Tarea> actual = colaTareasProgramadas.getFrente();
        while (actual != null) {
            lista.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return lista;
    }


    public Tarea crearSubtarea(Long idPadre, Long idUsuario, Tarea nuevaTarea) {
        Optional<Tarea> tareaPadreOpt = tareaRepository.findById(idPadre);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if (tareaPadreOpt.isPresent() && usuarioOpt.isPresent()) {
            nuevaTarea.setUsuario(usuarioOpt.get());
            Tarea guardada = tareaRepository.save(nuevaTarea);

            NodoArbol<Tarea> nodoPadre = buscarNodo(arbolTareas.getRaiz(), tareaPadreOpt.get());
            if (nodoPadre != null) {
                nodoPadre.agregarHijo(new NodoArbol<>(guardada));
            } else if (arbolTareas.getRaiz().getDato() == null) {
                NodoArbol<Tarea> nuevaRaiz = new NodoArbol<>(tareaPadreOpt.get());
                nuevaRaiz.agregarHijo(new NodoArbol<>(guardada));
                arbolTareas.setRaiz(nuevaRaiz);
            }

            return guardada;
        }

        return null;
    }

    private NodoArbol<Tarea> buscarNodo(NodoArbol<Tarea> nodo, Tarea tareaBuscada) {
        if (nodo == null) return null;
        if (nodo.getDato() != null && nodo.getDato().getId().equals(tareaBuscada.getId())) {
            return nodo;
        }

        for (NodoArbol<Tarea> hijo : nodo.getHijos()) {
            NodoArbol<Tarea> encontrado = buscarNodo(hijo, tareaBuscada);
            if (encontrado != null) return encontrado;
        }

        return null;
    }

    public Tarea crearTarea(Long usuarioId, Tarea tarea) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isPresent()) {
            tarea.setUsuario(usuario.get());
            Tarea tareaGuardada = tareaRepository.save(tarea);
            listaTareas.agregar(tareaGuardada);

            historialRepository.save(new Historial("Tarea creada: " + tarea.getTitulo(), tarea.getUsuario()));
            colaProducer.enviarMensaje("Tarea creada: " + tarea.getTitulo());

            return tareaGuardada;
        }
        return null;
    }

    public List<Tarea> obtenerTodas() {
        return tareaRepository.findAll();
    }

    public ListaEnlazada<Tarea> obtenerTareasMemoria() {
        return listaTareas;
    }

    public Optional<Tarea> obtenerPorId(Long id) {
        return tareaRepository.findById(id);
    }

    public void eliminarTarea(Long id) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tareaRepository.delete(tarea);
            pilaTareasEliminadas.push(tarea);

            historialRepository.save(new Historial("Tarea eliminada: " + tarea.getTitulo(), tarea.getUsuario()));
            colaProducer.enviarMensaje("Tarea eliminada: " + tarea.getTitulo());
        }
    }

    public Tarea deshacerTareaEliminada() {
        if (!pilaTareasEliminadas.estaVacia()) {
            Tarea tareaRestaurada = pilaTareasEliminadas.pop();
            tareaRestaurada.setId(null);
            Tarea tareaGuardada = tareaRepository.save(tareaRestaurada);

            historialRepository.save(new Historial("Se deshizo la eliminación de: " + tareaGuardada.getTitulo(), tareaGuardada.getUsuario()));
            colaProducer.enviarMensaje("Se deshizo la eliminación de: " + tareaGuardada.getTitulo());

            return tareaGuardada;
        }
        return null;
    }

    public void imprimirArbol() {
        arbolTareas.imprimirArbol();
    }

    public String encolarTareaProgramada(Long idTarea) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(idTarea);
        if (tareaOpt.isEmpty()) {
            return "Tarea no encontrada";
        }

        Tarea tarea = tareaOpt.get();
        colaTareasProgramadas.encolar(tarea);

        historialRepository.save(new Historial("Tarea programada en cola: " + tarea.getTitulo(), tarea.getUsuario()));
        colaProducer.enviarMensaje("Tarea programada en cola: " + tarea.getTitulo());

        return "Tarea encolada correctamente";
    }

    public Tarea atenderTareaProgramada() {
        Tarea tarea = colaTareasProgramadas.desencolar();
        if (tarea != null) {
            historialRepository.save(new Historial("Tarea atendida desde cola: " + tarea.getTitulo(), tarea.getUsuario()));
            colaProducer.enviarMensaje("Tarea atendida desde cola: " + tarea.getTitulo());
        }
        return tarea;
    }
}
