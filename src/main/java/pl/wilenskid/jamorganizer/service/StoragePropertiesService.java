package pl.wilenskid.jamorganizer.service;

import javax.inject.Named;

@Named
public class StoragePropertiesService {

    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}