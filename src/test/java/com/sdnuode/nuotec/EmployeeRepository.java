package com.sdnuode.nuotec;

import com.sdnuode.nuotec.hibernate.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * 根据部门ID获取员工数量
     */
    int countByDepartmentId(Long departmentId);

    /**
     * 根据部门ID分页查询
     */
    Page<Employee> queryByDepartmentId(Long departmentId, Pageable pageable);

    /**
     * 根据员工ID升序查询前10条
     */
    List<Employee> readTop10ByOrderById();

    /**
     * 根据员工姓名取第一条记录
     */
    Employee getFirstByName(String name, Sort sort);

//    /**
//     * 联表查询
//     */
//    @Query("select e.id as employeeId,e.name as employeeName,d.id as departmentId,d.name as departmentName from Employee e , Department d where e.id= ?1 and d.id= ?2")
//    EmployeeDetail getEmployeeJoinDepartment(Long eid, Long did);

    /**
     * 修改指定ID员工的姓名
     */
    @Modifying
    @Transactional(timeout = 10)
    @Query("update Employee e set e.name = ?1 where e.id = ?2")
    int modifyEmployeeNameById(String name, Long id);

    /**
     * 删除指定ID的员工
     */
    @Transactional(timeout = 10)
    @Modifying
    @Query("delete from Employee where id = ?1")
    void deleteById(Long id);

}