import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

/**
 * Simple program to compute statistics of 'students' marks in an assignment.
 *
 * @author (Oshani Hewawitharana)
 * @version (Version 02.0 10/02/2023)
 */
public class StudentMarks
{
    // instance variables
    private static File studentGradeCsv = new File("prog5001_students_grade_2022.csv");
    private static Scanner readScanner;
    private static String unitName;
    private static String headings;
    private static String[] lineArray;
    Double threshold;
    static String fileName;
    private static List<StudentDetails> studentDetailsArray = new ArrayList<>();

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Enter the file name: ");
            fileName = scanner.nextLine();
        }
    
        if (!fileName.equals("prog5001_students_grade_2022")) {
            System.out.println("#######Error!######");
            System.out.println("The file name you have entered is not exists. Reenter the file name.\n");
            fileName = "";
        }
    
        readFromTheFile();
        StudentMarks studentMarks = new StudentMarks();
        studentMarks.mainMenu();
    }

    /**
     * Default constructor for objects of class StudentMarks
     *
     * When it creates an object with this constructor, it executes methods call in this constructor
     */
    public StudentMarks() {}

     /**
     * The mainMenu method will take the user input to display their request and display the results accordingly
     *
     */
    public void mainMenu() {
        // Create a Scanner object once outside the loop
        Scanner scanner = new Scanner(System.in);
    
        while (true) { // Loop until the user chooses to exit
    
            try {
                displayMenu();
    
                int optionId = scanner.nextInt();
                
                runSelectedOption(optionId);
                
                if (optionId == 0) {
                    break; // Exit the loop if the user chooses to exit
                }
                
            } catch (InputMismatchException ime) {
                System.out.println("#######Error!######");
                System.out.println("The option id you have entered is invalid. You should enter a number.");
                scanner.nextLine(); // Consume the invalid input
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The displayMenu method will display the menu options to view information
     * 
     */
    private void displayMenu() {
        System.out.println("\n------------------------Select from the menu---------------------");
        System.out.println("1. Enter 1 to calculate and display total marks of the students assignments");
        System.out.println("2. Enter 2 to display the list of students with total marks less than a threshold");
        System.out.println("3. Enter 3 to display the top 05 students with the lowest total marks");
        System.out.println("4. Enter 4 to display the top 05 students with the highest total marks");
        System.out.println("5. Enter 0 to exit the menu");
    }
    
    /**
     * The runSelectedOption method will display the output according to the user request
     * 
     * @Param int optionId
     * 
     */
    public void runSelectedOption(int optionId) {
        switch (optionId){
                case 1:
                    printTotalMarks(); // Print total marks
                    break;
                case 2:
                    printsStudentsWithMarksLessThanThreshold(); // Prints the students less than the user input threshold
                    break;
                case 3:
                    printTop05StudentsWithLowestTotal(); // Print Top 05 students with the lowest total mark
                    break;
                case 4: 
                    printTop05StudentsWithHighestTotal(); // Print Top 05 students with the highest total mark
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
    }

    /**
     * The readFromTheFile method will read the data in the file
     * 
     * 
     */
    public static void readFromTheFile() {
        int lineNumber = 0;
        try {
            readScanner = new Scanner(studentGradeCsv);

            while (readScanner.hasNextLine()) {
                lineNumber = lineNumber + 1;
                String line =  readScanner.nextLine();
                if (lineNumber == 1) { // Read the first line of the file which is the unit name
                    unitName = line;
                } 
                if (lineNumber == 2) { // Read the second line of the file which is includes all the headings in the file
                    headings = line;
                }
                if (line.startsWith("#")) { // Ignore comment lines
                    continue;
                }
                if (line.startsWith("//")) { // Ignore comment lines
                    continue;
                }
                line = line.replace(",,",",0.0,"); // Assign 0.0 to empty values
                if (line.endsWith(",")) {
                    line = line + "0.0";
                }
                lineArray = line.split(",");
                if (lineArray.length != 6) { // Ensuring valid data ignores the empty cells replace this by changing the null values to 0
                    continue;
                }
                if (lineArray.length >= 1 && (lineArray[0].equals("0.0") || lineArray[0].equals(""))) {
                    continue;  // ignore the empty lines which were assigned with 0.0 previously
                }
                if (lineNumber != 1 && lineNumber != 2) { // Read the remaining lines of the file except line 01 and 02
                    StudentDetails studentDetails = new StudentDetails(lineArray[2].trim(),lineArray[3].trim(),lineArray[4].trim(),lineArray[5],
                    lineArray[0].trim(),lineArray[1].trim(), (Double.parseDouble(lineArray[3].trim()) + Double.parseDouble(lineArray[4].trim()) + Double.parseDouble(lineArray[5].trim())) );
                    studentDetailsArray.add(studentDetails);
                } 
            }
        } catch(IOException e) {
            System.out.println("#######Error!######");
            System.out.println("There is  an error in reading file.");
            e.printStackTrace();
        }
    }


    /**
     * The printHighest method will show the total marks of each students
     *
     */
    public void printTotalMarks() {
        System.out.println("-------------------------Total mark of the students ---------------------\n");
        System.out.println(unitName + "\n");
        System.out.println(headings + ", Total Mark \n" );
        for (int i=0; i< studentDetailsArray.size(); i++) {
            System.out.println(studentDetailsArray.get(i).getLastName()+","+studentDetailsArray.get(i).getFirstName() +","+
            studentDetailsArray.get(i).getStudentId()+","+studentDetailsArray.get(i).getMark01()+","+studentDetailsArray.get(i).getMark02()+
            ","+studentDetailsArray.get(i).getMark03()+", "+studentDetailsArray.get(i).getTotalMarks() );
        }

    }

    /**
     * The getThresholdValue method will receive a threshold value as an input
     *
     */
    public void getThresholdValue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter the threshold value: ");
        boolean validInput = false;

        while (!validInput) {
            try {
                threshold = scanner.nextDouble();
                validInput = true; // Exit the loop if input is a valid double
            } catch (InputMismatchException ime) {
                System.out.println("#######Error!######");
                System.out.println("The threshold value you have entered is invalid. You should enter a number.");
                scanner.nextLine(); // Consume the invalid input
            } catch (Exception ex) {
                System.out.println("#######Error!######");
                System.out.println("There is an error when getting input: " + ex);
            }
        }
    }

    /**
     * The printsStudentsWithMarksLessThanThreshold method will show the students with marks less thaan the threshold value which was taken from the user
     *
     */
    public void printsStudentsWithMarksLessThanThreshold() {
        getThresholdValue();
        System.out.println("\n--------------- Students with total mark less than threshold value of " + threshold + "---------------\n");
        System.out.println(headings + ", Total Mark \n" );
        System.out.println(studentDetailsArray.size());
        for (int i=0; i<studentDetailsArray.size(); i++) {
            if (studentDetailsArray.get(i).getTotalMarks() < threshold ) {
                System.out.println(studentDetailsArray.get(i).getLastName()+","+studentDetailsArray.get(i).getFirstName() +","+
                studentDetailsArray.get(i).getStudentId()+","+studentDetailsArray.get(i).getMark01()+","+studentDetailsArray.get(i).getMark02()+
                ","+studentDetailsArray.get(i).getMark03()+", "+studentDetailsArray.get(i).getTotalMarks() );
            }
        }

    }

    /**
     * The sortStudentsTotalMarks method will sort all the student by their total marks
     *
     */
    public void sortStudentsTotalMarks() {

        for (int i = 0; i < studentDetailsArray.size()-1; i++) {
            for (int k = 0; k < studentDetailsArray.size()-i-1; k++) {
                if (studentDetailsArray.get(k).getTotalMarks() > studentDetailsArray.get(k + 1).getTotalMarks()) {
                    
                    // Assign the kth object in the studentDetailsArray to the tmpStudentDetail
                    StudentDetails tmpStudentDetail = new StudentDetails(studentDetailsArray.get(k).getStudentId(), studentDetailsArray.get(k).getMark01(),
                            studentDetailsArray.get(k).getMark02(), studentDetailsArray.get(k).getMark03(), studentDetailsArray.get(k).getFirstName(),
                            studentDetailsArray.get(k).getLastName(), studentDetailsArray.get(k).getTotalMarks());

                    // Assign the (k+1)th object in the studentDetailsArray to the kth object in the studentDetailsArray
                    studentDetailsArray.get(k).setTotalMarks(studentDetailsArray.get(k+1).getTotalMarks());
                    studentDetailsArray.get(k).setStudentId(studentDetailsArray.get(k+1).getStudentId());
                    studentDetailsArray.get(k).setFirstName(studentDetailsArray.get(k+1).getFirstName());
                    studentDetailsArray.get(k).setLastName(studentDetailsArray.get(k+1).getLastName());
                    studentDetailsArray.get(k).setMark01(studentDetailsArray.get(k+1).getMark01());
                    studentDetailsArray.get(k).setMark02(studentDetailsArray.get(k+1).getMark02());
                    studentDetailsArray.get(k).setMark03(studentDetailsArray.get(k+1).getMark03());

                    // Assign the tmpStudentDetail to the (k+1)th object in the studentDetailsArray
                    studentDetailsArray.get(k+1).setTotalMarks(tmpStudentDetail.getTotalMarks());
                    studentDetailsArray.get(k+1).setStudentId(tmpStudentDetail.getStudentId());
                    studentDetailsArray.get(k+1).setFirstName(tmpStudentDetail.getFirstName());
                    studentDetailsArray.get(k).setLastName(tmpStudentDetail.getLastName());
                    studentDetailsArray.get(k+1).setMark01(tmpStudentDetail.getMark01());
                    studentDetailsArray.get(k+1).setMark02(tmpStudentDetail.getMark02());
                    studentDetailsArray.get(k+1).setMark03(tmpStudentDetail.getMark03());

                }
            }
        }
    }

    /**
     * The printTop05StudentsWithLowestTotal method will show the top 05 students who are having lowest total marks
     *
     */
    public void printTop05StudentsWithLowestTotal() {
        sortStudentsTotalMarks(); // Sort the student details array
        System.out.println("-------------Top 05 students with lowest total mark -------------------------\n");
        System.out.println(headings + ", Total Mark \n" );
        
        for (int i = 0; i<5; i++) {
           System.out.println(studentDetailsArray.get(i).getLastName()+","+studentDetailsArray.get(i).getFirstName() +","+
                studentDetailsArray.get(i).getStudentId()+","+studentDetailsArray.get(i).getMark01()+","+studentDetailsArray.get(i).getMark02()+
                ","+studentDetailsArray.get(i).getMark03()+", "+studentDetailsArray.get(i).getTotalMarks() );
        }

    }

    /**
     * The printTop05StudentsWithHighestTotal method will show the top 05 students who are having highest total marks
     *
     */
    public void printTop05StudentsWithHighestTotal() {
        sortStudentsTotalMarks(); // Sort the student details array
        System.out.println("-------------Top 05 students with highest total mark -------------------------\n");
        System.out.println(headings + ", Total Mark \n" ); // Print the heading included in the file with Total Mark as another heading
        
        for (int i = studentDetailsArray.size()-1; i>studentDetailsArray.size()-6; i--) {
            System.out.println(studentDetailsArray.get(i).getLastName()+","+studentDetailsArray.get(i).getFirstName() +","+
                studentDetailsArray.get(i).getStudentId()+","+studentDetailsArray.get(i).getMark01()+","+studentDetailsArray.get(i).getMark02()+
                ","+studentDetailsArray.get(i).getMark03()+", "+studentDetailsArray.get(i).getTotalMarks() );
        }

    }
}




