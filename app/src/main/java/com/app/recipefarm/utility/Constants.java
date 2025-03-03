package com.app.recipefarm.utility;

public class Constants {
    // shared prefs key
    public static String USERID = "userId";

    //error message
    public static String GENERIC_ERROR_MSG = "Something went wrong.\n Please try again later";


    //firebase storage folder names
    public static String RECIPE_IMAGES = "recipeImages";
    public static String PROFILE_IMAGES = "profileImages";


    //url
    public static String BASEURL = "https://recipefarm.88899900.xyz/";

    //user endpoints
    public static String loginUserEndpoint = BASEURL + "user/login";
    public static String registerUserEndpoint = BASEURL + "user/register";
    public static String updateUserEndpoint = BASEURL + "user/update";
    public static String detailUserEndpoint = BASEURL + "user/detail";

    //recipe endpoints
    public static String createRecipeEndpoint = BASEURL + "recipe/create";
    public static String updateRecipeEndpoint = BASEURL + "recipe/update";
    public static String deleteRecipeEndpoint = BASEURL + "recipe/delete";
    public static String searchRecipeEndpoint = BASEURL + "recipe/search";
    public static String latestRecipeEndpoint = BASEURL + "recipe/latest";
    public static String bookmarkRecipeEndpoint = BASEURL + "recipe/bookmark";
    public static String userRecipeEndpoint = BASEURL + "recipe/user";
    public static String detailRecipeEndpoint = BASEURL + "recipe/detail";

    //bookmark endpoints
    public static String createBookmarkEndpoint = BASEURL + "bookmark/create";
    public static String deleteBookmarkEndpoint = BASEURL + "bookmark/delete";
    public static String getBookmarkEndpoint = BASEURL + "bookmark/getbookmark";



}
