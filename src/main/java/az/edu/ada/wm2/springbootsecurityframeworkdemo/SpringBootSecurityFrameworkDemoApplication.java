package az.edu.ada.wm2.springbootsecurityframeworkdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "az.edu.ada.wm2.springbootsecurityframeworkdemo")  // Adjust this to the root package where `SecurityConfig` is located if necessary
public class SpringBootSecurityFrameworkDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityFrameworkDemoApplication.class, args);
	}
}
