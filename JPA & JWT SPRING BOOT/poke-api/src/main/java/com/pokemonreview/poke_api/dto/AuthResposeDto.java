package com.pokemonreview.poke_api.dto;

import lombok.Data;

@Data
public class AuthResposeDto {
    private String accestoken;
    private String tokenType = "Bearer";

    public AuthResposeDto(String accestoken) {
        this.accestoken = accestoken;
    }
}
