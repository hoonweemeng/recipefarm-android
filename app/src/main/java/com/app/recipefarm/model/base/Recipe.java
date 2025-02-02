package com.app.recipefarm.model.base;

import static com.app.recipefarm.utility.Constants.RECIPE_IMAGES;
import static com.app.recipefarm.utility.RFFunctions.jsonToList;
import static com.app.recipefarm.utility.RFFunctions.listToJson;

import java.util.ArrayList;

public class Recipe {
    public String recipeId = null;
    public String title = null;
    public String description = null;

    // in minutes
    public int duration = 0;
    public int servings = 0;

    // in stringify json list
    public String ingredients = null;

    // in stringify json list
    public String instructions = null;
    public String recipeImage = null;
    public String recipeImageExt = null;
    //public String timestamp  = null;
    public String userId = null;
    public int likes = 0;

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
        return RECIPE_IMAGES + "/" + recipeImage + "." + recipeImageExt;
    }
}
