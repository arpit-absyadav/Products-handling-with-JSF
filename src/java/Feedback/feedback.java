/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Feedback;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author ABS-Brother
 */
@ManagedBean
@Named
//@RequestScoped
@SessionScoped
public class feedback {

    
    private int product_id;
    private String user;
    private String feedback;
    private String date;
    private String time;
    
    
    private List<feedback> feed_list;
 
 
    
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    DataSource ds;
    public feedback() {
        try {
            Context ctx = new InitialContext();
            ds= (DataSource)ctx.lookup("jdbc/regJNDI");
                    } catch (NamingException ex) {
           ex.printStackTrace();
        }
    }
    public String prod_Feedback(){       
        
        PreparedStatement ps =null;
        Calendar calender  =Calendar.getInstance();
          java.sql.Date feed_date=new java.sql.Date(calender.getTime().getTime());
          java.sql.Time feed_time =new java.sql.Time(calender.getTime().getTime());
        try(Connection conn =ds.getConnection()) {
            ps = conn.prepareStatement(" insert into feedback(username,feedback,time,date,prodid) values(?,?,?,?,?)");
            ps.setString(1, user);
            ps.setString(2, feedback);
            ps.setTime(3, feed_time);
            ps.setDate(4, feed_date);
            ps.setInt(5, product_id);
            ps.executeUpdate();
           
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
        return "showProduct";
    }
    public List<feedback> getFeed_list() {
        //String id=String.valueOf(product_id);
        feed_list=new ArrayList();
        try(Connection conn =ds.getConnection()) {
            Statement stmt =conn.createStatement();
            String query="select username,feedback,time,date from feedback where cast(prodid as char)= '" +product_id+ "'";
            ResultSet rs=stmt.executeQuery(query);          
            while (rs.next()) {                
                feedback feed=new feedback();
                feed.setUser(rs.getString(1));
                feed.setFeedback(rs.getString(2));
                feed.setTime(rs.getString(3));
                feed.setDate(rs.getString(4));
                this.feed_list.add(feed);
                System.out.println(feed_list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.feed_list;
    }

    public void setFeed_list(List<feedback> feed_list) {
        this.feed_list = feed_list;
    }
    
    
    
}
