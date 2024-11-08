package org.rsb.tyr.config;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.rsb.tyr.services.UserService;
import org.rsb.tyr.util.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

  @Autowired final UserService userService;

  public SecurityConfig(UserService userService) {
    this.userService = userService;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      var userDetails =
          userService
              .getUserByName(username)
              .map(
                  u -> {
                    String roleName = u.getAuthLevel().getName().toUpperCase();
                    String roleWithPrefix =
                        roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;
                    var authority = new SimpleGrantedAuthority(roleWithPrefix);

                    log.info("User {} granted authority: {}", username, authority.getAuthority());

                    var user =
                        new org.springframework.security.core.userdetails.User(
                            u.getName(), u.getPassword(), List.of(authority));

                    log.info("User authorities: {}", user.getAuthorities());
                    return user;
                  })
              .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
      return userDetails;
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence rawPassword) {
        return Authenticator.hashPassword(Authenticator.Password.plain(rawPassword.toString()))
            .getValue();
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Authenticator.validatePassword(
            Authenticator.Password.plain(rawPassword.toString()),
            Authenticator.Password.hashed(encodedPassword));
      }
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth -> {
              try {
                auth.requestMatchers("/api/**")
                    .hasAuthority("ROLE_ADMIN")

                    // Public endpoints
                    .requestMatchers("/login", "/css/**", "/js/**", "/error", "/favicon.ico")
                    .permitAll()

                    // Dashboard access for all authenticated users
                    .requestMatchers("/dashboard")
                    .authenticated()

                    // Standard user endpoints
                    .requestMatchers("/profile/**", "/basic/**")
                    .hasAnyAuthority("ROLE_STANDARD", "ROLE_ADMIN")

                    // Admin-only endpoints
                    .requestMatchers("/admin/**", "/management/**", "/users/**", "/settings/**")
                    .hasAuthority("ROLE_ADMIN")

                    // Any other request requires authentication
                    .anyRequest()
                    .authenticated();

                log.info("Security configuration loaded successfully");
              } catch (Exception e) {
                log.error("Error configuring security", e);
                throw e;
              }
            })
        .formLogin(
            form ->
                form.loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/dashboard", true)
                    .failureUrl("/login?error=true")
                    .permitAll())
        .logout(
            logout ->
                logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout=true")
                    .permitAll())
        .authenticationProvider(authenticationProvider());

    return http.build();
  }
}
