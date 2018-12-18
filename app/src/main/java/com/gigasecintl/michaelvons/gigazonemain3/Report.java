package com.gigasecintl.michaelvons.gigazonemain3;

/**
 * Created by mike2 on 3/17/2017.
 */

public class Report {



    public String image;
    public String title;
    public String type;
    public String phonenumber;
    public String description;
    public Long timestamp;
    public String relativeTime;
    public Double latitude;
    public Double longitude;

    public Report() {
    }

    public Report(String image, String title, String type, String phonenumber, String description, Long timestamp, Double latitude, Double longitude) {

        this.image = image;
        this.title = title;
        this.type = type;
        this.phonenumber = phonenumber;
        this.description = description;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(String relativeTime) {
        this.relativeTime = relativeTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
