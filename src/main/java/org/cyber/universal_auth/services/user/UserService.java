package org.cyber.universal_auth.services.user;

import org.cyber.universal_auth.dto.auth.LoginRequestDto;
import org.cyber.universal_auth.dto.auth.LoginResponseDto;
import org.cyber.universal_auth.dto.pojo.PermissionDtoRepo;
import org.cyber.universal_auth.dto.user.PermissionDto;
import org.cyber.universal_auth.dto.user.UserDto;
import org.cyber.universal_auth.enums.Role;
import org.cyber.universal_auth.enums.UserType;
import org.cyber.universal_auth.exceptions.EmailAlreadyExistsException;
import org.cyber.universal_auth.exceptions.UserNotFoundException;
import org.cyber.universal_auth.models.PermissionGroup;
import org.cyber.universal_auth.models.User;
import org.cyber.universal_auth.repositories.PermissionGroupRepository;
import org.cyber.universal_auth.repositories.PermissionRepository;
import org.cyber.universal_auth.repositories.UserRepository;
import org.cyber.universal_auth.services.auth.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final PermissionGroupRepository permissionGroupRepository;
        private final AuthenticationManager authenticationManager;
        private  final PermissionRepository permissionRepository;
        private final UserDetailsService userDetailsService;

        private final JwtService jwtService;
        public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PermissionGroupRepository permissionGroupRepository, AuthenticationManager authenticationManager, PermissionRepository permissionRepository, UserDetailsService userDetailsService, JwtService jwtService) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
            this.permissionGroupRepository = permissionGroupRepository;
            this.authenticationManager = authenticationManager;
            this.permissionRepository = permissionRepository;
            this.userDetailsService = userDetailsService;
            this.jwtService = jwtService;
        }
        public boolean isUserExist(String email){
            if (userRepository.findByEmail(email).isPresent()) {
                return true;
            }else {
                return false;
            }
        }

        private String doAuthenticate(String email, String password) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication =  authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (authentication.isAuthenticated()) {
                return jwtService.createToken(email);
            } else {
                throw new BadCredentialsException("Invalid Username or Password!!");
            }
        }

        public User registerUser(User user) {
            if (isUserExist(user.getEmail())) {
                throw new EmailAlreadyExistsException("Email address already exist");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserType(UserType.NON_WALLET_HOLDER);
            user.setRole(Role.USER);
            user.setActive(true);
            return userRepository.save(user);
        }

        public LoginResponseDto loginUser(LoginRequestDto credentials){
            String token = this.doAuthenticate(credentials.getEmail(), credentials.getPassword());
            return LoginResponseDto.builder().email(credentials.getEmail()).accessToken(token).build();
        }

        public List<UserDto> getAllUser(){
            List<User> users = userRepository.findAll();
            List<UserDto> userDto = users.stream().map(UserDto::new).collect(Collectors.toList());
            return userDto;
        }

        public List<PermissionDto> getAllPermission(){
            List<Object[]> rawPermissionList = permissionRepository.getAllPermission();
            List<PermissionDto> permissions = rawPermissionList.stream().map(row -> new PermissionDto((String) row[0])).collect(Collectors.toList());
            return permissions;
        }

        public List<PermissionDto> getAllPermissionV2(){
            List<PermissionDtoRepo> rawPermissionList = permissionRepository.getAllPermissionV2();
            System.out.println(rawPermissionList.get(0).getPermissionName()); // "permissionName": null
            List<PermissionDto> permissions = rawPermissionList.stream().map(row -> new PermissionDto(row.getPermissionName())).collect(Collectors.toList());
            return permissions;
        }

        public List<PermissionGroup> getAllPermissionGroup(){
           return permissionGroupRepository.findAll();
        }

    }

