package com.kaikeventura.pbp.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class SecurityConfiguration {

    @Bean
    fun userDetailsService(bCryptPasswordEncoder: BCryptPasswordEncoder): UserDetailsService {
        val manager = InMemoryUserDetailsManager()
        manager.createUser(
            User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("userPass"))
                .roles("USER")
                .build()
        )
        manager.createUser(
            User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("adminPass"))
                .roles("USER", "ADMIN")
                .build()
        )
        return manager
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .authorizeHttpRequests { authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.DELETE)
                    .hasRole("ADMIN")
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/authenticated/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/login/**").permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement { httpSecuritySessionManagementConfigurer: SessionManagementConfigurer<HttpSecurity?> ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }

        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico")
        }
    }
}
