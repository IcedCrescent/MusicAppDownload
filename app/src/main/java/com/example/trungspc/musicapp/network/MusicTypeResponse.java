package com.example.trungspc.musicapp.network;

import java.util.ArrayList;
import java.util.List;

public class MusicTypeResponse {
    List<Subgenre> subgenres;

    public MusicTypeResponse() {
        subgenres = new ArrayList<>();
    }

    public List<Subgenre> getSubgenres() {
        return subgenres;
    }

    public class Subgenre {
        private String id;
        private String translation_key;

        public Subgenre(String id, String translation_key) {
            this.id = id;
            this.translation_key = translation_key;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTranslation_key() {
            return translation_key;
        }

        public void setTranslation_key(String translation_key) {
            this.translation_key = translation_key;
        }

        @Override
        public String toString() {
            return "Subgenre{" +
                    "id='" + id + '\'' +
                    ", translation_key='" + translation_key + '\'' +
                    '}';
        }
    }
}
