/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandlers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author ABS-Brother
 */
public class regPhaseListener implements PhaseListener{
    
    // Resource injection using instead of context and initialContext
    //@Resource(name = "jdbc/regJNDI")
     DataSource ds;
    
     
     
    public regPhaseListener(){
       try
       {
           Context ctx= new InitialContext();
            ds=(DataSource)ctx.lookup("jdbc/regJNDI");
       }
       catch( Exception e )
       {
           e.printStackTrace();
       }
    }
    

    @Override
    public void afterPhase(PhaseEvent pe) {
        //throw new UnsupportedOperationException("Not supported yet.");
        
    }

    @Override
    public void beforePhase(PhaseEvent pe) {
      //  throw new UnsupportedOperationException("Not supported yet."); 
       
            if( pe.getPhaseId() == PhaseId.RESTORE_VIEW )
            {
                
                
                
                    
               try (Connection conn = ds.getConnection()) {
                
                DatabaseMetaData dbMeta =conn.getMetaData();
                ResultSet check_tbl=dbMeta.getTables(null, null, "USERS", null);
                if (!check_tbl.next()) {
                    Statement stmt = null;
                    stmt=conn.createStatement();
                    String create_table="CREATE TABLE  USERS" +
                            "(FIRSTNAME VARCHAR(50),"+
                            "LASTNAME VARCHAR(50),"+
                            "EMAIL VARCHAR(100),"+
                            "PASSWORD VARCHAR(50),"+
                            "PHONE VARCHAR(50),"+
                            "USERNAME VARCHAR(50))";
                    stmt.executeUpdate(create_table);
                   conn.close();
                   stmt.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
      
            
        }
    

    @Override
    public PhaseId getPhaseId() {
        return  PhaseId.ANY_PHASE;
        //throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
