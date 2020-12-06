package com.cognodrive.year20.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ExpenseReporter
 */
public class Day1 {
    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        ArrayList<Integer> testReport = new ArrayList<Integer>();
        testReport.add(1721);
        testReport.add(979);
        testReport.add(366);
        testReport.add(299);
        testReport.add(675);
        testReport.add(1456);
        System.out.println(Day1.getProduct(testReport, 2020));
        System.out.println(Day1.getProduct(Day1.getExpenseReport(), 2020));
    }

    /*private static IntStream getExpenseReport() {
        IntStream items = null;
        try {
            items = Files.lines(Path.of("expenseReport.txt")).map(str -> Integer.parseInt(str)).mapToInt(Integer::intValue);
        } catch(Exception ex) {
            //nada
        }
        return items;
    }*/

    // Todo: could have changed to reduce instead of foreach.
    private static ArrayList<Integer> getExpenseReport() {
        ArrayList<Integer> items = new ArrayList<Integer>();
        try {
            // Doesn't preserve order but that is ok
            //Files.list(Path.of(".")).forEach(a -> System.out.println(a));
            Files.lines(Path.of("src\\main\\resources\\2020\\day1.txt")).
                    map(str -> Integer.parseInt(str)).
                    mapToInt(Integer::intValue).forEach(item -> items.add(item));
        } catch(Exception ex) {
            System.out.println(ex);
        }
        return items;
    }
    

    public static int getProduct(ArrayList<Integer> expenseReport, int sum) {
        
        int length = expenseReport.size()-1;
        int[] indices = IntStream.range(0, length).parallel().
            mapToObj(firstIndex -> {
                Stream<int[]> comboStream = IntStream.range(firstIndex + 1, length).mapToObj(secondIndex -> new int[]{firstIndex,secondIndex});
                return comboStream;
            }).
            flatMap(Function.identity()).filter(combo -> {
                return expenseReport.get(combo[0]) + expenseReport.get(combo[1]) == sum;
            }).findFirst().get();
        return expenseReport.get(indices[0]) * expenseReport.get(indices[1]);
    }



}
