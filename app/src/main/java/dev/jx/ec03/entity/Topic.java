package dev.jx.ec03.entity;

public class Topic {

    private int id;
    private String name;
    private String imageUrl;
    private int readingMinutes;

    public Topic() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReadingMinutes() {
        return readingMinutes;
    }

    public void setReadingMinutes(int readingMinutes) {
        this.readingMinutes = readingMinutes;
    }
}
