package com.example.trungspc.musicapp.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TopSongResponse {

    Feed feed;

    public TopSongResponse(Feed feed) {
        this.feed = feed;
    }

    public Feed getFeed() {
        return feed;
    }

    public class Feed {
        List<Entry> entry;

        public Feed(ArrayList<Entry> entry) {
            this.entry = entry;
        }

        public List<Entry> getEntry() {
            return entry;
        }

        public class Entry {
            @SerializedName("im:name")
            Name name;
            @SerializedName("im:image")
            List<Image> images;
            @SerializedName("im:artist")
            Artist artist;

            public Entry(Name name, ArrayList<Image> images, Artist artist) {
                this.name = name;
                this.images = images;
                this.artist = artist;
            }

            public Name getName() {
                return name;
            }

            public List<Image> getImages() {
                return images;
            }

            public Artist getArtist() {
                return artist;
            }

            public class Name {
                String label;

                public Name(String label) {
                    this.label = label;
                }

                public String getLabel() {
                    return label;
                }
            }

            public class Image {
                String label;

                public Image(String label) {
                    this.label = label;
                }

                public String getLabel() {
                    return label;
                }
            }

            public class Artist {
                String label;

                public Artist(String label) {
                    this.label = label;
                }

                public String getLabel() {
                    return label;
                }
            }

        }
    }
}
