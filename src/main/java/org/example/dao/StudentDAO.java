package org.example.dao;

import org.example.pojo.Student;

import java.util.List;

/**
 * @author xhb
 * @date 2024/7/22 15:34
 */
public interface StudentDAO {
    /**插入学生信息
     * @param student 要插入的学生信息 其中主键无序提供
     */
    void insert(Student student);

    /**根据id删除
     * @param id 要删除的学生id
     */
    void delete(Integer id);

    /**  学生的id修改学生的所有信息
     * @param student 提供学生的id和修改的字段值
     */
    void update(Student student);

    /**统计表记录总数
     * @return 返回表记录总数 为0 则没有记录
     */
    Integer count();

    /** 根据id查找对应的id信息
     * @param id 要获取的学生id
     * @return 返回对应的学生记录 不存在weinull
     */
    Student findById(Integer id);

    /**获取学生所有信息
     * @return 返回所有信息
     */
    List<Student> findAll();

    /** 根据名称模糊查询学生
     * @param name 模糊查询关键字
     * @return 返回查询结果
     */
    List<Student> findByNameLike(String name);

    /** 根据名称 进行分页模糊查询
     * @param name 关键字
     * @param start limit其实函数
     *      * @param limit limit行数
     * @return 返回学生对象 找不到为null
     */
    List<Student> findByNameLikeWithLimit(String name,int start,int limit);

    /**
     * @param start
     * @param limit
     * @return
     */
    List<Student> findWithLimit(int start,int limit);
}
