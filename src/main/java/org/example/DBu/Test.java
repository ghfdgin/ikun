package org.example.DBu;

import org.example.dao.StudentDAO;
import org.example.dao.impl.StudentDAOImpl;
import org.example.pojo.Student;
import java.util.Date;

/**
 * @author xhb
 * @date 2024/7/22 16:08
 */
public class Test {
    public static void main(String[] args) {
//        Student s1 = new Student(0,"无畏","男",
//                new Date(0),"南京",110);
        StudentDAO studentDAO = new StudentDAOImpl();
//        studentDAO.insert(s1);
        Student s2 = new Student(6,"不留情","女",new Date(),"宿迁", 119L);
        studentDAO.update(s2);
    }
}
