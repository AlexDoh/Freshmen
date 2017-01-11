package Freshmen;

public class Student extends Human {

    private int activities = 0;
    private int popularityLevel = 0;
    private int ratingLevelFromSchool = 0;
    private int commonRating = 0;

    public Student(String name, String surname, int activities, int popularityLevel, int ratingLevelFromSchool) {
        super(name, surname);
        this.activities = activities;
        this.popularityLevel = popularityLevel;
        this.ratingLevelFromSchool = ratingLevelFromSchool;
        commonRating = activities + popularityLevel + ratingLevelFromSchool;
    }

    public int getCommonRating() {
        return commonRating;
    }
}
