package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600) //Liberando o acesso de todos os path's para todos!
@RequestMapping("/auth")
public class AuthenticationController {

    //private Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @JsonView(UserDto.UserView.RegistrationPost.class) @Validated(UserDto.UserView.RegistrationPost.class) UserDto userDto){

        log.debug("POST registerUser userDto received {}", userDto.toString());

        if(userService.existsByUsername(userDto.getUsername())){
            log.warn(" Username {} is Already Taken ", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if(userService.existsByEmail(userDto.getEmail())){
            log.warn(" Email {} is Already Taken ", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        userService.save(userModel);

        log.debug("POST registerUser userDto saved {}", userModel.getUserId());
        log.info("User saved successfully userId: {}", userModel.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @GetMapping("/")
    public String index(){

        //Seu uso é para rastreamento! Traz muitos detalhes! Granularidade mais fina!
        log.trace("TRACE");

        //Usado normalmente no ambiente de desenvolvimento! É para o desenvolvedor entender algo!
        log.debug("DEBUG");

        //Esse é mais usado no ambiente de Produção. Traz mais informação do que esta acontecendo no processo!
        log.info("INFO");

        //Serve para alertar!
        log.warn("WARN");

        //Serve para exibir um erro! Detalhar o error! É usado muito dentro do bloco Catch{}
        log.error("ERROR");

        return "Logging Spring Boot ...";
    }
}
