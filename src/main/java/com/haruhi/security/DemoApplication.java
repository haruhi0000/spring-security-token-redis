package com.haruhi.security;

import com.haruhi.security.entity.Account;
import com.haruhi.security.repository.AccountRepository;
import com.haruhi.security.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 61711
 */
@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(AccountService accountService, AccountRepository accountRepository) {
		return (args -> {
			Iterable<Account> accounts = accountRepository.findAll();
			accounts.forEach((account2)->{
				logger.info(account2.toString());
			});
		});
	}

}
