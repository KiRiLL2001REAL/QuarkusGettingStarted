package edu.my.rest;

import edu.my.entities.IPlayer;
import edu.my.entities.annotations.AMp3;
import edu.my.entities.annotations.AWave;
import edu.my.service.PlayerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/play")
public class PlayerResource {
    @Inject @AMp3
    IPlayer mp3Player;

    @Inject @AWave
    IPlayer wavePlayer;

    @Inject
    PlayerService playerService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/mp3")
    public String playMp3() {
        return playerService.playSound(mp3Player.typeOfPlayingFile());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/wave")
    public String playWave() {
        return playerService.playSound(wavePlayer.typeOfPlayingFile());
    }
}
