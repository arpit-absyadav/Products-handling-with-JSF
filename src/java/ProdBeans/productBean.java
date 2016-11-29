        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdBeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;

/**
 *
 * @author ABS-Brother
 */
@Named
@RequestScoped
@ManagedBean
public class productBean {
    private int prodId;
    private String prodName;
    private String prodCategory;
    private String prodDescription;
    private double prodPrice;
    private Part image;
    
    
   
    
    DataSource ds;
    public productBean(){
        try {
            Context ctx= new InitialContext();
            ds=(DataSource)ctx.lookup("jdbc/regJNDI");  
        } catch (NamingException ex) {
            ex.printStackTrace();
        }        
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }
    
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }
    
    
    public String addProduct(){
        
            PreparedStatement ps=null;
            
            try (Connection conn = ds.getConnection()) {
            ps=conn.prepareStatement("insert into products(prodname, prodcategory , proddescription , prodprice) values(?,?,?,?)");
            ps.setString(1, prodName);
            ps.setString(2, prodCategory);
            ps.setString(3, prodDescription);
            ps.setString(4, String.valueOf(this.prodPrice));
            //ps.setDouble(5, prodPrice);            
            ps.executeUpdate();
            
            // Adding image for products with the product id
            ps=conn.prepareStatement("select max(id) from products");
                ResultSet rs =ps.executeQuery();
                String file_name ="dummy";
                while (rs.next()) {
                  file_name =rs.getString(1);
                    System.out.println("fileName :"+file_name);
                String path =FacesContext.getCurrentInstance().getExternalContext().getRealPath("/images");
                System.out.println(path);
                    try {
                        InputStream in = this.image.getInputStream();
                        byte[] data =new  byte[in.available()];
                        in.read(data);
                        File my_file = new File(path+"\\"+file_name+".jpg");
                        FileOutputStream  out = new  FileOutputStream(my_file, false);
                        out.write(data);
                        in.close();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            System.out.println("Data Added succesfully");
            
        } catch (Exception e) {
           
            e.printStackTrace();
        }
       
        return "showProduct";
    }
public String deleteProduct(productBean delProduct){
   
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps =conn.prepareStatement("delete from products where ID = ?");
            System.out.println();
            ps.setInt(1, delProduct.getProdId());
            ps.executeUpdate();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
   
    return "Product Successfully Deleted"; 
}
 public String viewProduct(productBean toViewProduct){
     
      this.setProdId(toViewProduct.getProdId());
      this.setProdName(toViewProduct.getProdName());
      this.setProdCategory(toViewProduct.getProdCategory());
      this.setProdDescription(toViewProduct.getProdDescription());
      this.setProdPrice(toViewProduct.getProdPrice());
      this.setImage(toViewProduct.getImage());
      return "viewProduct";
  }
    
}
