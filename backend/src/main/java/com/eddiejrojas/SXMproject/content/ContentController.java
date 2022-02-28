package com.eddiejrojas.SXMproject.content;

import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content")
class ContentController {
    private final ContentRepository repository;
    private final ContentModelAssembler assembler;

    ContentController(ContentRepository repository, ContentModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("")
    List<Content> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Content one(@PathVariable Long id){
        Content content = repository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(id));

        return content;
    }

    @PostMapping("")
    ResponseEntity<?> newContent(@RequestBody Content newContent){
        EntityModel<Content> entityModel = assembler.toModel(repository.save(newContent));
        return ResponseEntity
                .created(URI.create("")) //TODO reconsider our links
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceContent(@RequestBody Content newContent, @PathVariable Long id){
        Content updatedContent = repository.findById(id)
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

        EntityModel<Content> entityModel = assembler.toModel(updatedContent);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteContent(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
