package com.app.recipefarm.models.base;

import static com.app.recipefarm.utility.Constants.RECIPE_IMAGES;

import java.util.Date;

public class Recipe {
    public String recipeId;
    public String title;
    public String description;

    // in minutes
    public int duration;
    public int servings;

    // in stringify json list
    public String ingredients;

    // in stringify json list
    public String instructions;
    public String recipeImage;
    public String timestamp;
    public String userId;
    public int likes;

    public String getDuration() {
        if (duration <= 0) {
            return "0min";
        }

        int hours = duration / 60;
        int minutes = duration % 60;

        StringBuilder durationString = new StringBuilder();

        if (hours > 0) {
            durationString.append(hours).append("h");
        }
        if (minutes > 0) {
            if (hours > 0) {
                durationString.append(" ");
            }
            durationString.append(minutes).append("min");
        }

        return durationString.toString();
    }

    public String getImagePath() {
        return RECIPE_IMAGES + "/" + recipeImage;
    }
}
