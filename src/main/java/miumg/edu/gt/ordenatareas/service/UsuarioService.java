/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.ordenatareas.service;

import miumg.edu.gt.datostareas.entity.Usuario;
import miumg.edu.gt.datostareas.entity.Historial;
import miumg.edu.gt.datostareas.entity.Tarea;
import miumg.edu.gt.datostareas.repository.UsuarioRepository;
import miumg.edu.gt.datostareas.repository.HistorialRepository;
import miumg.edu.gt.datostareas.repository.TareaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private TareaRepository tareaRepository;

   
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado.");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void eliminarUsuarioYHistorial(Long id) {
        List<Tarea> tareas = tareaRepository.findByUsuarioId(id);
        tareaRepository.deleteAll(tareas);

        List<Historial> historialList = historialRepository.findByUsuarioId(id);
        historialRepository.deleteAll(historialList);

        usuarioRepository.deleteById(id);
    }
}
