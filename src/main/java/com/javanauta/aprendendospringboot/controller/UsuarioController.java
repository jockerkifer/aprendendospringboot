package com.javanauta.aprendendospringboot.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javanauta.aprendendospringboot.business.UsuarioService;
import com.javanauta.aprendendospringboot.controller.DTOs.UsuarioDTO;
import com.javanauta.aprendendospringboot.infrastructure.entity.Usuario;
import com.javanauta.aprendendospringboot.infrastructure.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController  {

    
	private final AuthenticationManager authenticationManager;
	private final UsuarioService usuarioService;
	private final JwtUtil jwtUtil;
	
    
	
	@PostMapping
	public ResponseEntity<Usuario>  salvarDados(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioService.salvarDados(usuario));
	}
	
	@PostMapping("/login")
	public String login(@RequestBody UsuarioDTO usuarioDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha()));
		return jwtUtil.generateToken(authentication.getName());
	}	
	
	@GetMapping
	public ResponseEntity<Usuario> buscarEmailUsuario(@RequestParam String email){
		return ResponseEntity.ok(usuarioService.buscarEmailUsuario(email));
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<Void> deletaUsuarioEmail(@PathVariable String email){
		usuarioService.deletarEmailUsuario(email);
		return ResponseEntity.ok().build();
		
	}
		
		
}
