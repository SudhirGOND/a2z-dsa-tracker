package com.a2zdsatracker.model;

public class Problem {

    // JSON se aane wale fields (naam JSON ke keys se exact match hone chahiye)
    private String id;
    private String topic;
    private String category;
    private String title;
    private String articleLink;
    private String gfgLink;
    private String leetCodeLink;
    private String youTubeLink;
    private String difficulty;

    // Runtime state (JSON mein nahi hai)
    private transient boolean solved;
    private transient String notes = "";

    public String getId() { return id; }
    public String getTopic() { return topic; }
    public String getCategory() { return category; }
    public String getTitle() { return title; }
    public String getArticleLink() { return articleLink; }
    public String getGfgLink() { return gfgLink; }
    public String getLeetCodeLink() { return leetCodeLink; }
    public String getYouTubeLink() { return youTubeLink; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public boolean isSolved() { return solved; }
    public void setSolved(boolean solved) { this.solved = solved; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}