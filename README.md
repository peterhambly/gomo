# gomo

This is the GOMO challenge code by Peter Hambly

## Prerequistes

* Java 1.8
* Apache Maven 3.5.3+. 

Maven and Java must be executable from the command line

Frameworks used: NONE. Based on the Spring frameworks maven simple executable example.

## Building

To build type: ```mvn clean install```. This:

* Cleans out any old build.
* Lints the code to Sun standards;
* Compiles Java to a jar file.

## Running

To run type: ```java -jar target/gomo-test-0.1.0.jar 0-1000 2000-3000 2500-4000```

E.g.

```
C:\Users\phamb\Documents\GitHub\gomo>java -jar target/gomo-test-0.1.0.jar 0-1000 2000-3000 2500-4000
3000
```

## Alogorithm

The algorithm is implemented by ```UvtPeriod.java``` and the functions *periodsOverlap* and *mergePeriods*. The function *addToUvtList* in ```Uvt.java``` uses these functions to add **UvtPeriod** objects to a list one argument at a time:

* If there is nothing in list then add;
* Check to see if new UvtPeriod overlaps any current member of the list:
  * If it overlaps the merge the two periods into one;
  * Otherwise add;
* Finally, if the list has two or more items check to see if any adjacent periods overlap and need merging. If merging is required the new period replaces the first and the second period is removed from the list.

Arguments are checked by regular expression.

## Tests

1. supplied example: 0-1000, 2000-3000, 2500-4000
2. NullPointerException checks   
3. test invalid characters: 0-1000a, 2@000-3000, 2500-4000}
4. test no separator for a period: 0-1000, 2000, 2500-4000
5. test no period after separator: 0-1000, 2000-, 2500-4000
6. test negative start time: -100-1000, 2000-3000, 2500-4000
7. supplied example as decimal mS: 0.0-1000.0, 2000.0-3000.0, 2500.0-4000.0
8. no overlap: 0-1000, 2000-2500, 3000-4000
9. different order: 0-1000, 2500-4000, 2000-3000
10. different order 2: 2500-4000, 2000-3000, 0-1000
11. multiple overlaps: 0-1000, 2000-3000, 2500-4000, 500-3500