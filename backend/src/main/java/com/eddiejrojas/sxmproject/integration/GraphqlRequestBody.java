package com.eddiejrojas.sxmproject.integration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GraphqlRequestBody {
    private String query;
    private Object variables;
}
