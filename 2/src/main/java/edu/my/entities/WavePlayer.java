package edu.my.entities;

import edu.my.entities.annotations.AWave;
import jakarta.enterprise.context.Dependent;

@Dependent
@AWave
public class WavePlayer implements IPlayer {
    @Override
    public String typeOfPlayingFile() {
        return "Wave";
    }
}
