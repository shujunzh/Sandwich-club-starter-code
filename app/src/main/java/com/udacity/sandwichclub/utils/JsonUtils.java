package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        final String MAINNAME = "mainName";
        final String ALSOKNOWNAS = "alsoKnownAs";
        final String PLACEOFORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";
        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        List<String> sandwichIngredients = null;
        List<String> sandwichAlsoKnownAs = null;
        String image = null;
        Sandwich sandwich;
        try {
            JSONObject sandwichDetail = new JSONObject(json);
            JSONObject name = sandwichDetail.getJSONObject("name");

            mainName = name.getString(MAINNAME);
            placeOfOrigin =sandwichDetail.getString(PLACEOFORIGIN);
            description = sandwichDetail.getString(DESCRIPTION);

            image = sandwichDetail.getString(IMAGE);
            JSONArray ingredients = sandwichDetail.getJSONArray(INGREDIENTS);
            sandwichIngredients = new ArrayList<>();
            if (ingredients.length() != 0) {
                for (int i = 0; i < ingredients.length(); i++) {
                    sandwichIngredients.add(ingredients.getString(i));
                }
            }

            JSONArray alsoKnownAs = name.getJSONArray(ALSOKNOWNAS);
            sandwichAlsoKnownAs = new ArrayList<>();
            if (alsoKnownAs.length() != 0) {
                for (int i = 0; i < alsoKnownAs.length(); i++) {
                    sandwichAlsoKnownAs.add(alsoKnownAs.getString(i));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        sandwich = new Sandwich(mainName, sandwichAlsoKnownAs, placeOfOrigin, description, image, sandwichIngredients);

       return sandwich;


    }
}
