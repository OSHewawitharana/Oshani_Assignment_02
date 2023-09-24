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
    File studentGradeCsv = new File("prog5001_students_grade_2022.csv");;

    Scanner readScanner;
    String unitName;
    String[] lineArray;
    Double threshold;
    private List<StudentDetails> studentDetailsArray = new ArrayList<>();
   
    /**
     * Constructor for objects of class StudentMarks
     *
     * When it creates an object with this constructor, it executes methods call in this constructor
     */
    public StudentMarks()
    {
        readFromTheFile();
        getUnitName();
        calculateTotalAssignmentMarks();
        printTotalMarks();
        getThresholdValue();
        printsStudentsWithMarksLessThanThreshold();
        sortStudentsTotalMarks();
        printTop05StudentsWithLowestTotal();
        printTop05StudentsWithHighestTotal();
    }

    public void readFromTheFile() {
        int lineNumber = 0;
        try {
            readScanner = new Scanner(studentGradeCsv);

            while (readScanner.hasNextLine()) {
                lineNumber = lineNumber + 1;
                String line =  readScanner.nextLine();
                line = line.replace(",,",",0.0,");
                if (line.endsWith(",")) {
                    line = line + "0.0";
                }
                lineArray = line.split(",");
                if (lineNumber != 1 && lineNumber != 2) {
                    //System.out.println(line);
                    //System.out.println(lineNumber);
                    StudentDetails studentDetails = new StudentDetails(lineArray[2],lineArray[3],lineArray[4],lineArray[5],lineArray[0],lineArray[1], 0.0 );
                    studentDetailsArray.add(studentDetails);
                } else if (lineNumber == 1) {
                    unitName = line;
                }

            }
        } catch(IOException e) {
            System.out.println("There is  an error in reading file.");
            e.printStackTrace();
        }
    }

    /**
     * The getAssignmentName method will receive the name of the assignment as an input
     *
     */
    public void getUnitName() {
        System.out.println("\n" + unitName + "\n");
    }

    /**
     * The getAssignmentMarks method will receive 30 student marks as an input
     *
     */
    public void calculateTotalAssignmentMarks() {
        double sum = 0.0;
        for (StudentDetails studentDetails: studentDetailsArray) {
            if (studentDetails != null) {
                sum = Double.parseDouble(studentDetails.getMark01()) + Double.parseDouble(studentDetails.getMark02()) + Double.parseDouble(studentDetails.getMark03());
                studentDetails.setTotalMarks(sum);
            }
        }
    }

    /**
     * The printHighest method will show the highest mark out of 30 marks which were given as input
     *
     */
    public void printTotalMarks() {
        System.out.println("-------------------------Total mark of the students ---------------------");
        for (int i=0; i<studentDetailsArray.size(); i++) {
            System.out.println(studentDetailsArray.get(i).getTotalMarks() + " - " + studentDetailsArray.get(i).getFirstName() + " " + studentDetailsArray.get(i).getLastName() + " bearing student id of  " + studentDetailsArray.get(i).getStudentId());
        }

    }

    public void getThresholdValue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter the threshold value: ");
        try {
            threshold = scanner.nextDouble();
            if (this.threshold.equals("")) {
                System.out.println("Invalid input. Assignment name cannot be empty. Please enter the assignment name.");
            }
        } catch (InputMismatchException ime) { // Handle error occurred when user enters data type other than double
            System.out.println("The threshold value you have entered is invalid. You should enter a number." );
            scanner.nextDouble();; // Consume the invalid input
        } catch (Exception ex) {
            System.out.println("There is an error when getting input " + ex);
        }
    }

    /**
     * The printLowest method will show the lowest mark out of 30 marks which were given as input
     *
     */
    public void printsStudentsWithMarksLessThanThreshold() {
        System.out.println("\n--------------- Students with total mark less than threshold value of " + threshold + "---------------");
        for (int i=0; i<studentDetailsArray.size(); i++) {
            if (studentDetailsArray.get(i).getTotalMarks() < threshold) {
                System.out.println(studentDetailsArray.get(i).getFirstName() + " " + studentDetailsArray.get(i).getLastName());
            }
        }

    }

    /**
     * The printAssignmentMarks method will show the all the marks which were given as input
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
     * The calculateAndPrintMean method will show the mean value of the given marks
     *
     */
    public void printTop05StudentsWithLowestTotal() {
        for (int k = 0; k < studentDetailsArray.size(); k++) {
            System.out.println(studentDetailsArray.get(k).getTotalMarks());
        }
        System.out.println("-------------Top 05 students with lowest total mark -------------------------");
        for (int i = 0; i<5; i++) {
            System.out.println(studentDetailsArray.get(i).getFirstName() + " " + studentDetailsArray.get(i).getLastName());
            System.out.println(studentDetailsArray.get(i).getTotalMarks());
        }

    }

    /**
     * The calculateAndPrintStandardDeviation method will show the standard deviation of the marks given as the input
     *
     */
    public void printTop05StudentsWithHighestTotal() {
        System.out.println("-------------Top 05 students with highest total mark -------------------------");
        for (int i = studentDetailsArray.size(); i>studentDetailsArray.size()-5; i--) {
            System.out.println(studentDetailsArray.get(i-1).getFirstName() + " " + studentDetailsArray.get(i-1).getLastName());
            System.out.println(studentDetailsArray.get(i-1).getTotalMarks() + " " + studentDetailsArray.get(i-1).getTotalMarks());
        }

    }
}




