package de.solugo.scrumpoker.rest.controller;

import de.solugo.scrumpoker.data.entity.Account;
import de.solugo.scrumpoker.data.entity.Game;
import de.solugo.scrumpoker.data.repository.AccountRepository;
import de.solugo.scrumpoker.data.repository.GameRepository;
import de.solugo.scrumpoker.rest.resource.GameResource;
import de.solugo.scrumpoker.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/games")
public class GameController extends BaseController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BeanMapper beanMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<GameResource> listAllGames() {
        return beanMapper.map(gameRepository.findAll(), GameResource.class);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.POST)
    public GameResource addGame(@RequestBody final GameResource gameResource) {
        return beanMapper.map(gameRepository.createGame(getCurrentAccount().getId(), beanMapper.map(gameResource, Game.class)), GameResource.class);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public GameResource findGame(@PathVariable final Long gameId) {
        return beanMapper.map(gameRepository.findById(gameId), GameResource.class);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{gameId}", method = RequestMethod.POST)
    public GameResource saveGame(@PathVariable final Long gameId, @RequestBody final GameResource gameResource) {
        return beanMapper.map(gameRepository.merge(gameId, gameResource), GameResource.class);
    }


}
