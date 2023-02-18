package org.roronoa.banklyuser.rest;

import javax.swing.text.Utilities;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import lombok.RequiredArgsConstructor;

import org.roronoa.banklyuser.config.JwtUtils;
import org.roronoa.banklyuser.util.EntityUtils;
import org.roronoa.banklyuser.util.IConstantes;
import org.roronoa.banklyuser.dto.AuthDto;
import org.roronoa.banklyuser.dto.ResponseDTO;
import org.roronoa.banklyuser.dto.UserDto;
import org.roronoa.banklyuser.entity.User;
import org.roronoa.banklyuser.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("**")
public class UserResource {
    private final IUserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseDTO<UserDto>> register(@RequestBody @Valid UserDto userDTO){

            User user = EntityUtils.userDTOToUser(userDTO);
            user = userService.save(user);
            ResponseDTO<UserDto> response = new ResponseDTO<>() ;
            response.setData(EntityUtils.userToUserDTO(user));
            response.setStatus(IConstantes.CODE_001);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        @PostMapping(path = "/login")
        public ResponseEntity<ResponseDTO<User>> login(@RequestBody @Valid AuthDto authDto){
            try {
                User user = userService.findByEmail(authDto);
                ResponseDTO<User> response = new ResponseDTO<>() ;
                if (user != null){
                    response.setMessage(jwtUtils.generateToken(user));
                    response.setStatus(IConstantes.CODE_001);
                    response.setData(user);
                }else {
                    response.setMessage("user not found");
                    response.setStatus(IConstantes.CODE_002);
                }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ResponseDTO<User> response = new ResponseDTO<>() ;
            response.setStatus(IConstantes.CODE_000);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @GetMapping("/validate-token/{token}")
    public UserDto validateToken(@PathVariable("token") String token){
        return EntityUtils.userToUserDTO(userService.validateToken(token));
    }
    /*@GetMapping(path = "/user/{uuid}")
    public ResponseEntity<ResponseDTO<UserDto>> getUser(@PathVariable @NotEmpty @NotBlank String uuid){
        try {
            ResponseDTO<UserDto> response = new ResponseDTO<>() ;
            User user = userService.getUser(uuid);
            if (!Objects.isNull(user)){
                response.setData(EntityUtils.userToUserDTO(user));
                response.setStatus(CODE_001);
            }else {
                response.setStatus("user not exist");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ResponseDTO<UserDto> response = new ResponseDTO<>() ;
            response.setStatus(CODE_000);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @GetMapping(path = "/users")
    public ResponseEntity<ResponseDTO<List<UserDto>>> getListUser(){
        try {
            List<User> users = userService.getListUsers();
            ResponseDTO<List<UserDto>> response = new ResponseDTO<>() ;
            List<UserDto> usersDTO = users.stream().map(EntityUtils::userToUserDTO).collect(Collectors.toList());
            response.setStatus(CODE_001);
            response.setData(usersDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ResponseDTO<List<UserDto >> response = new ResponseDTO<>() ;
            response.setStatus(CODE_000);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @GetMapping(path = "/userBanner/{uuid}")
    public ResponseEntity<ResponseDTO<UserDto>> bannerUser(@PathVariable @NotEmpty @NotBlank String uuid){
        try {
            ResponseDTO<UserDto> response = new ResponseDTO<>() ;
            User user = userService.bannerUser(uuid);
            if (!Objects.isNull(user)){
                response.setData(EntityUtils.userToUserDTO(user));
                response.setStatus(CODE_001);
            }else {
                response.setStatus("user not exist");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ResponseDTO<UserDto> response = new ResponseDTO<>() ;
            response.setStatus(CODE_000);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }*/

}
