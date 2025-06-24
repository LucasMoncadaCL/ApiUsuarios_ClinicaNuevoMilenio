package com.clinicanuevomilenio.usuarios.services;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinicanuevomilenio.usuarios.config.UsuarioMapper;
import com.clinicanuevomilenio.usuarios.dto.UsuarioActualizacionDTO;
import com.clinicanuevomilenio.usuarios.dto.UsuarioCreacionRequest;
import com.clinicanuevomilenio.usuarios.dto.UsuarioRespuestaDTO;
import com.clinicanuevomilenio.usuarios.models.Persona;
import com.clinicanuevomilenio.usuarios.models.Rol;
import com.clinicanuevomilenio.usuarios.models.Usuario;

import com.clinicanuevomilenio.usuarios.repository.RolRepository;
import com.clinicanuevomilenio.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final RolRepository rolRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UsuarioMapper usuarioMapper;

    @Transactional
    public UsuarioRespuestaDTO crearUsuario(UsuarioCreacionRequest request) {
        // 1. Validar y buscar el Rol
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + request.getRolId()));

        // 2. Crear y configurar la entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword())); // Encriptar contraseña
        usuario.setRol(rol);
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setEstado(true); // Por defecto, activo
        usuario.setEstaDisponible(true); // Por defecto, disponible

        // 3. Crear y configurar la entidad Persona
        Persona persona = new Persona();
        persona.setRut(request.getRut());
        persona.setDv(request.getDv());
        persona.setPnombre(request.getPnombre());
        persona.setSnombre(request.getSnombre());
        persona.setAppaterno(request.getAppaterno());
        persona.setApmaterno(request.getApmaterno());
        persona.setNumeroTelefono(request.getNumeroTelefono());
        persona.setEspecialidad(request.getEspecialidad());
        persona.setEmail(request.getEmail());

        // 4. Establecer la relación bidireccional
        persona.setUsuario(usuario);
        usuario.setPersona(persona);

        // 5. Guardar el usuario (la persona se guardará en cascada)
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 6. Devolver el DTO de respuesta
        return convertirAUsuarioRespuestaDTO(usuarioGuardado);
    }

    @Transactional
public UsuarioRespuestaDTO actualizarUsuario(Integer id, UsuarioActualizacionDTO dto) {
    // 1. Buscar el usuario existente o lanzar excepción
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
    Persona persona = usuario.getPersona();

    // 2. Buscar el nuevo rol
    Rol nuevoRol = rolRepository.findById(dto.getRolId())
            .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + dto.getRolId()));

    // 3. Actualizar los campos
    persona.setPnombre(dto.getPnombre());
    persona.setAppaterno(dto.getAppaterno());
    // ... actualizar los demás campos de la persona ...
    persona.setEmail(dto.getEmail());

    usuario.setRol(nuevoRol);
    usuario.setEstado(dto.getEstado());

    // 4. Guardar y devolver el DTO
    // Como la relación tiene Cascade, solo necesitamos guardar el usuario
    Usuario usuarioActualizado = usuarioRepository.save(usuario);
    return convertirAUsuarioRespuestaDTO(usuarioActualizado);
}

@Transactional
public void eliminarUsuario(Integer id) {
    if (!usuarioRepository.existsById(id)) {
        throw new EntityNotFoundException("No se puede eliminar: Usuario no encontrado con ID: " + id);
    }
    usuarioRepository.deleteById(id);
}

    public UsuarioRespuestaDTO buscarUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toUsuarioRespuestaDTO(usuario);
    }

    public List<UsuarioRespuestaDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toUsuarioRespuestaDTO)
                .collect(Collectors.toList());
    }

    // --- Método de Mapeo ---
    private UsuarioRespuestaDTO convertirAUsuarioRespuestaDTO(Usuario usuario) {
        Persona persona = usuario.getPersona();
        String nombreCompleto = persona.getPnombre() + " " + persona.getAppaterno();

        return UsuarioRespuestaDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .username(usuario.getUsername())
                .nombreCompleto(nombreCompleto)
                .email(persona.getEmail())
                .rol(usuario.getRol().getNombre())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
}