import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by John on 2/28/17.
 */
public class TestStudent {

    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        //Testing for a student class here
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
        this.admin.createClass("ECS189E",2017,"Prem",2);
    }

    @Test
    public void testRegistrationClass() {
        this.student.registerForClass("John","ECS189E",2017);
        assertTrue(this.student.isRegisteredFor("John","ECS189E",2017));
    }



    @Test
    public void testRegistrationFull() {
        this.student.registerForClass("Jeff","ECS189E",2017);
        this.student.registerForClass("Alex","ECS189E",2017);
        this.student.registerForClass("Vincent","ECS189E",2017);
        assertFalse(this.student.isRegisteredFor("Vincent","ECS189E",2017));
    }
    @Test
    public void testRegistrationFull2() {
        this.student.registerForClass("Jeff","ECS189E",2017);
        this.student.registerForClass("Alex","ECS189E",2017);
        this.student.registerForClass("Vincent","ECS189E",2017);
        this.student.registerForClass("Bob","ECS189E",2017);
        boolean both = this.student.isRegisteredFor("Bob","ECS189E",2017) &&
                this.student.isRegisteredFor("Vincent","ECS189E",2017);
        assertFalse(both);
    }
    @Test
    public void testClassNotExist() {
        this.student.registerForClass("Jeff","ECS145",2017);
        assertFalse(this.student.isRegisteredFor("Jeff","ECS145",2017));
    }

    @Test
    public void testsubmitHomework() {
        this.student.registerForClass("John","ECS189E",2017);
        this.instructor.addHomework("Prem","ECS189E",2017,"HW1","Test");
        this.student.submitHomework("John","HW1","ABC","ECS189E",2017);
        assertTrue(this.student.hasSubmitted("John","HW1","ECS189E",2017));
    }
    /*
    Test when the student does not exist
     */
    @Test
    public void testNoStudentHomework() {
        this.student.submitHomework("John","HW1","ABC","ECS189E",2017);
        assertFalse(this.student.hasSubmitted("John","HW1","ECS189E",2017));
    }
    /*
    Test for no homework created
     */
    @Test
    public void testNoHomework() {
        this.student.registerForClass("John","ECS189E",2017);
        this.student.submitHomework("John","HW1","ABC","ECS189E",2017);
        assertFalse(this.student.hasSubmitted("John","HW1","ECS189E",2017));
    }

    @Test
    public void testdropClass(){
        this.student.registerForClass("Bob","ECS189E",2017);
        this.student.dropClass("Bob","ECS189E",2017);
        assertFalse(this.student.isRegisteredFor("Bob","ECS189E",2017));
    }

    @Test
    public void testdropClass2(){
        this.admin.createClass("ECS145",2017,"Instructor",10);
        this.admin.createClass("ECS145",2018,"Instructor",10);
        this.student.registerForClass("Bob","ECS145",2017);
        this.student.registerForClass("Bob","ECS145",2018);
        this.student.dropClass("Bob","ECS189E",2017);
        assertTrue(this.student.isRegisteredFor("Bob","ECS145",2018));
    }


}
