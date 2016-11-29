/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdBeans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
//@ViewScoped
@ManagedBean
public class showInfo {
     private List<categoryBean> categoryList;
     private List<productBean> prodList =new ArrayList<>();
    int categoryCount=0;

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }
    
   
     
     
      public  void setProdList(List<productBean> prodList){
        this.prodList=prodList;     
        
    }
     
      public void setCategoryList(List<categoryBean> categoryList){
          this.categoryList=categoryList;
      }
/*String products[]=prodList.toArray(new String[prodList.size()]);
 String categories[]=categoryList.toArray(new String[categoryList.size()]);

  */
    
    DataSource ds;
    public showInfo(){
        
        try {
            Context ctx= new InitialContext();
            ds=(DataSource)ctx.lookup("jdbc/regJNDI");  
        } catch (NamingException ex) {
            ex.printStackTrace();
        }     
    }
    public List<categoryBean> getCategoryList(){
         // categoryList=this.fetchCategory();
         
         categoryList = new ArrayList();
         
         try{ 
             try (Connection conn = ds.getConnection()) {
                 Statement stmt=conn.createStatement();
                 String query="select id, categoryName from category";
                 ResultSet rs=stmt.executeQuery(query);
                     while (rs.next()) {
                     categoryBean categoryBean = new categoryBean();
                     
                     categoryBean.setCategoryName(rs.getString(2));
                     categoryBean.setId(rs.getInt(1))   ;
                     this.categoryList.add(categoryBean);
                     //System.out.println(categoryList);
                     categoryCount++;
                    //System.out.println(categoryCount);
                     } }
       }catch(Exception e){
           e.printStackTrace();
       }
        return this.categoryList;               
          
      }
   public List<productBean> getProdList(){
       prodList=new ArrayList();
        
            try (Connection conn = ds.getConnection()) {
               Statement stmt=conn.createStatement();
               String query="select id, prodname,prodcategory,proddescription,prodprice from products";
               ResultSet rs=stmt.executeQuery(query);
               while (rs.next()) {
                   productBean prodBean =new productBean();
                   prodBean.setProdId(rs.getInt(1));
                   prodBean.setProdName(rs.getString(2));
                   prodBean.setProdCategory(rs.getString(3));
                   prodBean.setProdDescription(rs.getString(4));
                   prodBean.setProdPrice(rs.getDouble(5));
                   prodList.add(prodBean);
               }           
       }catch(Exception e){
           e.printStackTrace();
       }
        return prodList;        
    } 
   
}
    
   
         
    
  

