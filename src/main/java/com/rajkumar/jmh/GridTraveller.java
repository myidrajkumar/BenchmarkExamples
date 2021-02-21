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
 * On 2D grid, how many ways we have to reach bottom right from top left?
 * 
 * <br />
 * <b>Rule:</b> We can move either down or right only
 * 
 * @author Rajkumar. S
 *
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class GridTraveller {
    
    @Param({ "3", "18" })
    private int ROW; // NOSONAR
    
    @Param({ "3", "18" })
    private int COLUMN; // NOSONAR
    
    @Benchmark
    public long identifyGeneralGridTravel() {
        return generalGridTravel(ROW, COLUMN);
    }
    
    @Benchmark
    public long identifyMemoizeGridTravel() {
        return memoizeGridTravel(ROW, COLUMN);
    }
    
    private static long generalGridTravel(int row, int column) {
        if (row == 1 && column == 1) {
            return 1L;
        }
        
        if (row == 0 || column == 0) {
            return 0L;
        }
        
        return generalGridTravel(row - 1, column) + generalGridTravel(row, column - 1);
    }
    
    private static long memoizeGridTravel(int row, int column) {
        Map<String, Long> memoMap = new HashMap<>();
        return memoizeGridTravel(row, column, memoMap);
    }
    
    private static long memoizeGridTravel(int row, int column, Map<String, Long> memoMap) {
        String key = row + "," + column;
        if (memoMap.containsKey(key)) {
            return memoMap.get(key);
        }
        
        if (row == 1 && column == 1) {
            return 1L;
        }
        
        if (row == 0 || column == 0) {
            return 0L;
        }
        
        memoMap.put(key, memoizeGridTravel(row - 1, column, memoMap) + memoizeGridTravel(row, column - 1, memoMap));
        
        return memoMap.get(key);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(generalGridTravel(1, 1));
        System.out.println(generalGridTravel(2, 3));
        System.out.println(generalGridTravel(3, 2));
        System.out.println(generalGridTravel(3, 3));
        System.out.println(generalGridTravel(18, 18));
        System.out.println("-".repeat(30));
        System.out.println(memoizeGridTravel(1, 1));
        System.out.println(memoizeGridTravel(2, 3));
        System.out.println(memoizeGridTravel(3, 2));
        System.out.println(memoizeGridTravel(3, 3));
        System.out.println(memoizeGridTravel(18, 18));
    }
    
}
