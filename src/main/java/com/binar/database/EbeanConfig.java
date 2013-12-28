package com.binar.database;

import com.avaje.ebean.Ebean;  
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.SqlRow;  
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.GlobalProperties;  
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.MySqlPlatform;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.Insurance;
import com.binar.entity.Invoice;
import com.binar.entity.InvoiceItem;
import com.binar.entity.Manufacturer;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Role;
import com.binar.entity.Setting;
import com.binar.entity.Supplier;
import com.binar.entity.SupplierGoods;
import com.binar.entity.User;
  
public class EbeanConfig {  
  

    public void test() {  
        EbeanServer server=setConfig();
        
        System.out.println("Got   - DataSource good.");  
    }  
    
     EbeanServer setConfig(){
    	// programmatically build a EbeanServer instance
    	// specify the configuration...

    	ServerConfig config = new ServerConfig();
    	config.setName("mysql");

    	// Define DataSource parameters
    	DataSourceConfig postgresDb = new DataSourceConfig();
    	postgresDb.setDriver("com.mysql.jdbc.Driver");
    	postgresDb.setUsername("root");
    	postgresDb.setPassword("");
    	postgresDb.setUrl("jdbc:mysql://127.0.0.1:3306/perbekalan_farmasi");

    	// specify a JNDI DataSource 
    	// config.setDataSourceJndiName("someJndiDataSourceName");

    	// set DDL options...
//    	config.setDdlGenerate(true);
//    	config.setDdlRun(true);
    	config.setDdlGenerate(false);
    	config.setDdlRun(false);
    	config.setDefaultServer(false);
    	config.setRegister(false);

    	
    	// automatically determine the DatabasePlatform
    	// using the jdbc driver 
    	 config.setDatabasePlatform(new MySqlPlatform());
    	 config.setDataSourceConfig(postgresDb);
    	// specify the entity classes (and listeners etc)
    	// ... if these are not specified Ebean will search
    	// ... the classpath looking for entity classes.
//    	config.addClass(Order.class);
     	config.addClass(DeletedGoods.class);
    	config.addClass(Goods.class);
    	config.addClass(GoodsConsumption.class);
    	config.addClass(GoodsReception.class);
    	config.addClass(Insurance.class);
    	config.addClass(Invoice.class);
    	config.addClass(InvoiceItem.class);
    	config.addClass(PurchaseOrder.class);
    	config.addClass(PurchaseOrderItem.class);
    	config.addClass(ReqPlanning.class);
    	config.addClass(Role.class);
    	config.addClass(Setting.class);
    	config.addClass(Supplier.class);
    	config.addClass(SupplierGoods.class);
    	config.addClass(User.class);
    	config.addClass(Manufacturer.class);
    	
    	
    	
//    	...

    	// specify jars to search for entity beans
//    	config.addJar("someJarThatContainsEntityBeans.jar");

    	// create the EbeanServer instance
    	EbeanServer server = EbeanServerFactory.create(config);
    	
    	return server;
    }
}  