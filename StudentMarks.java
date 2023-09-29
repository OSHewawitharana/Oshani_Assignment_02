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
 * @version (Version 01.0 08/29/2023)
 */
public class StudentMarks
{
    // instance variables
    File studentGradeCsv = new File("prog5001_students_grade_2022.csv");
    Scanner readScanner;
    String unitName;
    String headings;
    String[] lineArray;
    Double threshold;
    String fileName;
    private List<StudentDetails> studentDetailsArray = new ArrayList<>();

    
    public static void main(String[] args) {
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
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Enter the file name: ");
            fileName = scanner.nextLine();
        }

        if (!fileName.equals("prog5001_students_grade_2022")) {
            System.out.println("#######Error!######");
            System.out.println("The file name you have entered is not exists. Reenter the file name.\n");
            fileName = "";
            continue; // Continue to the next iteration of the loop
        }

        readFromTheFile();

        try {
            System.out.println("\n------------------------Select from the menu---------------------");
            System.out.println("1. Enter 1 to print unit name and the students' marks details in the file.");
            System.out.println("2. Enter 2 to calculate and display total marks of the students assignments");
            System.out.println("3. Enter 3 to display the list of students with the total marks less than a threshold which can be given by you.");
            System.out.println("4. Enter 4 to display top 05 students with the lowest total marks");
            System.out.println("5. Enter 5 to display top 05 students with the highest total marks");
            System.out.println("6. Enter 0 to exit the menu\n");

            int optionId = scanner.nextInt();
            runSelectedOption(optionId);

            if (optionId == 0) {
                break; // Exit the loop if the user chooses to exit
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
                    printFileDetails(); // Print the file details
                    mainMenu();
                    break;
                case 2:
                    printTotalMarks(); // Print total marks
                    mainMenu();
                    break;
                case 3:
                    printsStudentsWithMarksLessThanThreshold(); // Prints the students less than the user input threshold
                    mainMenu();
                    break;
                case 4:
                    sortStudentsTotalMarks(); // Sort the student details array
                    printTop05StudentsWithLowestTotal(); // Print Top 05 students with the lowest total mark
                    mainMenu();
                    break;
                case 5: 
                    sortStudentsTotalMarks(); // Sort the student details array
                    printTop05StudentsWithHighestTotal(); // Print Top 05 students with the highest total mark
                    mainMenu();
                    break;
    
                case 0:
                    break;
            }
    }

    /**
     * The readFromTheFile method will read the data in the file
     * 
     * 
     */
    public void readFromTheFile() {
        int lineNumber = 0;
        try {
            readScanner = new Scanner(studentGradeCsv);

            while (readScanner.hasNextLine()) {
                lineNumber = lineNumber + 1;
                String line =  readScanner.nextLine();
                if (lineNumber == 1) {
                    unitName = line;
                } 
                if (lineNumber == 2) {
                    headings = line;
                }
                if (line.startsWith("#")) { // Ignore comment lines
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
                if (lineNumber != 1 && lineNumber != 2) {
                    
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
     * The printFileDetails method will print all the details in the file
     * 
     * 
     */
    public void printFileDetails () {
        System.out.println(unitName + "\n");
        System.out.println(headings + "\n");
        for (int i=0; i< studentDetailsArray.size(); i++) {
            System.out.println(studentDetailsArray.get(i).getLastName()+","+studentDetailsArray.get(i).getFirstName() +","+
            studentDetailsArray.get(i).getStudentId()+","+studentDetailsArray.get(i).getMark01()+","+studentDetailsArray.get(i).getMark02()+
            ","+studentDetailsArray.get(i).getMark03());
        }
        
    }


    /**
     * The printHighest method will show the total marks of each students
     *
     */
    public void printTotalMarks() {
        System.out.println("-------------------------Total mark of the students ---------------------\n");
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
        System.out.println("\n--------------- Students with total mark less than threshold value of " + threshold + "---------------");
        for (int i=0; i<studentDetailsArray.size(); i++) {
            if (studentDetailsArray.get(i).getTotalMarks() < threshold ) {
                System.out.println(studentDetailsArray.get(i).getFirstName() + " " + studentDetailsArray.get(i).getLastName());
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
                    StudentDetails tmpStudentDetail = new StudentDetails(studentDetailsArray.get(k).getStudentId(), studentDetailsArray.get(k).getMark01(),
                            studentDetailsArray.get(k).getMark02(), studentDetailsArray.get(k).getMark03(), studentDetailsArray.get(k).getFirstName(),
                            studentDetailsArray.get(k).getLastName(), studentDetailsArray.get(k).getTotalMarks());

                    studentDetailsArray.get(k).setTotalMarks(studentDetailsArray.get(k+1).getTotalMarks());
                    studentDetailsArray.get(k).setStudentId(studentDetailsArray.get(k+1).getStudentId());
                    studentDetailsArray.get(k).setFirstName(studentDetailsArray.get(k+1).getFirstName());
                    studentDetailsArray.get(k).setLastName(studentDetailsArray.get(k+1).getLastName());
                    studentDetailsArray.get(k).setMark01(studentDetailsArray.get(k+1).getMark01());
                    studentDetailsArray.get(k).setMark02(studentDetailsArray.get(k+1).getMark02());
                    studentDetailsArray.get(k).setMark03(studentDetailsArray.get(k+1).getMark03());

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
        System.out.println("-------------Top 05 students with lowest total mark -------------------------");
        for (int i = 0; i<5; i++) {
            System.out.println(studentDetailsArray.get(i).getFirstName() + " " + studentDetailsArray.get(i).getLastName());
            //System.out.println(studentDetailsArray.get(i).getTotalMarks());
        }

    }

    /**
     * The printTop05StudentsWithHighestTotal method will show the top 05 students who are having highest total marks
     *
     */
    public void printTop05StudentsWithHighestTotal() {
        System.out.println("-------------Top 05 students with highest total mark -------------------------");
        for (int i = studentDetailsArray.size(); i>studentDetailsArray.size()-5; i--) {
            System.out.println(studentDetailsArray.get(i-1).getFirstName() + " " + studentDetailsArray.get(i-1).getLastName());
            //System.out.println(studentDetailsArray.get(i-1).getTotalMarks() + " " + studentDetailsArray.get(i-1).getTotalMarks());
        }

    }
}




