package com.app.recipefarm.models.base;

import static com.app.recipefarm.utility.Constants.RECIPE_IMAGES;
import static com.app.recipefarm.utility.RFFunctions.jsonToList;
import static com.app.recipefarm.utility.RFFunctions.listToJson;

import java.util.ArrayList;
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

    public Recipe() {}

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

    public ArrayList<String> getIngredients() {
        ArrayList<String> value = jsonToList(ingredients);
        if (value == null) {
            value = new ArrayList<>();
        }
        return value;
    }

    public void setIngredients(ArrayList<String> value) {
        if (value == null) {
            value = new ArrayList<>();
        }
        this.ingredients = listToJson(value);
    }

    public ArrayList<String> getInstructions() {
        ArrayList<String> value = jsonToList(instructions);
        if (value == null) {
            value = new ArrayList<>();
        }
        return value;
    }

    public void setInstructions(ArrayList<String> value) {
        if (value == null) {
            value = new ArrayList<>();
        }
        this.instructions = listToJson(value);
    }

    public String getImagePath() {
        return RECIPE_IMAGES + "/" + recipeImage;
    }
}
