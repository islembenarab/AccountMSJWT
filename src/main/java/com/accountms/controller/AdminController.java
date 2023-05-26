package com.accountms.controller;

import com.accountms.Dto.SignUpRequest;
import com.accountms.entity.Role;
import com.accountms.entity.User;
import com.accountms.repo.RoleRepository;
import com.accountms.service.AuthService;
import com.accountms.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    public final UserServiceImpl userService;
    public final RoleRepository roleRepository;

    public final AuthService authService;



    @GetMapping("/Users")
    public List<User> findAllUsers() {
        return userService.users();
    }
    @PostMapping("signUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUp) throws Exception {
        return ResponseEntity.ok().body(authService.register(signUp));
    }
    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>("User Not Found ",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("User credentials has been updated");
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@RequestBody User user)  {
        try {
            userService.deleteUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>("User Not Found ",
                    HttpStatus.BAD_REQUEST);

        }
        return ResponseEntity.ok().body("User has been deleted");
    }
    @PostMapping("/createRole")
    public ResponseEntity<?> createRole(@RequestBody Role role){
        try {
            userService.createRole(role);
        } catch (Exception e) {
            return new ResponseEntity<>("There is a problem in creating please verify your credentials",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body("New User has been created");
    }
    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> roles(){
        System.out.println("roles invoked");
        return ResponseEntity.ok().body(userService.findAllRoles());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello () {
        System.out.println(65433321);
        return ResponseEntity.ok().body("loi[pjgrhrkghlkjrgaklfhih");
    }

}
