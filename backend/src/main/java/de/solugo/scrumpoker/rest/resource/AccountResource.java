package de.solugo.scrumpoker.rest.resource;

import lombok.Data;

@Data
public class AccountResource extends BaseResource {
    private String name;
    private String email;
    private String role;
    private String password;
}
