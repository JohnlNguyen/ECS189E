import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/*
A file for each class, test class for each class
    3 files -> AdminTest, InstructorTest, StudentTest
    a couple more files-> for broader bugs
    we are testing the implementation for the api
    import api.IStudent;
    import api.core.impl.Student;
*/


/**
 * Created by Vincent on 23/2/2017.
 */
public class TestIntegration {

    private IAdmin admin;
    private  IStudent student;
    private IInstructor instructor;
    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
    }

    @Test
    public void testIntegration() {
        this.admin.createClass("Test",2018,"Instructor",5);
        this.instructor.addHomework("Instructor","Test",2018,"HW1","HW Description");
        this.student.registerForClass("John","Test",2018);
        this.student.submitHomework("John","HW1","ABC","Test",2018);
        this.instructor.assignGrade("Instructor","Test",2018,"HW1","John",100);
        int grade = this.instructor.getGrade("Test",2018,"HW1","John");
        assertEquals(grade,100);
    }

    @Test
    public void testIntegration2() {
        this.admin.createClass("Test",2018,"Instructor",5);
        this.instructor.addHomework("Instructor","Test",2018,"HW1","HW Description");
        this.student.registerForClass("John","Test",2018);
        this.student.submitHomework("John","HW1","ABC","Test",2018);
        this.instructor.assignGrade("Instructor","Test",2018,"HW1","John",100);
        this.instructor.assignGrade("Instructor","Test",2018,"HW1","John",90);
        int grade = this.instructor.getGrade("Test",2018,"HW1","John");
        assertEquals(grade,90);
    }

}
