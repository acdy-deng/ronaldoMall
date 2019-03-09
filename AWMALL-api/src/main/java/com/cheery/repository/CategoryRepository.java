package com.cheery.repository;

import com.cheery.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @desc: 产品分类数据访问层接口
 * @className: CategoryRepository
 * @author: RONALDO
 * @date: 2019-02-27 14:56
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CrudRepository<Category, Long> {

    /**
     * desc: 修改产品分类名称
     *
     * @param id   产品分类id
     * @param name 产品名称
     * @return ApiResult<?>
     * @auther RONALDO
     * @date: 2019-02-27 20:13
     */
    @Modifying
    @Query(value = "UPDATE category SET name = ? WHERE id = ?", nativeQuery = true)
    int updateCategoryNameById(String name, Long id);

    /**
     * desc:
     * 获取当前父类id下的所有产品,不递归查询
     * 如当前父分类ID为0 它有子类ID为1000 1001 1002 会全部查询出来 但是子类下面的子类不会查询出来了
     *
     * @param parentId 父类别id
     * @return List<Category>
     * @auther RONALDO
     * @date: 2019-02-27 20:41
     */
    List<Category> findAllByParentId(int parentId);

}
