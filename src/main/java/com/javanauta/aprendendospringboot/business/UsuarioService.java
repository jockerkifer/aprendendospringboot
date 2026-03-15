package com.javanauta.aprendendospringboot.business;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javanauta.aprendendospringboot.infrastructure.entity.Usuario;
import com.javanauta.aprendendospringboot.infrastructure.exception.ConflictException;
import com.javanauta.aprendendospringboot.infrastructure.exception.ResourceNotFoundException;
import com.javanauta.aprendendospringboot.infrastructure.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	
	public Usuario  salvarDados(Usuario usuario) {
		try {
			emailEncontrado(usuario.getEmail());
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); 
			return usuarioRepository.save(usuario);
			
		}catch(ConflictException e ) {
			throw new ConflictException("Email Encontrado " +e.getCause());
		}
	}
	
	public void emailEncontrado(String email) {
		try {
			boolean existe = buscarEmail(email);
			if(existe) {
				throw new ConflictException("Email Encontrado!!" +email);
			}
		}catch(ConflictException e) {
			throw new ConflictException("Email Encontrado " +e.getCause());
		}
	}
	
	public Boolean buscarEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}
	
		
	public Usuario buscarEmailUsuario(String email) {
		return usuarioRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException("Email não encontrado" +email));
		
	}
	
	public void deletarEmailUsuario(String email) {
		usuarioRepository.deleteByemail(email);
	}
		

}
