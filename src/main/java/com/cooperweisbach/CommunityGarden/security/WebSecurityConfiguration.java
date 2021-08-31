package com.cooperweisbach.CommunityGarden.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private GardenMemberDetailsService gardenMemberDetailsService;

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(4);
    }

    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(gardenMemberDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//                .antMatchers("/member/**").hasAnyAuthority("ROLE_ADMIN","ROLE_MEMBER")
//                .antMatchers("/login/**").permitAll()
//                .antMatchers("/registration/**").permitAll()
//                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password").loginProcessingUrl("/login/authenticate").defaultSuccessUrl("/members").failureUrl("/login?error=true").permitAll()
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll().and().exceptionHandling().accessDeniedPage("/403");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/styles/**", "/js/**", "/images/**");
    }

}

