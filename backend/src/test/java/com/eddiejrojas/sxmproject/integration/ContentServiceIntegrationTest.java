package com.eddiejrojas.sxmproject.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.eddiejrojas.sxmproject.model.Content;
import com.eddiejrojas.sxmproject.repository.ContentRepository;
import com.eddiejrojas.sxmproject.service.ContentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

@SpringBootTest
public class ContentServiceIntegrationTest {
    @Autowired private ContentService contentService;
    @Autowired private ContentRepository contentRepository;

    @BeforeEach
    public void setUp() {
        contentRepository.deleteAll();

        Content content = new Content();
        content.setName("Test");
        content.setDescription("Test Description");
        content.setTitle("Test Title");
        content.setImageUrl("cdn://test.img.jpg");
        content.setAudioUrl("cdn://test.img.jpg");
        content.setSource("cdn://test.img.jpg");
        content.setId(1L);

        contentRepository.save(content);
    }

    @Test
    public void getContentTest() {
        Content retrievedContent = contentService.one(null, 1L);
        assertNotNull(retrievedContent);
        assertEquals("Test Title", retrievedContent.getTitle());
    }

    @Test
    @Transactional
    public void deleteContentTest() {
        Content contentToDelete = Content.builder().id(5L).title("Delete Me").build();

        contentRepository.save(contentToDelete);
        assertNotNull(contentRepository.getReferenceById(5L));

        contentService.delete(5L);
        assertThrows(
                JpaObjectRetrievalFailureException.class,
                () -> contentRepository.getReferenceById(5L));
    }

    @Test
    @Transactional
    public void updateContentTest() {
        Content content = contentRepository.getReferenceById(1L);
        content.setTitle("New Title");
        contentService.replaceContent(content, 1L);

        Content updatedContent = contentService.one(null, 1L);
        assertEquals("New Title", updatedContent.getTitle());
    }
}
