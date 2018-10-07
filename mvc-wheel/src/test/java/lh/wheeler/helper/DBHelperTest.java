package lh.wheeler.helper;

import lh.wheeler.entity.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelperTest {

    @Before
    public void init() {
        DBHelper.executeSqlFile("init-demotest.sql");
    }

    @Test
    public void testDelete() {
        DBHelper.delete(1, Student.class);
        DBHelper.delete(3, Student.class);
    }

    @Test
    public void testInsert() {
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("id", 3);
        row.put("age", 21);
        row.put("name", "lh+1");
        DBHelper.insert(row, Student.class);
    }

    @Test
    public void testUpdate() {
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("age", 20);
        DBHelper.update(row, 2, Student.class);
    }

    @Test
    public void testQuery() {
        Student s = DBHelper.queryEntity("select * from student where id = 1", Student.class);
        List<Student> ss = DBHelper.queryEntityList("select * from student", Student.class);

        System.out.println(s);
        System.out.println(ss);
    }
}
