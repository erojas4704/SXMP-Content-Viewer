package com.eddiejrojas.SXMproject.content;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class ContentController {
    private final ContentRepository repository;

    ContentController(ContentRepository repository){
        this.repository = repository;
    }

    @GetMapping("/content")
    List<Content> all(){
        return repository.findAll();
    }

    @PostMapping("/content")
    Content newContent(@RequestBody Content newContent){
        return repository.save(newContent);
    }

    @GetMapping("/content/{id}")
    Content one(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(id));
    }

    @PutMapping("/content/{id}")
    Content replaceContent(@RequestBody Content newContent, @PathVariable Long id){
        return repository.findById(id)
                .map(content -> {
                    content.setTitle(newContent.getTitle());
                    content.setDescription(newContent.getDescription());
                    content.setAudioURL(newContent.getAudioURL());
                    content.setSource(newContent.getSource());
                    content.setImageURL(newContent.getImageURL());
                    return repository.save(content);
                })
                .orElseGet(() -> {
                    newContent.setId(id);
                    return repository.save(newContent);
                });
    }

    @DeleteMapping("/content/{id}")
    public void deleteContent(@PathVariable Long id){
        repository.deleteById(id);
    }
}
