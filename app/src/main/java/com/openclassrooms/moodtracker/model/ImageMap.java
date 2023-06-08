package com.openclassrooms.moodtracker.model;

import com.openclassrooms.moodtracker.R;

import java.util.HashMap;

public class ImageMap {

    HashMap<Integer, Integer> imageToColor = new HashMap<Integer, Integer>();

    HashMap<Integer, Integer> imageToWidth = new HashMap<Integer, Integer>();

    public ImageMap() {
        imageToColor.put(R.drawable.ic_smiley_happy, R.color.light_sage);
        imageToColor.put(R.drawable.ic_smiley_super_happy, R.color.banana_yellow);
        imageToColor.put(R.drawable.ic_smiley_sad, R.color.faded_red);
        imageToColor.put(R.drawable.ic_smiley_disappointed, R.color.warm_grey);
        imageToColor.put(R.drawable.ic_smiley_normal, R.color.cornflower_blue_65);
        imageToColor.put(0, R.color.light_grey);

        imageToWidth.put(R.drawable.ic_smiley_happy, 860);
        imageToWidth.put(R.drawable.ic_smiley_super_happy, 1075);
        imageToWidth.put(R.drawable.ic_smiley_sad, 215);
        imageToWidth.put(R.drawable.ic_smiley_disappointed, 430);
        imageToWidth.put(R.drawable.ic_smiley_normal, 645);
        imageToWidth.put(0, 645);

    }

    public HashMap<Integer, Integer> getImageToWidth() {
        return imageToWidth;
    }

    public void setImageToWidth(HashMap<Integer, Integer> imageToWidth) {
        this.imageToWidth = imageToWidth;
    }

    public HashMap<Integer, Integer> getImageToColor() {
        return imageToColor;
    }

    public void setImageToColor(HashMap<Integer, Integer> imageToColor) {
        this.imageToColor = imageToColor;
    }
}
