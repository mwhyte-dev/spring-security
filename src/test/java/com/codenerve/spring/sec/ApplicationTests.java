/*
 * Copyright (c) 2018 codenerve.com
 *
 * You may study, use, and modify this example. Redistribution is not permitted.
 */
package com.codenerve.spring.sec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void loginWithValidUserThenAuthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
				.user("user")
				.password("pass");

		mockMvc.perform(login)
				.andExpect(authenticated().withUsername("user"));
	}

	@Test
	public void loginWithInvalidUserThenUnauthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
				.user("invalid")
				.password("invalidpassword");

		mockMvc.perform(login)
				.andExpect(unauthenticated());
	}

	@Test
	public void accessUnsecuredResourceThenOk() throws Exception {
		mockMvc.perform(get("/css/style.css"))
				.andExpect(status().isOk());
	}

	@Test
	public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser
	public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
		mockMvc.perform(get("/index"))
				.andExpect(status().isOk());
	}


	@Test
	@WithMockUser(roles = "USER")
	public void loginWithRoleUserThenExpectUserSpecificContent() throws Exception {
		mockMvc.perform(get("/index"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("This content is only shown to users.")));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void loginWithRoleAdminThenExpectAdminSpecificContent() throws Exception {
		mockMvc.perform(get("/index"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("This content is only shown to administrators.")));
	}

}
