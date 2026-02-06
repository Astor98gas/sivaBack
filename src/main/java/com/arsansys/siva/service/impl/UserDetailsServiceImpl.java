package com.arsansys.siva.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arsansys.siva.model.entity.UserEntity;
import com.arsansys.siva.repository.mongo.UserRepository;


/**
 * Servicio que implementa métodos para la gestión de usuarios y autenticación.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        /**
         * Carga los detalles de un usuario por su nombre de usuario.
         *
         * @param username Nombre de usuario.
         * @return Detalles del usuario (UserDetails).
         * @throws UsernameNotFoundException Si el usuario no existe.
         */
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "El usuario: " + username + " no existe."));

                Collection<? extends GrantedAuthority> authorities = Stream.of(userEntity.getRol())
                                .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getName().name())))
                                .collect(Collectors.toSet());

                return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getActive(), true, true,
                                true,
                                authorities);
        }

}
