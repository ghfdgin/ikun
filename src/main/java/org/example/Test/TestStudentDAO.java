package org.example.Test;

import org.example.dao.StudentDAO;
import org.example.dao.impl.StudentDAOImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xhb
 * @date 2024/7/23 11:02
 */
public class TestStudentDAO {
    //@Test 将指定方法标记为测方法 不依赖于main方法直接运行
    //@Before 在所有代码运行前执行 环境初始化
    //@After 锁头代码运行后执行 资源回收
    private StudentDAO studentDAO;
    @Before
    public void init(){
        studentDAO = new StudentDAOImpl();
    }
    @Test
    public void testFindByid(){
        Assert.assertNotNull(studentDAO.findById(1));
        System.out.println(studentDAO.findById(1));
    }
    @Test
    public void testCount(){
        Assert.assertEquals(12l,(long)studentDAO.count());
    }
    @Test
    public void testFindAll(){
        Assert.assertNotNull(studentDAO.findAll());
    }
    @Test
    public void testFindByNameLike(){
        Assert.assertNotNull(studentDAO.findByNameLike("总"));
    }
    @Test
    public void testFindWithLimit(){
        Assert.assertNotNull(studentDAO.findWithLimit(0,3));
    }
    @Test
    public void testFindByNameLikeWithLimit(){
        Assert.assertNotNull(studentDAO.findByNameLikeWithLimit("总",0,5));
    }
}
