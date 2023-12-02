package com.scalable.payment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateNewDefaultPaymentEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payment/create-default")
                        .param("username", "Andy"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateNewPaymentEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payment/create")
                        .param("username", "Pong")
                        .param("credits", "50"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetBalanceEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payment/create")
                        .param("username", "Sai")
                        .param("credits", "1000000"));

        mockMvc.perform(MockMvcRequestBuilders.get("/payment/balance")
                        .param("username", "Sai")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1000000.0"));
    }
}
