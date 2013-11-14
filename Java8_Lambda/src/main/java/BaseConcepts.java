import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/25/13
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseConcepts {

    // High order function
    public static Function<String, String> twice( Function<String, String> f ) {
        return (String s) -> f.apply( f.apply(s) );
    }

    // Functor - map
    public static List<String> map( Function<String, String> f, List<String> values ) {
        List<String> toReturn = new ArrayList<>();
        for( String current : values ) {
            toReturn.add( f.apply(current) );
        }
        return toReturn;
    }

    // Functor - filter
    public static List<String> filter( Function<String, Boolean> f, List<String> values ) {
        List<String> toReturn = new ArrayList<>();
        for( String current : values ) {
            if ( f.apply(current) )
                toReturn.add( current );
        }
        return toReturn;
    }

    // Combinator (Null Combinator)
    public static Function<String, String> nullCheck( Function<String, String> f ) {
        return (String s) -> s == null ? "null" : f.apply(s);
    }

    // Compose
    public static Function<String, String> compose ( Function<String, String> f1, Function<String, String> f2  ) {
       return (String s) -> f1.apply( f2.apply(s) );
    }

    public static void main( String[] args ) {

        // First Class  Function
        System.out.println( "//=> First Class  Function" );
        Function<String, String> hello = (String s) -> "hello " + s;
        System.out.println(hello);
        System.out.println( new BaseConcepts() );
        System.out.println(hello.apply("Erouan"));

        // High order function
        System.out.println( "\n//=> High order function" );
        System.out.println( twice(hello) );
        System.out.println( twice(hello).apply("Erouan") );

        // Functor: map
        System.out.println( "\n//=> Functor: map" );
        List<String> confs = Arrays.asList( new String[]{"jug", "devoxx", "javaone"} );
        System.out.println( map(s -> s.toUpperCase(), confs) );
        System.out.println( confs.stream().map(s -> s.toUpperCase()).collect(Collectors.toList()) );

        // Functor: filter
        System.out.println( "\n//=> Functor: filter" );
        System.out.println( filter(s -> s.contains("j"), confs) );
        System.out.println( confs.stream().filter( s -> s.contains("j") ).collect( Collectors.toList() ) );

        // Combinator
        System.out.println( "\n//=> Combinator" );
        List<String> confs2 = Arrays.asList( new String[]{"jug", "devoxx", "javaone", null} );
        //System.out.println( map( s -> s.toUpperCase(), confs2) );
        System.out.println( map( nullCheck(s -> s.toUpperCase()), confs2) );
        System.out.println( confs2.stream().map( nullCheck(s -> s.toUpperCase()) ).collect( Collectors.toList() ) );

        // Composition
        System.out.println( "\n//=> Composition" );
        Function<String, String> up = (String s) -> s.toUpperCase();
        System.out.println( compose( up, hello).apply("Erouan") );
        System.out.println( up.apply( hello.apply("Erouan") ));

    }

}
