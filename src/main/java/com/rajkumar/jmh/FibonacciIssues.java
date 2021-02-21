/**
 * 
 */
package com.rajkumar.jmh;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * @author Rajkumar. S
 *
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class FibonacciIssues {
    
    @Param({ "20", "50" })
    private int FIBNUM; // NOSONAR
    
    @Benchmark
    public long identifyGeneralFib() {
        return generalFib(FIBNUM);
    }
    
    @Benchmark
    public long identifyMemoizeFib() {
        return memoizeFib(FIBNUM);
    }
    
    private static long generalFib(long num) {
        if (num <= 2) {
            return 1;
        }
        
        return generalFib(num - 1) + generalFib(num - 2);
    }
    
    private static long memoizeFib(long num) {
        Map<Long, Long> memoMap = new HashMap<>();
        return memoizeFib(memoMap, num);
        
    }
    
    private static long memoizeFib(Map<Long, Long> memoMap, long num) {
        if (memoMap.containsKey(num)) {
            return memoMap.get(num);
        }
        
        if (num <= 2) {
            return 1L;
        }
        
        memoMap.put(num, memoizeFib(memoMap, num - 1) + memoizeFib(memoMap, num - 2));
        return memoMap.get(num);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Fib of 50: " + generalFib(50));
        System.out.println("-".repeat(20));
        System.out.println("Fib of 50: " + memoizeFib(50));
    }
    
}
