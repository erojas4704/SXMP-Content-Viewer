package com.eddiejrojas.sxmproject;

import com.eddiejrojas.sxmproject.controller.ContentController;
import com.eddiejrojas.sxmproject.service.ContentService;
import com.eddiejrojas.sxmproject.service.exception.ContentNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ContentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContentControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContentService contentService;


    @Test
    public void testGetContentById_NotFound() throws Exception {
        Long badId = 999L;

        Mockito.when(contentService.one(null, badId)).thenThrow(new ContentNotFoundException(badId));
        mockMvc.perform(MockMvcRequestBuilders.get("/content/{id}", badId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}
