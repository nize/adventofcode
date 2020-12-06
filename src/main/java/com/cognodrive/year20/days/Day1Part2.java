package com.cognodrive.year20.days;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * ExpenseReporter
 */
public class Day1Part2 {
    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        HashSet<Integer> testReport = new HashSet<Integer>();
        testReport.add(1721);
        testReport.add(979);
        testReport.add(366);
        testReport.add(299);
        testReport.add(675);
        testReport.add(1456);
        System.out.println(Day1Part2.getProduct(testReport, 2020));
        System.out.println(Day1Part2.getProduct(Day1Part2.getExpenseReport(), 2020));
    }

    private static Set<Integer> getExpenseReport() {
        Set<Integer> items = new HashSet<Integer>();
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

    // Instead of using the Sets lib one could also have implemented a custom one consuming a stream and producing a stream of combinations.
    public static int getProduct(Set<Integer> expenseReport, int sum) {
        Set<Set<Integer>> combinations = Sets.combinations(expenseReport, 3);
        return combinations.parallelStream().filter(combo -> {
            return combo.parallelStream().reduce(0,Integer::sum) == sum;
        }).findFirst().map(combo -> combo.parallelStream().reduce(1,Math::multiplyExact)).get();
    }
}
