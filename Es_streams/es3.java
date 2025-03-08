import java.util.*;
import java.util.stream.*;

public class es3 {
    public static Object[] repl(Object[] xs, Integer n) {
        Object[] res = Arrays.stream(xs)
                             .flatMap(u -> Stream.of(u,u))
                             ......

        return res;
    }
    public static void main(String[] args) {
        Integer v[] = {1, 2, 3, 4};
        List<Integer> l = new ArrayList<>(Arrays.asList((Integer[]) repl(v, 2)));

        System.out.println(l);
    }
}
