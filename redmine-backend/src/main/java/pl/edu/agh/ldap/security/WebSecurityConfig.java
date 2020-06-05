package pl.edu.agh.ldap.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationEntryPoint authenticationEntryPoint = new RestAuthenticationEntryPoint();

    @Value("${ldapUrl}")
    private String ldapUrl;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(new RestAccessDeniedHandler());

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(new JwtValidator(), authenticationEntryPoint),
                        BasicAuthenticationFilter.class);

        http.csrf().disable();
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.applyPermitDefaultValues();
            corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
            corsConfiguration.addAllowedMethod(HttpMethod.POST);
            corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
            corsConfiguration.addAllowedMethod(HttpMethod.PUT);
            corsConfiguration.addExposedHeader(HttpHeaders.AUTHORIZATION);
            return corsConfiguration;
        });

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://" + ldapUrl + ":389/dc=redmine2,dc=org")
                .and()
                .passwordCompare()
                .passwordAttribute("userPassword");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserAuthenticationService userAuthenticationServiceBean(AuthenticationManager manager) {
        JwtCreator jwtCreator = new JwtCreator();
        return new UserAuthenticationService(manager, jwtCreator);
    }
}
