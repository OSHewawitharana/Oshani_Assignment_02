
/**
 * Write a description of class StudentDetails here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StudentDetails {

    private String studentName;
    private String studentId;

    private String mark01;
    private String mark02;
    private String mark03;

    private String firstName;
    private String lastName;

    private double totalMarks = 0.0;

    public StudentDetails() {
    }

    public StudentDetails(String studentId, String mark01, String mark02, String mark03, String firstName, String lastName, double totalMarks) {
        this.studentId = studentId;
        this.mark01 = mark01;
        this.mark02 = mark02;
        this.mark03 = mark03;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalMarks = totalMarks;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }


    public String getStudentName() {
        return studentName;
    }


    public void setStudentName(String firstName, String lastName) {
        this.studentName = firstName + " " + lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMark01() {
        return mark01;
    }

    public void setMark01(String mark01) {
        this.mark01 = mark01;
    }

    public String getMark02() {
        return mark02;
    }

    public void setMark02(String mark02) {
        this.mark02 = mark02;
    }

    public String getMark03() {
        return mark03;
    }

    public void setMark03(String mark03) {
        this.mark03 = mark03;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
