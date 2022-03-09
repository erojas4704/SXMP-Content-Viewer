package com.eddiejrojas.SXMproject.api;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDataTransferObject {
        private String accessToken;

        @SuppressWarnings("unchecked")
        @JsonProperty("data")
        private void unpackNested(Map<String, Object> data) {
                Map<String, String> requestAccessToken = (Map<String, String>) data.get("requestAccessToken");
                this.accessToken = requestAccessToken.get("access_token");
        }
}
