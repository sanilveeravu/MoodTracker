package com.openclassrooms.moodtracker.model;

import junit.framework.TestCase;

import org.junit.Test;
import com.openclassrooms.moodtracker.R;
import java.util.HashMap;

public class ImageMapTest extends TestCase {

    @Test
    public void testImageToColorMapping() {
        ImageMap imageMap = new ImageMap();

        HashMap<Integer, Integer> expectedImageToColor = new HashMap<>();
        expectedImageToColor.put(R.drawable.ic_smiley_happy, R.color.light_sage);
        expectedImageToColor.put(R.drawable.ic_smiley_super_happy, R.color.banana_yellow);
        expectedImageToColor.put(R.drawable.ic_smiley_sad, R.color.faded_red);
        expectedImageToColor.put(R.drawable.ic_smiley_disappointed, R.color.warm_grey);
        expectedImageToColor.put(R.drawable.ic_smiley_normal, R.color.cornflower_blue_65);
        expectedImageToColor.put(0, R.color.light_grey);

        assertEquals(expectedImageToColor, imageMap.getImageToColor());
    }

    @Test
    public void testImageToWidthMapping() {
        ImageMap imageMap = new ImageMap();

        HashMap<Integer, Integer> expectedImageToWidth = new HashMap<>();
        expectedImageToWidth.put(R.drawable.ic_smiley_happy, 710);
        expectedImageToWidth.put(R.drawable.ic_smiley_super_happy, 925);
        expectedImageToWidth.put(R.drawable.ic_smiley_sad, 130);
        expectedImageToWidth.put(R.drawable.ic_smiley_disappointed, 280);
        expectedImageToWidth.put(R.drawable.ic_smiley_normal, 495);
        expectedImageToWidth.put(0, 495);

        assertEquals(expectedImageToWidth, imageMap.getImageToWidth());
    }
}