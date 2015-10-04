package de.solugo.scrumpoker.rest.controller;

import de.solugo.scrumpoker.data.entity.Participant;
import de.solugo.scrumpoker.data.repository.ParticipantRepository;
import de.solugo.scrumpoker.rest.resource.ParticipantResource;
import de.solugo.scrumpoker.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/games/{gameId}/participants")
public class ParticipantController extends BaseController {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private BeanMapper beanMapper;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public List<ParticipantResource> listAllParticipants(@PathVariable final Long gameId) {
        return beanMapper.map(participantRepository.findAllByGame(gameId), ParticipantResource.class);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{participantId}/selection", method = RequestMethod.GET)
    public Integer findParticipantSelection(@PathVariable final Long gameId, @PathVariable final Long participantId) {
        return participantRepository.findByGameAndId(gameId, participantId).getSelection();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{participantId}/selection", method = RequestMethod.POST)
    public ParticipantResource saveParticipantSelection(@PathVariable final Long gameId, @PathVariable final Long participantId, @RequestBody final Integer selection) {
        final Participant participant = participantRepository.findByGameAndId(gameId, participantId);
        participant.setSelection(selection);

        return beanMapper.map(participantRepository.save(participant), ParticipantResource.class);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/self", method = RequestMethod.GET)
    public ParticipantResource findLoggedInParticipants(@PathVariable final Long gameId) {
        return beanMapper.map(participantRepository.findByGameAndAccountId(gameId, getCurrentAccountId()), ParticipantResource.class);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/self", method = RequestMethod.POST)
    public ParticipantResource addLoggedInParticipant(@PathVariable final Long gameId, @RequestBody final ParticipantResource participantResource) {
        return beanMapper.map(participantRepository.addParticipantToGame(gameId, getCurrentAccountId(), beanMapper.map(participantResource, Participant.class)), ParticipantResource.class);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/self/selection", method = RequestMethod.GET)
    public Integer findLoggedInParticipantSelection(@PathVariable final Long gameId) {
        final Participant participant = participantRepository.findByGameAndAccountId(gameId, getCurrentAccountId());
        return participant.getSelection();
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/self/selection", method = RequestMethod.POST)
    public ParticipantResource saveParticipantSelection(@PathVariable final Long gameId, @AuthenticationPrincipal final String email, @RequestBody final Integer selection) {
        final Participant participant = participantRepository.findByGameAndAccountId(gameId, getCurrentAccountId());
        participant.setSelection(selection);

        return beanMapper.map(participantRepository.save(participant), ParticipantResource.class);
    }

}
