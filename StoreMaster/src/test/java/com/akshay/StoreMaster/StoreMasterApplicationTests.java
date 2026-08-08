package com.akshay.StoreMaster;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StoreMasterApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@MockitoBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

}
