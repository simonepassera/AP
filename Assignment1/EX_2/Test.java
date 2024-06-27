import ap.xml.XMLSerializer;

public class Test {
    public static void main(String[] args) {
        Object[] array = {
            new Student("Simone", "Passèra", 25),
            new String("non-XMLable class"),
            new Student("Mario", "Rossi", 28),
            new Course("Advanced Programming", "301AA", 9),
            new Student("Luca", "Verdi", 35)
        };

        XMLSerializer.serialize(array, "target/test.xml");
    }
}
