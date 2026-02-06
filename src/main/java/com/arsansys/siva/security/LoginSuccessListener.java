package com.arsansys.siva.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// import com.arsansys.siva.models.entities.RolEntity;
// import com.arsansys.siva.models.entities.Suscripcion;
// import com.arsansys.siva.models.entities.UserEntity;
// import com.arsansys.siva.models.enums.ERol;
// import com.arsansys.siva.services.UserService;
// import com.arsansys.siva.services.stripe.SuscripcionService;

/**
 * Listener que se ejecuta tras un inicio de sesión exitoso.
 * <p>
 * Verifica la suscripción del usuario y ajusta el rol si es necesario.
 */
// @Component
// public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private SuscripcionService suscripcionService;

//     /**
//      * Método que se ejecuta cuando ocurre un evento de autenticación exitosa.
//      * <p>
//      * Si el usuario es vendedor, verifica si tiene suscripción activa.
//      * Si no tiene suscripción o está caducada, cambia el rol a comprador.
//      *
//      * @param event Evento de autenticación exitosa.
//      */
//     @Override
//     public void onApplicationEvent(@NonNull AuthenticationSuccessEvent event) {
//         try {

//             UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
//             String username = userDetails.getUsername();

//             // Obtener información del usuario
//             UserEntity userEntity = userService.getUserByUsername(username);

//             // Verificar suscripción para vendedores
//             if (userEntity.getRol().getName() == ERol.VENDEDOR) {
//                 List<Suscripcion> suscripciones = suscripcionService.getSuscripcionesByIdUsuario(userEntity.getId());
//                 if (suscripciones.isEmpty()) {
//                     // Si no tiene suscripciones, cambiar a rol de comprador
//                     userEntity.setRol(RolEntity.builder().name(ERol.COMPRADOR).build());
//                     userService.updateUser(userEntity);
//                 } else {
//                     // Ordenamos por fecha de compra (descendente) para obtener la más reciente
//                     suscripciones.sort((s1, s2) -> s2.getFechaCompra().compareTo(s1.getFechaCompra()));
//                     Suscripcion ultimaSuscripcion = suscripciones.get(0);

//                     // Verificamos si está caducada
//                     if (ultimaSuscripcion.getFechaVencimiento().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)
//                             .toEpochMilli() <= System.currentTimeMillis()) {
//                         userEntity.setRol(RolEntity.builder().name(ERol.COMPRADOR).build());
//                         userService.updateUser(userEntity);
//                     }
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
