/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdBeans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
@ManagedBean
public class total_data {

    private int total_Product;
    private int total_Category;
    DataSource ds;
    public total_data()  {
         try {
            Context ctx= new InitialContext();
            ds=(DataSource)ctx.lookup("jdbc/regJNDI");  
        } catch (NamingException ex) {
            ex.printStackTrace();
        }     
    }

    public int getToatal_Product() {
        return total_Product;
    }

    public void setToatal_Product(int total_Product) {
        this.total_Product = total_Product;
    }

    public int getTotal_Category() {
        return total_Category;
    }

    public void setTotal_Category(int total_Category) {
        this.total_Category = total_Category;
    }
    
    public int count_Product(){
        try(Connection con =ds.getConnection()) {
          
            Statement stmt;
            stmt=con.createStatement();
            String sql="select COUNT(*) as ROWCOUNT from products";
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()) {                
                total_Product=rs.getInt("ROWCOUNT");
                System.out.println(total_Product);
                System.out.println("heloo");
            }           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total_Product;
    }
    
}
