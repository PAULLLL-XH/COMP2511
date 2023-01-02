package q12.operation;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class Operation<T, R> {

    private Function<T, R> operator;
    private Supplier<R> exceptOperator;
    private HashMap<T, R> cache = new HashMap<>();

    public Operation() {
    }

    public Operation(Function<T, R> operation) {
        this.operator = operation;
    }

    public Operation(Function<T, R> operation, Supplier<R> exceptOpertion) {
        this.operator = operation;
        this.exceptOperator = exceptOpertion;
    }

    public <V> Operation<T, V> then(Function<R, V> operation) {
        Function<T, V> f = this.operator.andThen(operation);
        return new Operation<>(f);
    }

    public Operation<T, R> except(Supplier<R> operation) {
        return new Operation<>(this.operator, operation);
    }

    public Function<T, R> getFunction(){
        return this.operator;
    }

    public boolean hasCachedResult(T forInput) {
        return cache.containsKey(forInput);
    }

    public R resolve(T input) {
        if (hasCachedResult(input)){
            return cache.get(input);
        }
        R ret;
        try{
            ret = operator.apply(input);
        } catch (Exception e){
            return exceptOperator.get();
        }
        cache.put(input, ret);
        return ret;
    }
}