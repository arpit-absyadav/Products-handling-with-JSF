/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reg.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.*;
import javax.annotation.ManagedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ABS-Brother
 */
@Named
@RequestScoped
@ManagedBean
public class SignUp {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String Phone;
    
    private String checkEmail;
    
    private boolean btnDisabled;
    
    DataSource ds;  
    public SignUp(){
        try {
            Context ctx=new InitialContext();
            ds=(DataSource)ctx.lookup("jdbc/regJNDI");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        
    }

    public boolean isBtnDisabled() {
        return btnDisabled;
    }

    public void setBtnDisabled(boolean btnDisabled) {
        this.btnDisabled = btnDisabled;
    }
    

    public String getCheckEmail() {
        return checkEmail;
    }

    public void setCheckEmail(String checkEmail) {
        this.checkEmail = checkEmail;
    }
    
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
    
        public  String Insert(){
          try {
            PreparedStatement ps=null;
            Connection conn=ds.getConnection();
            ps=conn.prepareStatement("INSERT INTO users(firstname,lastname,email,password,phone,username) VALUES(?,?,?,?,?,?)");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3,email);
            ps.setString(4, password);
            ps.setString(5, Phone);
            ps.setString(6, userName);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return "index";
    }       
        public void nameValidation(FacesContext fc, UIComponent c, Object value){
            if (
                    ((String)value).contains("^")||((String)value).contains("&") ||
                    ((String)value).contains("!")||((String)value).contains("@") ||
                    ((String)value).contains("#")||((String)value).contains("$") ||
                    ((String)value).contains("%")||((String)value).contains("*")) {
                throw new ValidatorException(new FacesMessage("Can't Contain Special Character"));
            }
        }
  

    public void email_email_Validation(FacesContext fc, UIComponent c, Object value){
       // boolean pass;
       // pass = false;
        if (value != null) {
            try {
                PreparedStatement ps;
                Connection conn;
                ResultSet rs;
                conn=ds.getConnection();
                if (conn != null) {
                    String query ="select email from users where email='"+value+"'";
                    ps=conn.prepareStatement(query);
                    rs=ps.executeQuery();
                    rs.next();  
                    checkEmail=rs.getString("email");
                    System.out.println();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (value.equals(checkEmail)) {
            //pass = true;
           // buttonDissabled(pass);
            throw  new ValidatorException(new FacesMessage(""+value+" :- already exist. Please try another"));
            
        }
    }
   /* public void buttonDissabled(boolean check){
        btnDisabled = check != false;
    }*/
}
                
               
            
                                  
           
                    

            
        
    
