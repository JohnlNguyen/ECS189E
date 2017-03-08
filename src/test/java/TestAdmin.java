import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;



import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by John on 2/28/17.
 */
public class TestAdmin {
    private IAdmin admin;
    private IStudent student;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    /*
    Test for creating a class in the past
     */
    @Test
    public void testMakeClassPast() {
        this.admin.createClass("Test", 2015, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2015));
    }
    /*
    Test for creating a class with zero capacity
     */
    @Test
    public void testMakeClassZero() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    /*
    Test for negative class capacity
     */
    @Test
    public void testMakeClassNegative() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    /*
    Test for creating the same class in the same year
     */
    @Test
    public void testMakeClassNotUnique() {
        this.admin.createClass("Test", 2017, "Instructor", 5);
        this.admin.createClass("Test", 2017, "Instructor", 10);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    /*
    Test for instructor teaching more than 2 classes
     */
    @Test
    public void testMakeClassSameInstructor() {
        this.admin.createClass("ECS189E", 2017, "Instructor", 5);
        this.admin.createClass("ECS160", 2017, "Instructor", 10);
        this.admin.createClass("ECS145", 2017, "Instructor", 15);
        boolean all_exist =  this.admin.classExists("ECS189E", 2017) && this.admin.classExists("ECS160", 2017)
                && this.admin.classExists("ECS145", 2017);
        assertFalse(all_exist);
    }
    /*
    Test for instructor teaching 3 classes in different years
     */
    @Test
    public void test3SameInstructor() {
        this.admin.createClass("ECS189E", 2017, "Instructor", 5);
        this.admin.createClass("ECS160", 2018, "Instructor", 10);
        this.admin.createClass("ECS145", 2017, "Instructor", 15);
        boolean exists = this.admin.classExists("ECS189E", 2017) && this.admin.classExists("ECS160", 2018)
            && this.admin.classExists("ECS145", 2017);
        assertTrue(exists);
    }
    /*
    Test if instructor can teach 2 classes each year
     */
    @Test
    public void testMake2SameInstructor(){
        this.admin.createClass("ECS189E", 2017, "Instructor", 5);
        this.admin.createClass("ECS160", 2017, "Instructor", 10);
        boolean both = this.admin.classExists("ECS189E", 2017) && this.admin.classExists("ECS160", 2017);
        boolean same = this.admin.getClassInstructor("ECS189E",2017).equals("Instructor") &&
                this.admin.getClassInstructor("ECS160",2017).equals("Instructor");
        assertTrue(both && same);
    }

    @Test
    public void testCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 5);
        this.admin.changeCapacity("Test",2017,10);
        int cap = this.admin.getClassCapacity("Test",2017);
        assertEquals(cap,10);
    }
    @Test
    public void testCapacityNeg() {
        this.admin.createClass("Test", 2017, "Instructor", 5);
        this.admin.changeCapacity("Test",2017,-1);
        int cap = this.admin.getClassCapacity("Test",2017);
        assertNotEquals(cap,-1);
    }
    /*
    Check for changing capacity to less than the number of students currently in the class
     */
    @Test
    public void testCapacityLess() {
        this.admin.createClass("Test", 2017, "Instructor", 5);
        this.student.registerForClass("John","Test",2017);
        this.student.registerForClass("Jeff","Test",2017);
        this.student.registerForClass("Vincent","Test",2017);
        this.admin.changeCapacity("Test",2017,1);
        int cap = this.admin.getClassCapacity("Test",2017);
        assertNotEquals(cap,1);
    }

    @Test
    public void testCapacityLess2() {
        this.admin.createClass("Test", 2017, "Instructor", 5);
        this.student.registerForClass("John","Test",2017);
        this.student.registerForClass("Jeff","Test",2017);
        this.student.registerForClass("Vincent","Test",2017);
        this.admin.changeCapacity("Test",2017,3);
        assertEquals(this.admin.getClassCapacity("Test",2017),5);
    }
    @Test
    public void testInstructor() {
        this.admin.createClass("Test", 2017, "Instructor", 5);
        assertEquals(this.admin.getClassInstructor("Test",2017),"Instructor");
    }

    @Test
    public void testInstructorNULL() {
        this.admin.createClass("Test", 2017, null, 5);
        assertNotEquals(this.admin.getClassInstructor("Test",2017),null);
    }



}
