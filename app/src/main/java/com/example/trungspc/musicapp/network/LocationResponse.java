package com.example.trungspc.musicapp.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "tracklist", strict = false)
public class LocationResponse {
    @Element(name = "track")
    public TrackXML track;
}
