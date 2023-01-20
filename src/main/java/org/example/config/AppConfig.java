package org.example.config;

import org.example.dao.CategoryDao;
import org.example.dao.OrderDao;
import org.example.dao.ProductDao;
import org.example.dao.UserDao;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.*;

import javax.sql.DataSource;


@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class AppConfig implements WebMvcConfigurer {

    private final Environment environment;

    private final String URL = "url";
    private final String USER = "dbuser";
    private final String DRIVER = "driver";
    private final String PASSWORD = "dbpassword";

    @Autowired
    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty(URL));
        driverManagerDataSource.setUsername(environment.getProperty(USER));
        driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
        driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
        return driverManagerDataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    UserDao userDao() {
        return new UserDao(jdbcTemplate());
    }

    @Bean
    AuthService authService() {
        return new AuthService(userDao());
    }

    @Bean
    CategoryDao categoryDao() {
        return new CategoryDao(jdbcTemplate());
    }

    @Bean
    ProductDao productDao() {
        return new ProductDao(jdbcTemplate());
    }

    @Bean
    OrderDao orderDao() {
        return new OrderDao(jdbcTemplate());
    }

    @Bean
    CategoryService categoryService() {
        return new CategoryService(categoryDao());
    }

    @Bean
    ProductService productService() {
        return new ProductService(productDao(), categoryDao());
    }

    @Bean
    UserService userService() {
        return new UserService(userDao());
    }
    @Bean
    BasketService basketService(){ return  new BasketService();}

    @Bean
    OrderService orderService() {
        return new OrderService(productDao(), orderDao());
    }
    @Bean
    CookieService cookieService(){return  new CookieService();}
    
    @Bean
    OrderService orderService(){return new OrderService();}
    
   @Bean
    OrderDao orderDao(){
        return new OrderDao();
   }
}
