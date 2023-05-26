package com.accountms.service;

import com.accountms.entity.Role;
import com.accountms.entity.User;
import com.accountms.repo.RoleRepository;
import com.accountms.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<User> users(){
        return userRepository.findAll();
    }

    public void createUser(User user)  {

            userRepository.save(user);

    }
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }
    public void updateUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(User user) throws Exception {
        if (! userRepository.existsById(user.getUserId())){
            throw new Exception("User not Found");
        }
         userRepository.delete(user);
    }
    public void createRole(Role role){
        try {
            roleRepository.save(role);
        }catch (Exception e){
            try {
                throw new Exception("role is already exist");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow();
    }
}
