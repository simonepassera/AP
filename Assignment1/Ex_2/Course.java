import ap.xml.XMLable;
import ap.xml.XMLfield;

@XMLable
public class Course {
    @XMLfield(type = "String", name = "CourseName")
    private String name;

    @XMLfield(type = "String")
    private String code;

    @XMLfield(type = "float")
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

