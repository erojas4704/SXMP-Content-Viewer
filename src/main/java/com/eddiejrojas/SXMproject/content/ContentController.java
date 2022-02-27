package com.eddiejrojas.SXMproject.content;

import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class ContentController {
    private final ContentRepository repository;
    private final ContentModelAssembler assembler;

    ContentController(ContentRepository repository, ContentModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/content")
    CollectionModel<EntityModel<Content>> all() {
        List<EntityModel<Content>> content = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(content,
                linkTo(methodOn(ContentController.class).all()).withSelfRel());
    }

    @GetMapping("/content/{id}")
    EntityModel<Content> one(@PathVariable Long id){
        Content content = repository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(id));

        return assembler.toModel(content);
    }

    @PostMapping("/content")
    ResponseEntity<?> newContent(@RequestBody Content newContent){
        EntityModel<Content> entityModel = assembler.toModel(repository.save(newContent));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/content/{id}")
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

    @DeleteMapping("/content/{id}")
    ResponseEntity<?> deleteContent(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
