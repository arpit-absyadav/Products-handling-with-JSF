/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reg.Users;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author ABS-Brother
 */
@Named
@ManagedBean
@SessionScoped
public class loginBean implements Serializable//implements ValueChangeListener{
{
    private String user;
    private String password;
    private  String checkUser;
    private String checkPass;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
    }
    
    DataSource ds;
    public loginBean(){
        try {
            Context ctx = new  InitialContext();
            ds=(DataSource)ctx.lookup("jdbc/regJNDI");                       
            
        } catch (NamingException ex) {
           ex.printStackTrace();
        }
    }
    public void databaseValidation(String userName){
        if (userName != null) {
            try {
                PreparedStatement ps;
                Connection conn;
                ResultSet rs;
                conn=ds.getConnection();
                if (conn != null) {
                    String query="select username, pasword from login where username ='"+userName+"'";
                    ps=conn.prepareStatement(query);
                    rs=ps.executeQuery();
                    rs.next();
                     checkUser=rs.getString("username");
                    checkPass=rs.getString("pasword");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    public String userValidation(){
        databaseValidation(user);
        FacesContext facesctx =FacesContext.getCurrentInstance();
        if (user.equals(checkUser)) {
            if (password.equals(checkPass)) {
                return "admin-index";
            }else{
                facesctx.addMessage("loginform", new FacesMessage("Incorrect Credentials"));
                return null;
            }
        }else{
           facesctx.addMessage("loginform", new FacesMessage("Incorrect Credentials"));
                return null;
        }
    }
    

    /*@Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        
    }*/

   
}
