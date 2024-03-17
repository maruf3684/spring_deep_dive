package org.cyber.universal_auth.services.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found !!");
            }
            if (!passwordEncoder.matches(password,user.getPassword())) {
                throw new BadCredentialsException("Invalid credentials !!") {};
            }
            Authentication authenticated = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());
            return authenticated;
        }catch (UsernameNotFoundException e){
            throw new BadCredentialsException("Invalid Credentials !!");
        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
         // return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
         return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
