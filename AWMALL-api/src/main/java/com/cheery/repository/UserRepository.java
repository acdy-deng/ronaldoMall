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
     * @param phone    手机号码
     * @param password 密码
     * @return 用户对象
     * @auther RONALDO
     * @date: 2019-02-23 16:50
     */
    User findByPhoneAndPassword(String phone, String password);

    /**
     * desc: 根据手机号码查询该用户是否存在
     *
     * @param phone 手机号码
     * @return 用户对象
     * @auther RONALDO
     * @date: 2019-02-24 20:19
     */
    User findByPhone(String phone);

    /**
     * desc: 根据手机号码、密保问题、密保答案查询该用户是否存在
     *
     * @param phone    手机号码
     * @param question 密保问题
     * @param answer   密保答案
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-24 21:17
     */
    int countByPhoneAndQuestionAndAnswer(String phone, String question, String answer);

    /**
     * desc: 根据手机号码修改密码
     *
     * @param phone       手机号码
     * @param newPassword 新密码
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 08:52
     */
    @Modifying
    @Query(value = "UPDATE user SET password = ?,update_time = NOW() WHERE phone = ?", nativeQuery = true)
    int updatePasswordByPhone(String newPassword, String phone);

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
     * desc: 查询邮箱是否占用
     *
     * @param emial 邮箱
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 14:13
     */
    int countByEmail(String emial);

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
     * @param phone 手机号码
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 14:19
     */
    int countByPhone(String phone);

    /**
     * desc: 查询是否被其他用户占用
     *
     * @param phone 手机号码
     * @param id    用户id
     * @return 受影响的行数
     * @auther RONALDO
     * @date: 2019-02-25 14:23
     */
    @Query(value = "SELECT COUNT(1) FROM user WHERE phone = ? AND id != ?", nativeQuery = true)
    int countByPhoneIsOccupy(String phone, Long id);

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
