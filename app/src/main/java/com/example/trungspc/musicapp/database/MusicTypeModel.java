package com.example.trungspc.musicapp.database;

import java.io.Serializable;

public class MusicTypeModel implements Serializable{
    private String id;
    private String name;
    private int imageID;

    public MusicTypeModel(String id, String name, int imageID) {
        this.id = id;
        this.name = name;
        this.imageID = imageID;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageID() {
        return imageID;
    }
}
