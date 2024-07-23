package org.example.dao.impl;

import org.example.DBu.DBUtil;
import org.example.dao.StudentDAO;
import org.example.pojo.Student;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/** wwefw
 * @author xhb
 * @date 2024/7/22 15:52
 */
public class StudentDAOImpl implements StudentDAO {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void insert(Student student) {
        try {
            Connection c = DBUtil.getConnection();
            Statement st = c.createStatement();
            //传参不方便 性能较差 存在sql注入攻击
            //prepareStatement 先编译后传参 效率高 传参方便 避免攻击
            String sql = "insert into student(name,gender,birthday,addr,qqnumber)"+
                    "values('%s','%s','%s','%s','%d')";
            sql = String.format(sql,student.getName(),student.getGender(),
                    sdf.format(student.getBirthday()),student.getAddr(),student.getQqnumber());

            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection c = null;
        String sql = "delete from student where id = ?";
        try {
            c = DBUtil.getConnection();
            //关闭自动提交
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            ps.executeUpdate();
        }catch(SQLException e){
            try {
                c.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }finally{
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(Student student) {
        String sql = "update student set name=?,gender=?,birthday=?,addr=?,qqnumber=? where id=?";
        try (
            Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ){
            ps.setString(1,student.getName());
            ps.setString(2,student.getGender());
            ps.setDate(3, new Date(student.getBirthday().getTime()));
            ps.setString(4,student.getAddr());
            ps.setLong(5,student.getQqnumber());
            ps.setInt(6,student.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer count() {
        String sql = "select count(*) from student";
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ){
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //每次从结果读取一行数据
                //根据读取的数据不同 调用不同的get方法
                //可以不写字段出现顺序 而写字段名称
                return rs.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public Student findById(Integer id) {
        String sql = "select * from student where id = ?";
        Student student = null;
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                student = new Student();
                student.setId(id);
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setBirthday(rs.getDate("birthday"));
                student.setAddr(rs.getString("addr"));
                student.setQqnumber(rs.getLong("qqnumber"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        return findWithLimit(0,Integer.MAX_VALUE);
    }

    @Override
    public List<Student> findByNameLike(String name) {
        return findByNameLikeWithLimit(name,0,Integer.MAX_VALUE);
    }

    @Override
    public List<Student> findByNameLikeWithLimit(String name, int start, int limit) {
        String sql = "select * from student where name like concat('%',?,'%') limit ?,?";
        List<Student> stus = new ArrayList<>();
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ){
            ps.setString(1,name);
            ps.setInt(2,start);
            ps.setInt(3,limit);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Student s =new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setGender(rs.getString("gender"));
                s.setAddr(rs.getString("addr"));
                s.setQqnumber(rs.getLong("qqnumber"));
                s.setBirthday(rs.getDate("birthday"));
                stus.add(s);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return stus.size() == 0?null:stus;
    }

    @Override
    public List<Student> findWithLimit(int start, int limit) {
        String sql = "select * from student limit ?,?";
        List<Student> stus = new ArrayList<>();
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ){
            ps.setInt(1,start);
            ps.setInt(2,limit);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Student s =new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setGender(rs.getString("gender"));
                s.setAddr(rs.getString("addr"));
                s.setQqnumber(rs.getLong("qqnumber"));
                s.setBirthday(rs.getDate("birthday"));
                stus.add(s);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return stus;
    }
}
