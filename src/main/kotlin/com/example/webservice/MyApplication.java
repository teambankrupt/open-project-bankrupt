package com.example.webservice;

import com.example.webservice.commons.utils.PasswordUtil;
import com.example.webservice.services.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class MyApplication {
	private final CustomUserDetailsService userDetailsService;

	@Autowired
	public MyApplication(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}


	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(PasswordUtil.getBCryptPasswordEncoder());
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+06:00"));
		System.out.println("====== Timezone set to Dhaka ======");
	}

}
