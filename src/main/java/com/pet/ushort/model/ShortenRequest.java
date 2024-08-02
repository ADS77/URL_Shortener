package com.pet.ushort.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class ShortenRequest {
    @NotEmpty(message = "URL cannot be empty")
    private String url;
    @JsonCreator
    public ShortenRequest() {
    }

    @JsonCreator
    public ShortenRequest(String url) {
        this.url = url;
    }
}
