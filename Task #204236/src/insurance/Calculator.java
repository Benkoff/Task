package insurance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
 * The Harrison Group Life Insurance company computes annual policy premiums based on the age the customer turns
 * in the current calendar year. The premium is computed by taking the decade of the customer’s age, adding 15 to it,
 * and multiplying by 20. For example, a 34 year old would pay $360, which is calculated by adding the decades
 * (3) to 15, and then multiplying by 20.
 * Write an application that prompts a user for the current year and a birth year.
 * Pass both to a method that calculates and returns the premium amount, and then display the returned amount.
 */
public class Calculator {
    private LocalDate birthDate = null;
    private LocalDate calcDate = null;

    public Calculator() {
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("Please, enter the birth date, format: M/d/yyyy. Empty line to exit:");
        calc.birthDate = calc.readDate();
//        System.out.println("The date entered: " + calc.birthDate);

        System.out.println("Enter calculation date, format: M/d/yyyy. Empty line to exit:");
        calc.calcDate = calc.readDate();
//        System.out.println("The date entered: " + calc.calcDate);

        System.out.println("Your annual policy premium: $" + (calculateAge(calc.birthDate, calc.calcDate)/10+15)*20);
    }

    private LocalDate readDate() {
        LocalDate date = null;
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                line = br.readLine();
                if (line.equals("")) {
                    br.close();
                    System.out.println("An empty line entered. Exiting.");
                    System.exit(0);
                } else {
                    date = parseInput(line);
                    if (date != null) {

                        return date;
                    }
                }
            }
        } catch(IOException e){
            e.getStackTrace();
        }

        return null;
    }

    private LocalDate parseInput(String userInput) {
        LocalDate date = null;
        if (userInput != null && !userInput.equals("")) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
            try {
                date = LocalDate.parse(userInput, dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Illegal date, please use format: M/d/yyyy.");
                date = null;
            }
        }

        return date;
    }

    public static int calculateAge(LocalDate first, LocalDate second) {
        if ((first != null) && (second != null)) {
            return Period.between(first, second).getYears();
        } else {
            return 0;
        }
    }
}
