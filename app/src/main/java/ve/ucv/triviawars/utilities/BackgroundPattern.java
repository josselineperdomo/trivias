package ve.ucv.triviawars.utilities;


import java.util.Random;

import ve.ucv.triviawars.R;

public enum  BackgroundPattern {
    PATTERN_1(R.drawable.bg_pattern_1),
    PATTERN_2(R.drawable.bg_pattern_2),
    PATTERN_3(R.drawable.bg_pattern_3),
    PATTERN_4(R.drawable.bg_pattern_4),
    PATTERN_5(R.drawable.bg_pattern_5),
    PATTERN_6(R.drawable.bg_pattern_6);


    private int patternId;

    BackgroundPattern(int patternId) {
        this.patternId = patternId;
    }

    public int getPatternId() {
        return this.patternId;
    }

    public static BackgroundPattern getRandomPattern() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
