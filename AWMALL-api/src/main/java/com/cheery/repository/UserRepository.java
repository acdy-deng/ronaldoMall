package com.cheery.repository;

import com.cheery.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @desc: 用户数据访问层接口
 * @className: UserRepository
 * @author: RONALDO
 * @date: 2019-02-23 16:31
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {

    /**
     * desc: 登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return 用户对象
     * @auther RONALDO
     * @date: 2019-02-23 16:50
     */
    User findByEmailAndPassword(String email, String password);

    /**
     * desc: 根据手机号码查询该用户是否存在
     *
     * @param email 邮箱
     * @return 用户对象
     * @auther RONALDO
     * @date: 2019-02-24 20:19
     */
    User findByEmail(String email);

    /**
     * desc: 根据手机号码修改密码
     *
     * @param email       邮箱
     * @param newPassword 新密码
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 08:52
     */
    @Modifying
    @Query(value = "UPDATE user SET password = ?,update_time = NOW() WHERE email = ?", nativeQuery = true)
    int updatePasswordByEmail(String newPassword, String email);

    /**
     * desc: 根据用户id修改密码
     *
     * @param id       用户id
     * @param password 新密码
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 11:58
     */
    @Modifying
    @Query(value = "UPDATE user SET password = ?,update_time = NOW() WHERE id = ?", nativeQuery = true)
    int updatePasswordByUserId(String password, Long id);

    /**
     * desc: 根据id和原密码查询
     *
     * @param id       用户id
     * @param password 原密码
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 13:26
     */
    int countByIdAndPassword(Long id, String password);

    /**
     * desc: 查询邮箱是否被其他用户占用
     *
     * @param emial 邮箱
     * @param id    用户id
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 14:16
     */
    @Query(value = "SELECT COUNT(1) FROM user WHERE email = ? AND id != ?", nativeQuery = true)
    int countByEmailIsOccupy(String emial, Long id);

    /**
     * desc: 查询是否被占用
     *
     * @param email 邮箱
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 14:19
     */
    int countByEmail(String email);

    /**
     * desc: 根据用户id查询用户信息
     *
     * @param id 用户id
     * @return User
     * @auther RONALDO
     * @date: 2019-03-05 14:28
     */
    User findById(Long id);
}
