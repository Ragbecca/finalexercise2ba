package com.ragbecca.finalexercise2ba.security;

import com.ragbecca.finalexercise2ba.entity.Quote;
import com.ragbecca.finalexercise2ba.entity.Task;
import com.ragbecca.finalexercise2ba.entity.TaskCategory;
import com.ragbecca.finalexercise2ba.entity.User;
import com.ragbecca.finalexercise2ba.repository.QuoteRepository;
import com.ragbecca.finalexercise2ba.repository.TaskCategoryRepository;
import com.ragbecca.finalexercise2ba.repository.TaskRepository;
import com.ragbecca.finalexercise2ba.repository.UserRepository;
import com.ragbecca.finalexercise2ba.service.UserDetailsServiceImpl;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.sql.Date;
import java.sql.Time;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    public static final String USER = "USER";
    private final UserRepository userRepository;

    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskRepository taskRepository;
    private final QuoteRepository quoteRepository;
    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(daoAuthenticationProvider());

        http.authorizeRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable();

        if (userRepository.findByUsername("ragbecca").isEmpty()) {
            User user = new User();
            user.setUsername("ragbecca");
            user.setEmail("rebeccadj2003@gmail.com");
            user.setPassword(passwordEncoder().encode("Welkom123!"));
            user.setRole("ADMIN");
            userRepository.save(user);
        }

        User user = userRepository.findByUsername("ragbecca").get();

        if (taskCategoryRepository.findByCategoryName("Advancius").isEmpty()) {
            TaskCategory taskCategory = new TaskCategory();
            taskCategory.setCategoryName("Advancius");
            taskCategoryRepository.save(taskCategory);
        }

        if (taskCategoryRepository.findByCategoryName("School").isEmpty()) {
            TaskCategory taskCategory = new TaskCategory();
            taskCategory.setCategoryName("School");
            taskCategoryRepository.save(taskCategory);
        }

        TaskCategory taskCategory = taskCategoryRepository.findByCategoryName("Advancius").get();

        if (taskRepository.findAllByUsername(user.getUsername()).isEmpty()) {
            Task task = new Task();
            task.setDeadlineDate(Date.valueOf("2023-04-03"));
            task.setDeadlineTime(Time.valueOf("12:00:00"));
            task.setUsername(user.getUsername());
            task.setStatus(false);
            task.setTaskCategory(taskCategory);
            task.setTaskName("HAPPY BIRTHDAY");
            taskRepository.save(task);
        }

        if (quoteRepository.findAllByUsername("unknown").isEmpty()) {
            Quote quote = new Quote();
            quote.setUsername("unknown");
            quote.setQuote("Life isn’t about getting and having, it’s about giving and being.");
            quote.setAuthor("Kevin Kruse");
            quoteRepository.save(quote);
        }

        return http.build();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
