import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean doMenu = true;
        double[] mainArray = new double[0]; // Initialize to size of 0, we'll have to change this size as we add/remove values.

        System.out.println("Welcome to Array Manipulation!\n");
        do { // Doing a do-while loop since we always want to run at least once, we can validate our doMenu variable at the end of the loop.
            printMenu();
            int choice = getUserInt("Your Choice: ");
            switch (choice) {
                case 1: // Add a new number
                    mainArray = addNumber(mainArray); // Need to set mainArray to addNumber's return value, as it returns back the new array.
                    break;
                case 2: // Search for number
                    findNumber(mainArray);
                    break;
                case 3: // Remove a number
                    mainArray = removeNumber(mainArray);
                    break;
                case 4: // Sort array
                    sortArray(mainArray);
                    break;
                case 5: // Print array
                    printArray(mainArray);
                    break;
                case 6: // Print number at index
                    printNumber(mainArray);
                    break;
                case 7: // Print Statistics
                    printStats(mainArray);
                    break;
                case 8: // Clear Array
                    mainArray = clearArray(mainArray);
                    break;
                case 9: // Exit Program
                    System.out.println("Goodbye!");
                    doMenu = false; // while loop will now exit.
                    break;
                default:
                    System.out.println("Invalid input - Please enter a number between 1 and 9.");
            }
        } while (doMenu);
    }

    private static void printMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Add a number to the array.");
        System.out.println("2. Search the array for a number.");
        System.out.println("3. Remove a number from the array.");
        System.out.println("4. Sort the array.");
        System.out.println("5. Print the array.");
        System.out.println("6. Print out the number at the specified index.");
        System.out.println("7. Print out the mean, median, and mode of the array.");
        System.out.println("8. Clear the array.");
        System.out.println("9. Exit program.");
        System.out.println("------------------------------------------------------");
    }

    private static int getUserInt(String message) {
        Scanner input = new Scanner(System.in);
        boolean getValidNum = false;
        int newNum = 0;

        do {
            System.out.print(message);
            try {
                newNum = input.nextInt();
                getValidNum = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input - Please enter a number.");
                input.next();
            } catch (Exception e) {
                System.out.println(e.getMessage()); // Not sure what other errors I could get, so I'll just print them out and handle them later.
                input.next();
            }
        } while (!getValidNum);
        return newNum;
    }

    private static double getUserDouble(String message) {
        // Wrapping this into a function, since I'm asking the user for a double quite often.
        Scanner input = new Scanner(System.in);
        boolean getValidNum = false;
        double newNum = 0.;

        do {
            System.out.print(message);
            try {
                newNum = input.nextDouble();
                getValidNum = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input - Please enter a number.");
                input.next();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                input.next();
            }
        } while (!getValidNum);
        return newNum;
    }

    private static double[] addNumber(double[] mainArray) {
        System.out.println("Adding number to array");
        System.out.println("----------------------");
        double newNum = getUserDouble("Enter any number to insert into the array: ");

        // Since we're adding a new value, we need to make our array longer.
        double[] newArray = new double[mainArray.length + 1];

        // Transfer values from original array to new array.
        // Could use a system function to copy the array, but doing it by hand for learning purposes.
        for (int index = 0; index < mainArray.length; ++index) {
            newArray[index] = mainArray[index];
        }

        // Add our new value to the end of the array.
        newArray[newArray.length - 1] = newNum;
        return newArray;
    }

    private static int findNumberLocation(final double[] mainArray, double value) {
        // Helper function to find numbers within the array.
        // Either will return the index it was found at, or -1 for not found.
        for (int index = 0; index < mainArray.length; ++index) {
            if (mainArray[index] == value) {
                return index;
            }
        }
        return -1;
    }

    private static void findNumber(final double[] mainArray) {
        System.out.println("Find if a number is within the array.");
        System.out.println("-------------------------------------");
        double findNum = getUserDouble("Enter the number you wish to find: ");

        int index = findNumberLocation(mainArray, findNum);
        if (index == -1) {
            System.out.println(findNum + " isn't within the array...");
        } else {
            System.out.println(findNum + " is within the array! In position #" + index);
        }
    }

    private static double[] removeNumber(double[] mainArray) {
        System.out.println("Remove a number from the array.");
        System.out.println("-------------------------------");
        double removeNum = getUserDouble("Enter the number you wish to remove: ");

        int foundIndex = findNumberLocation(mainArray, removeNum);
        double[] workingArray = mainArray;
        while (foundIndex != -1) {
            // We have a valid index of the number we want to remove. So we'll move everything into a new, shorter, array.
            mainArray = workingArray;
            workingArray = new double[mainArray.length - 1];

            // We'll simply transfer to the new array up to the location we wish to skip.
            for (int index = 0; index < foundIndex; ++index) {
                workingArray[index] = mainArray[index];
            }
            // We'll now skip the value we want to remove, and continue transferring.
            for (int index = foundIndex + 1; index < mainArray.length; ++index) {
                // workingArray's index is now one less than main arrays, so we have to account for that.
                workingArray[index - 1] = mainArray[index];
            }

            // With that value now removed, we need to see if there's anymore. So we need to update foundIndex;
            foundIndex = findNumberLocation(workingArray, removeNum);
        }
        return workingArray;
    }

    private static void sort(double[] array) {
        // While we could use a built-in sorting algorithm, going to write one from scratch. Since we're only in section one, we'll do one of the simple sorts.
        // Insertion Sort, for all the values beyond this index, find the smallest one. Once found, swap it with our current position.
        // This isn't an ideal sort, as its speed is n^2, but faster sorts utilize topics we haven't covered yet.
        for (int index = 0; index < array.length; ++index) {
            int minLoc = index;
            for (int next = index + 1; next < array.length; ++next) {
                if (array[next] < array[minLoc]) {
                    minLoc = next;
                }
            }
            // Classic swap, a simple way to swap the values of two variables.
            double tempNum = array[index];
            array[index] = array[minLoc];
            array[minLoc] = tempNum;
        }
    }

    private static void sortArray(double[] mainArray) {
        // Don't need to return the array, since we're modifying mainArray directly. Objects/arrays are by-reference in java.
        System.out.println("Sort the array.");
        System.out.println("-------------------------------");
        sort(mainArray);
        System.out.println("Array Sorted");
    }

    private static void printArray(final double[] mainArray) {
        System.out.println("Print the entire array.");
        System.out.println("-------------------------------");
        System.out.print("[ ");
        for (int index = 0; index < mainArray.length; ++index) {
            // While I could use an enhanced for loop here, I don't want to print the ", " on the last value.
            System.out.print(mainArray[index]);
            if (index != mainArray.length - 1)
                System.out.print(", ");
        }
        System.out.println(" ]");
    }

    private static void printNumber(final double[] mainArray) {
        System.out.println("Print number at specific index.");
        System.out.println("-------------------------------");
        if (mainArray.length <= 0) {
            System.out.println("The array has no values inside, so can't print at a location.");
            return;
        }

        boolean getValidIndex = false;
        int index = 0;
        do {
            index = getUserInt("Enter location you want to print between 0 and " + (mainArray.length - 1) + ": ");
            if (index < 0 || index >= mainArray.length) {
                System.out.println("Location is outside of the bounds of the array, ensure the value is between 0 and " + (mainArray.length - 1) + ".");
            } else {
                getValidIndex = true;
            }
        } while (!getValidIndex);
        System.out.println("Number at position " + index + ": " + mainArray[index]);
    }

    private static int countNumber(final double[] array, double value) {
        int count = 0;
        for (double num : array) {
            if (num == value)
                count++;
        }
        return count;
    }

    private static void printStats(final double[] mainArray) {
        System.out.println("Print statistics of values within the array.");
        System.out.println("-------------------------------");

        if (mainArray.length == 0) {
            System.out.println("No values within the array, so can't calculate any stats.");
            return;
        }

        double[] workingArray = mainArray;
        sort(workingArray); // Made a copy of the array to sort, as I don't want to change their array.

        // Calculate average, the sum of all the numbers, divided by amount of numbers we have.
        double average = 0.;
        for (double val : workingArray) {
            average += val;
        }
        average /= mainArray.length;

        // We can just use the int value of half the length of the array to calculate the median. Since we sorted our workingArray
        double median = 0.;
        if (workingArray.length % 2 == 0) {
            // Even number of values, so it's the average of the two middle numbers.
            int middle = workingArray.length / 2;
            median = (workingArray[middle - 1] + workingArray[middle]) / 2;
        } else {
            // Odd number of values, so it's just the middle number.
            median = workingArray[workingArray.length / 2];
        }

        // Calculate mode. Without more advanced data structures, this is a bit tricky.
        // First, find what the maximum count of a single value is.
        int maxCount = 0;
        for (int index = 0; index < workingArray.length; ++index) {
            if (index != 0 && workingArray[index] == workingArray[index -1])
                continue; // Don't process the same number.
            int count = countNumber(workingArray, workingArray[index]);
            if (count > maxCount)
                maxCount = count;
        }

        // Next, if a specific value's count matches the maxCount, then concat it to the string.
        String modes = "";
        for (int index = 0; index < workingArray.length; ++index) {
            if (index != 0 && workingArray[index] == workingArray[index -1])
                continue; // Don't process the same number.
            if (countNumber(workingArray, workingArray[index]) == maxCount)
                modes = modes + workingArray[index] + " ";
        }

        System.out.println("Average: " + average);
        System.out.println("Median: " + median);
        System.out.println("Mode: " + modes);
    }

    private static double[] clearArray(double[] mainArray) {
        System.out.println("Clear the array.");
        System.out.println("-------------------------------");
        System.out.println("Array cleared.");
        return new double[0];
    }
}