package edu.my.entities;

import edu.my.entities.annotations.AMp3;
import jakarta.enterprise.context.Dependent;

@Dependent
@AMp3
public class MP3Player implements IPlayer {
    @Override
    public String typeOfPlayingFile() {
        return "MP3";
    }
}
