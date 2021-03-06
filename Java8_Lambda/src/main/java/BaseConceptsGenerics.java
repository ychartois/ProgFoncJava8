import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/25/13
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseConceptsGenerics<T,R> {

    // High order function
    public Function<T, T> twice( Function<T, T> f ) {
        return (T s) -> f.apply( f.apply(s) );
    }

    // Functor - map
    public List<R> map( Function<? super T, ? extends R> f, List<T> values ) {
        List<R> toReturn = new ArrayList<>();
        for( T current : values ) {
            toReturn.add( f.apply(current) );
        }
        return toReturn;
    }

    // High order function - filter
    public List<T> filter( Predicate<? super T> f, List<T> values ) {
        List<T> toReturn = new ArrayList<>();
        for( T current : values ) {
            if ( f.test(current) )
                toReturn.add( current );
        }
        return toReturn;
    }

    // Reduction: reduce
    public T reduce( BinaryOperator<T> op, List<T> values) {
        T toReturn = null;
        for( T current : values ) {
            toReturn = toReturn == null ? current : op.apply(toReturn, current);
        }
        return toReturn;
    }

    // Combinator (Null Combinator)
    public Function<T, R> nullCheck( Function<T, R> f ) {
        return s -> s == null ? null : f.apply(s);
    }

    // Compose
    public Function<T, R> compose ( Function<T, R> f1, Function<T, T> f2  ) {
       return s -> f1.apply( f2.apply(s) );
    }

    public static void main( String[] args ) {

        // Init class
        BaseConceptsGenerics<String, String> func = new BaseConceptsGenerics<>();

        // First Class  Function
        System.out.println( "//=> First Class  Function" );
        Function<String, String> hello = (String s) -> "hello " + s;
        System.out.println(hello);
        System.out.println( new BaseConceptsGenerics() );
        System.out.println( hello.apply("Erouan") );

        // High order function
        System.out.println( "\n//=> High order function" );
        System.out.println( func.twice(hello) );
        System.out.println( func.twice(hello).apply("Erouan") );

        // Functor: map
        System.out.println( "\n//=> Functor: map" );
        List<String> confs = Arrays.asList( "jug", "devoxx", "javaone" );
        System.out.println( func.map( s -> s.toUpperCase(), confs) );
        System.out.println( confs.stream().map(s -> s.toUpperCase()).collect(Collectors.toList()) );

        // Reduction: fold
        System.out.println( "\n//=> Reduction: fold" );
        System.out.println( func.reduce( (s1, s2) -> s1 + ", " + s2 , confs) );
        System.out.println(confs.stream().reduce( (s1, s2) -> s1 + ", " + s2 ).get());

        // High order function: filter
        System.out.println( "\n//=> High order function: filter" );
        System.out.println( func.filter(s -> s.contains("j"), confs) );
        System.out.println( confs.stream().filter( s -> s.contains("j") ).collect( Collectors.toList() ) );

        // Combinator
        System.out.println( "\n//=> Combinator" );
        List<String> confs2 = Arrays.asList( "jug", "devoxx", "javaone", null );
        //System.out.println( map( s -> s.toUpperCase(), confs2) );
        System.out.println( func.map( func.nullCheck(s -> s.toUpperCase()), confs2) );
        System.out.println( confs2.stream().map( func.nullCheck(s -> s.toUpperCase()) ).collect( Collectors.toList() ) );

        // Composition
        System.out.println( "\n//=> Composition" );
        Function<String, String> up = (String s) -> s.toUpperCase();
        System.out.println( func.compose( up, hello).apply("Erouan") );
        System.out.println( up.apply( hello.apply("Erouan") ));
        System.out.println( hello.andThen(up).apply("Erouan") );

    }

}
