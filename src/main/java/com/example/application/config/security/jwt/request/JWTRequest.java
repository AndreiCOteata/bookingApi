package com.example.application.config.security.jwt.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTRequest implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 178329873198173L;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

}
