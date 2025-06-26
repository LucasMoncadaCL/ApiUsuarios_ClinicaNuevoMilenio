package com.clinicanuevomilenio.usuarios.controllers;

import com.clinicanuevomilenio.usuarios.dto.UsuarioActualizacionDTO;
import com.clinicanuevomilenio.usuarios.dto.UsuarioCreacionRequest;
import com.clinicanuevomilenio.usuarios.dto.UsuarioRespuestaDTO;
import com.clinicanuevomilenio.usuarios.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioCreacionRequest request) {
        try {
            UsuarioRespuestaDTO usuarioCreado = usuarioService.crearUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Ocurrió un error inesperado al crear el usuario."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            UsuarioRespuestaDTO usuario = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRespuestaDTO>> listarTodos() {
        List<UsuarioRespuestaDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioActualizacionDTO dto) {
        try {
            UsuarioRespuestaDTO usuarioActualizado = usuarioService.actualizarUsuario(id, dto);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/por-ids")
    public ResponseEntity<List<UsuarioRespuestaDTO>> obtenerUsuariosPorIds(@RequestParam List<Integer> ids) {
        List<UsuarioRespuestaDTO> usuarios = usuarioService.buscarUsuariosPorIds(ids);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/por-rol")
    public ResponseEntity<List<UsuarioRespuestaDTO>> obtenerUsuariosPorRol(@RequestParam String rol) {
        List<UsuarioRespuestaDTO> usuarios = usuarioService.buscarPorRol(rol);
        return ResponseEntity.ok(usuarios);
    }
}