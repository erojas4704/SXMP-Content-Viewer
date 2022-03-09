package com.eddiejrojas.SXMproject.content;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ContentModelAssembler implements RepresentationModelAssembler<Content, EntityModel<Content>> {

    @Override
    public EntityModel<Content> toModel(Content content){
        return EntityModel.of(content,
                linkTo(methodOn(ContentController.class).one(null, content.getId())).withSelfRel(),
                linkTo(methodOn(ContentController.class).all(null)).withRel("content"));
    }
}
