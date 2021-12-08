/*
 * Copyright (c) 2022 mwhyte.dev
 *
 * You may study, use, and modify this example. Redistribution is not permitted.
 */

package com.codenerve.spring.sec;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
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
}
