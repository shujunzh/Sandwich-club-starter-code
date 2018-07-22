package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView sandwichIv;
    private TextView alsoKnownAs_labelTv;
    private TextView alsoKnownAsTv;
    private TextView placeOfOrigin_labelTv;
    private TextView placeOfOriginTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;
    Sandwich sandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sandwichIv = (ImageView)findViewById(R.id.image_iv);
        alsoKnownAsTv = (TextView)findViewById(R.id.also_known_tv);
        alsoKnownAs_labelTv = (TextView)findViewById(R.id.also_known_as_label);
        placeOfOriginTv = (TextView)findViewById(R.id.origin_tv);
        placeOfOrigin_labelTv = (TextView)findViewById(R.id.origin_label);
        ingredientsTv = (TextView)findViewById(R.id.ingredients_tv);
        descriptionTv = (TextView)findViewById(R.id.description_tv);




        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }


        try {
            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichIv);

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOrigin_labelTv.setVisibility(View.GONE);
            placeOfOriginTv.setVisibility(View.GONE);
        } else {
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAs_labelTv.setVisibility(View.GONE);
            alsoKnownAsTv.setVisibility(View.GONE);
        } else {
            String alsoKnownAs_str = TextUtils.join(" , ", sandwich.getAlsoKnownAs());
            alsoKnownAsTv.setText(alsoKnownAs_str);
        }
        descriptionTv.setText(sandwich.getDescription());
        ingredientsTv.setText(TextUtils.join(", ", sandwich.getIngredients()));




    }
}
