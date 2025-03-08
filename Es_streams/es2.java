import java.util.*;

public class es2 {
    public static ImmutablePair<Integer, Double> someCalculation(List<Double> lst) {
        Integer x = (int) lst.stream()
                             .filter(e -> e >= 0 && e <= Math.PI)
                             .count();

        Double y = lst.stream()
                      .filter(e -> e >= 10 && e <= 100)
                      .mapToDouble(d -> d)
                      .average()
                      .orElse(-1);

        return new ImmutablePair<Integer,Double>(x, y);
    }
    public static void main(String[] args) {
        Double v[] = {1.0, 2.0, 3.0, 4.0};
        List<Double> l = new ArrayList<>(Arrays.asList(v));

        ImmutablePair<Integer, Double> p = someCalculation(l);

        System.out.println(p.getX());
        System.out.println(p.getY());
    }
}
