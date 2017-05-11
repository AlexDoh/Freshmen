package freshmen.structure;

public class Student extends Human {

    private int activities = 0;
    private int popularityLevel = 0;
    private int ratingLevelFromSchool = 0;
//    private int commonRating = 0;
    private boolean isHeadOfGroup = false;

    public Student(String firstName, String lastName, int activities, int popularityLevel, int ratingLevelFromSchool) {
        super(firstName, lastName);
        this.activities = activities;
        this.popularityLevel = popularityLevel;
        this.ratingLevelFromSchool = ratingLevelFromSchool;
//        commonRating = activities + popularityLevel + ratingLevelFromSchool;
    }

    public int getCommonRating() {
        return activities + popularityLevel + ratingLevelFromSchool;
    }

    public void setHeadOfGroup(){
        this.isHeadOfGroup = true;
    }

    public boolean getHeadOfGroup(){
        return this.isHeadOfGroup;
    }
}
