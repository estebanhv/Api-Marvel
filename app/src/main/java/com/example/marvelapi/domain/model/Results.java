package com.example.marvelapi.domain.model;

import java.util.ArrayList;


public class Results {
    private float id;
    private String name;
    private String description;
    private String modified;
    Thumbnail thumbnail;
    private String resourceURI;

    ArrayList<Object> urls = new ArrayList<Object>();


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModified() {
        return modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }



    // Setter Methods

    public void setId( float id ) {
        this.id = id;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public void setModified( String modified ) {
        this.modified = modified;
    }

    public void setThumbnail( Thumbnail thumbnailObject ) {
        this.thumbnail = thumbnailObject;
    }

    public void setResourceURI( String resourceURI ) {
        this.resourceURI = resourceURI;
    }


}

