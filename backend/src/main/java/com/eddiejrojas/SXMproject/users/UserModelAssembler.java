package com.eddiejrojas.SXMproject.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class UserModelAssembler implements RepresentationModelAssembler<UserProfile, EntityModel<UserProfile>> {

    @Override
    public EntityModel<UserProfile> toModel(UserProfile user){
        return EntityModel.of(user);
    }
}
