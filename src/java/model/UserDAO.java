/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.DbUtils;
import jakarta.servlet.http.HttpServletRequest;
/**
 *
 * @author an0other
 */
public class UserDAO {
    public static final String GET_USER_BY_USERNAME="select * from users where username=?";
    public static final String CREATE_NEW_USER="insert into users (username, password, email, full_name, role, status) values (?, ?, ?, ?, ?, ?)";
    
    public UserDTO getUserByUsername(String username){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql=GET_USER_BY_USERNAME;
        UserDTO user=null;
        try {
            conn=DbUtils.getConnection();
            
            ps=conn.prepareStatement(sql);
            
            ps.setString(1, username);
            
            rs=ps.executeQuery();
            
            if (rs.next()){
                String password=rs.getString("password");
                String email=rs.getString("email");
                String fullname=rs.getString("full_name");
                String role=rs.getString("role");
                String status_string=rs.getString("status");
                boolean status= "1".equals(status_string);
                user=new UserDTO(username, password, email, fullname, role, status);
            }
        } catch (Exception e) {
            System.err.print(e);
        } finally {
            DbUtils.close(conn, ps, rs);
        }
        return user;
    }
    
    public boolean login(String username, String password){
        UserDTO user=this.getUserByUsername(username);
        
        if (user!=null && user.getPassword().equals(password) && user.isStatus()) return true;
        return false;
    }
    
    public boolean isExist(String para, String value){
        boolean ans=false;
        String sql="select * from users where "+para+"=?";
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try {
            conn=DbUtils.getConnection();
            
            ps=conn.prepareStatement(sql);
            ps.setString(1, value);
            
            rs=ps.executeQuery();
            if (rs.next()) ans=true;
        } catch (Exception e) {
        } finally {
            DbUtils.close(conn, ps, rs);
        }
        return ans;
    }
    
    public boolean createNewUser(UserDTO user, HttpServletRequest request){
        boolean isDone=false;
        boolean continuous=true;
        if (isExist("username", user.getUsername())){
            //get message
            request.setAttribute("UserNameAlert", "This username is already exist!");
            continuous=false;
        }
        if (isExist("email", user.getEmail())){
            //get message
            request.setAttribute("EmailAlert", "This email is already exist!");
            continuous=false;
        }
        if (continuous==false) return false;
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try {
            conn=DbUtils.getConnection();
            
            ps=conn.prepareStatement(CREATE_NEW_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getRole());
            ps.setInt(6, 1);
            
            int rowsAffected=ps.executeUpdate();
            
            if (rowsAffected>0) isDone=true;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            DbUtils.close(conn, ps, rs);
        }
        return isDone;
    }
}
