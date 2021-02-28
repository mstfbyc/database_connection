package com.bilgeadam;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SELECT, INSERT, UPDATE, DELETE
 */
public class App {
    private static final String CONNECTION_STRING ="jdbc:oracle:thin:@//localhost:1521/ORCLPDB1.localdomain";
    private static final String DB_USER_NAME ="HR";
    private static final String DB_USER_PASSWORD ="oracle";

    public static void main(String[] args) throws ClassNotFoundException {
        int result = 0;
/*        Employee employee = Employee.builder()
                .employeeId(8001)
                .firstName("Mustafa")
                .lastName("BOYACI")
                .email("EBOYACI")
                .phoneNumber("5325323232")
                .hireDate(Timestamp.valueOf(LocalDateTime.now()))
                .jobId("IT_PROG")
                .salary(7500.00)
                .commission(0.0)
                .managerId(102)
                .departmentId(60)
                .build();*/
        //result = addEmployee(employee);
        //result = updateEmployeeCommission(8001,0.01);
        result = deleteEmployee(8001);
        if(result >0 ){
            List<Employee> employeeList = getEmployeeList();
            employeeList.forEach(System.out::println);
            System.out.println("KAYIT İşlemi Başarılı...");
        }else{
            System.out.println("Database kayıt işlemi yapılamadı");
        }
    }

    public static int deleteEmployee(Integer employeeId){
        int result = 0;
        String sql = "DELETE FROM employees WHERE EMPLOYEE_ID = ?" ;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING,DB_USER_NAME,DB_USER_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,employeeId);
            result = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static int updateEmployeeCommission(Integer employeeId, Double commission)  {

        int result = 0;
        String sql = "UPDATE employees SET COMMISSION_PCT = ? WHERE EMPLOYEE_ID = ?";
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_STRING,DB_USER_NAME,DB_USER_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1,commission);
            statement.setInt(2,employeeId);
            result = statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static int addEmployee(Employee employee) throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        int result = 0;
        try {
            Connection con = DriverManager.getConnection(CONNECTION_STRING,DB_USER_NAME,DB_USER_PASSWORD);
            String sql = "INSERT INTO employees VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,employee.getEmployeeId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3,employee.getLastName());
            statement.setString(4,employee.getEmail());
            statement.setString(5, employee.getPhoneNumber());
            statement.setTimestamp(6,employee.getHireDate());
            statement.setString(7, employee.getJobId());
            statement.setDouble(8,employee.getSalary());
            statement.setDouble(9,employee.getCommission());
            statement.setInt(10,employee.getManagerId());
            statement.setInt(11,employee.getDepartmentId());
            result = statement.executeUpdate();
            statement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    private static List<Employee> getEmployeeList() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        List<Employee> employeeList = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(CONNECTION_STRING,DB_USER_NAME,DB_USER_PASSWORD);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from employees order  by EMPLOYEE_ID");


          while (rs.next()){
              employeeList.add(Employee.builder()
                      .employeeId(rs.getInt(1))
                      .firstName(rs.getString(2))
                      .lastName(rs.getString(3))
                      .email(rs.getString(4))
                      .phoneNumber(rs.getString(5))
                      .hireDate(rs.getTimestamp(6))
                      .jobId(rs.getString("JOB_ID"))
                      .salary(rs.getDouble("SALARY"))
                      .commission(rs.getDouble("COMMISSION_PCT"))
                      .managerId(rs.getInt("MANAGER_ID"))
                      .departmentId(rs.getInt("DEPARTMENT_ID"))
                      .build());
            }
            statement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeeList;
    }
}
