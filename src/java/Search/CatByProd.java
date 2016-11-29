/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import ProdBeans.categoryBean;
import ProdBeans.productBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
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
@RequestScoped
@Named
public class CatByProd{
    
    private List<categoryBean> categoryList;
    private List<productBean> prodList =new ArrayList<>();

        private String CategoryName;

    /**
     * Get the value of CategoryName
     *
     * @return the value of CategoryName
     */
    public String getCategoryName() {
        return CategoryName;
    }

    /**
     * Set the value of CategoryName
     *
     * @param CategoryName new value of CategoryName
     */
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    
    DataSource ds;
    public CatByProd(){
        
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
                     //System.out.println(categoryCount);
                     } }
       }catch(Exception e){
             System.out.println(e);
     }
        return this.categoryList;               
          
      }

    public void setCategoryList(List<categoryBean> categoryList) {
        this.categoryList = categoryList;
    }   
    
    public List<productBean> getProdList(){
       prodList=new ArrayList();
         try{ 
               Connection conn = ds.getConnection();
               Statement stmt=conn.createStatement();
               
               String query;
               
               if( this.CategoryName == null )
                    query="select id, prodname,prodcategory,proddescription,prodprice from products";
               else
                   query="select id, prodname,prodcategory,proddescription,prodprice from products where prodcategory= '" + this.CategoryName + "'";
               ResultSet rs=stmt.executeQuery(query);
               while (rs.next()) {
                   productBean prodBean =new productBean();
                   prodBean.setProdId(rs.getInt(1));
                   prodBean.setProdName(rs.getString(2));
                   prodBean.setProdCategory(rs.getString(3));
                   prodBean.setProdDescription(rs.getString(4));
                   prodBean.setProdPrice(rs.getDouble(5));
                   prodList.add(prodBean);
                   
                   conn.close();
               }           
       }catch(Exception e){
           e.printStackTrace();
       }
        return prodList;        
    
    }  
    
    public void valueChange( ValueChangeEvent e )
    {
//        if(e != null)
            //
//        if(e != null)
//            System.out.println( e.getNewValue() );
        
        System.out.println( e.getNewValue() );
        
        this.CategoryName = e.getNewValue().toString();
    }

    public void setProdList(List<productBean> prodList) {
        this.prodList = prodList;
    }
     
     
}
