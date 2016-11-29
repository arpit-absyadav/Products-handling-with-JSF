/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdBeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class categoryBean {
    private int id;
    private boolean editable;
    private String categoryName;

    @Override
    public String toString() {
        return  categoryName ;
    }
    
    
    
    DataSource ds;
    public categoryBean(){
          try {
            Context ctx= new InitialContext();
            ds=(DataSource)ctx.lookup("jdbc/regJNDI");  
        } catch (NamingException ex) {
            ex.printStackTrace();
        }        
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String addCategory(){
      
            PreparedStatement ps=null;
           try (Connection conn = ds.getConnection()) {
            ps=conn.prepareStatement("insert into category(categoryname) values(?)");
            ps.setString(1, categoryName);     

            ps.executeUpdate();
             // fetch max id
            ps=conn.prepareStatement("select * form category");
            ResultSet rs=ps.executeQuery(); 
            int catCount=0;
            while(rs.next()){
                catCount++;
            }
             System.out.println(catCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "showCategory";
    }
    public String deleteCategory(categoryBean delCategory){
        
           try (Connection conn = ds.getConnection()) {
                PreparedStatement ps =conn.prepareStatement("delete from category where ID = ?");
                System.out.println();
                ps.setInt(1, delCategory.getId());
                ps.executeUpdate();
                conn.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }

        return "showCategory"; 
    } 
    
  public String updateCategory(categoryBean toUpdateCategory){
      this.setId(toUpdateCategory.getId());
      this.setCategoryName(toUpdateCategory.getCategoryName());              
      return "updateCategory";
  }
  public String updateCategoryDb(){
      
      System.out.println( this.getId() );
      
        try (Connection conn = ds.getConnection()) {
                PreparedStatement ps =conn.prepareStatement("update CATEGORY set CATEGORYNAME = ? where ID=? ");
                ps.setInt(2, this.getId());
                ps.setString(1, this.getCategoryName() );
                ps.executeUpdate();
                conn.close();
                System.out.println();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
      return "showCategory";
  }
  

 
}
