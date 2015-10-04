package de.solugo.scrumpoker.rest.resource;

import lombok.Data;

@Data
public class ParticipantResource extends BaseResource {
    private String name;
    private Integer selection;
}
