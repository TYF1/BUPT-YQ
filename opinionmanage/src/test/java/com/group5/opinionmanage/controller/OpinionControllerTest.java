package com.group5.opinionmanage.controller;

import com.group5.opinionmanage.OpinionmanageApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpinionmanageApplication.class)
@WebAppConfiguration
public class OpinionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception{
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void getForm() throws Exception{
        String responseString=mockMvc.perform(MockMvcRequestBuilders.get("/formGet?limit=10&page=1")
                .contentType((MediaType.APPLICATION_JSON_UTF8))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);


    }

    @Test
    public void findCondition() throws Exception {
        String responseString=mockMvc.perform(MockMvcRequestBuilders.get("/ConditionSelect?conditionSelect=ctx&conditionInput=日本&page=1&limit=10")
                        .contentType((MediaType.APPLICATION_JSON_UTF8))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
        String responseString2=mockMvc.perform(MockMvcRequestBuilders.get("/ConditionSelect?conditionSelect=kw&conditionInput=日本&page=1&limit=10")
                        .contentType((MediaType.APPLICATION_JSON_UTF8))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString2);
        String responseString3=mockMvc.perform(MockMvcRequestBuilders.get("/ConditionSelect?conditionSelect=ft&conditionInput=积极&page=1&limit=10")
                        .contentType((MediaType.APPLICATION_JSON_UTF8))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString3);
    }
}