package com.pet.ushort.controller;

import com.pet.ushort.model.ShortenRequest;
import com.pet.ushort.service.manager.UrlManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(UrlController.class)
public class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlManager urlManager;

    private ShortenRequest shortUrl;
    private String originalUrl;
    private String shortKey;

    @BeforeEach
    public void setUp() {
        originalUrl = "http://example.com";
        shortKey = "abc123";
        shortUrl = new ShortenRequest();
        shortUrl.setUrl(originalUrl);;
        // Mock the behavior of urlManager
    }

    @Test
    public void testShortenUrl() throws Exception {
        mockMvc.perform(post("/urlShortener/shortenUrl")
                        .header("url", originalUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl", is(originalUrl)))
                .andExpect(jsonPath("$.shortenedUrl", is("http://short.url/" + shortKey)));
    }

    @Test
    public void testGetUrl() throws Exception {
        mockMvc.perform(get("/urlShortener/getUrl")
                        .header("key", shortKey)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(originalUrl)));
    }
}
