package miumg.edu.gt.ordenatareas.service;

import miumg.edu.gt.datostareas.entity.Historial;
import miumg.edu.gt.datostareas.entity.Usuario;
import miumg.edu.gt.datostareas.repository.HistorialRepository;
import miumg.edu.gt.datostareas.repository.UsuarioRepository;


import miumg.edu.gt.ordenatareas.producer.ColaProducer;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialService {

    private final HistorialRepository historialRepository;
    private final UsuarioRepository usuarioRepository;
    private final ColaProducer colaProducer;

    public HistorialService(
        HistorialRepository historialRepository,
        UsuarioRepository usuarioRepository,
        ColaProducer colaProducer
    ) {
        this.historialRepository = historialRepository;
        this.usuarioRepository = usuarioRepository;
        this.colaProducer = colaProducer;
    }

    public Historial registrarAccion(String descripcion, Long idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isPresent()) {
            Historial historial = new Historial();
            historial.setDescripcion(descripcion);
            historial.setFecha(LocalDateTime.now());
            historial.setUsuario(usuarioOpt.get());

            Historial guardado = historialRepository.save(historial);
            colaProducer.enviarMensaje("Historial registrado: " + descripcion);
            return guardado;
        }
        return null;
    }

    public Historial registrarEvento(Long usuarioId, String descripcion) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Historial historial = new Historial();
            historial.setUsuario(usuarioOpt.get());
            historial.setDescripcion(descripcion);
            historial.setFecha(LocalDateTime.now());

            Historial guardado = historialRepository.save(historial);
            colaProducer.enviarMensaje("Evento registrado: " + descripcion);
            return guardado;
        }
        return null;
    }

    public List<Historial> obtenerTodos() {
        return historialRepository.findAll();
    }

    public Optional<Historial> obtenerPorId(Long id) {
        return historialRepository.findById(id);
    }

    public void eliminarHistorial(Long id) {
        historialRepository.deleteById(id);
    }
}
