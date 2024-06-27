import ap.xml.XMLfield;

public class Course {
    @XMLfield(type = "String", name = "Course title")
    private String name;

    @XMLfield(type = "String")
    private String code;

    @XMLfield(type = "int")
    private float credits;

    public Course(String name, String code, float credits) {
        this.name = name;
        this.code = code;
        this.credits = credits;
    }

    public String getName() { return name; }
    public String getCode() { return code; }
    public float getCredits() { return credits; }

}

