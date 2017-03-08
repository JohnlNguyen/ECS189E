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
public class TestInstructor {
    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;
    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
        //Making class and student
        this.admin.createClass("ECS189E",2017,"Prem",5);
        this.student.registerForClass("John","ECS189E",2017);
    }
    /*
    Test if Homework exists
     */
    @Test
    public void testHomework() {
        this.instructor.addHomework("Prem","ECS189E",2017,"HW1","Test");
        assertTrue(this.instructor.homeworkExists("ECS189E", 2017,"HW1"));
    }

    /*
    Instructor did not assigned to this class
     */
    @Test
    public void testHomework2() {
        this.instructor.addHomework("Vincent","ECS189E",2017,"HW1","Test");
        assertFalse(this.instructor.homeworkExists("ECS189E", 2017,"HW1"));
    }

    /*
    Test if homework exists if the class year is wrong
     */
    @Test
    public void testHomework3() {
        this.instructor.addHomework("Prem","ECS189E",2018,"HW1","Test");
        assertFalse(this.instructor.homeworkExists("ECS189E", 2018,"HW1"));
    }

    /*
    Test if homework exists if the this class is wrong
     */
    @Test
    public void testHomework4() {
        this.admin.createClass("Test",2019,"Instructor",10);
        this.instructor.addHomework("Instructor","Test",2029,"HW2","Test");
        assertFalse(this.instructor.homeworkExists("Test", 2019,"HW2"));
    }

    @Test
    public void testAssignGrade() {
        this.instructor.addHomework("Prem","ECS189E",2017,"HW1","Test");
        this.student.submitHomework("John","HW1","ABC","ECS189E",2017);
        this.instructor.assignGrade("Prem","ECS189E",2017,"HW1","John",100);
        Integer i = new Integer(this.instructor.getGrade("ECS189E", 2017,"HW1","John"));
        assertTrue(i.equals(100));
    }

    @Test
    public void testAssignGrade2() {
        this.instructor.addHomework("Prem","ECS189E",2017,"HW1","Test");
        this.student.submitHomework("John","HW1","ABC","ECS189E",2017);
        this.instructor.assignGrade("Vincent","ECS189E",2017,"HW1","John",100);
        Integer i = new Integer(this.instructor.getGrade("ECS189E", 2017,"HW1","John"));
        assertFalse(i.equals(100));
    }
    /*
    Test for assigning homework but student did not submit
     */
    @Test
    public void testAssignGradeWithNoSubmit() {
        this.instructor.addHomework("Prem","ECS189E",2017,"HW1","Test");
        this.instructor.assignGrade("Prem","ECS189E",2017,"HW1","John",100);
        boolean submitted = this.student.hasSubmitted("John","HW1","ECS189E",2017);
        Integer i = new Integer(this.instructor.getGrade("ECS189E", 2017,"HW1","John"));
        assertFalse(i.equals(100) && submitted);
    }
    /*
    Test for assigning homework but student did not submit
     */
    @Test
    public void testNotSubmitted(){
        this.instructor.addHomework("Prem","ECS189E",2017,"HW1","Test");
        this.instructor.assignGrade("Prem","ECS189E",2017,"HW1","John",100);
        Integer i = new Integer(this.instructor.getGrade("ECS189E", 2017,"HW1","John"));
        assertFalse(i.equals(100));
    }
    /*
    Test for assigning homework but student did not submit
     */
    @Test
    public void testNegativeGrade() {
        this.instructor.addHomework("Prem","ECS189E",2017,"HW1","Test");
        this.student.submitHomework("John","HW1","ABC","ECS189E",2017);
        this.instructor.assignGrade("Prem","ECS189E",2017,"HW1","John",-1);
        Integer i = new Integer(this.instructor.getGrade("ECS189E", 2017,"HW1","John"));
        assertFalse(i.equals(-1));
    }

}
