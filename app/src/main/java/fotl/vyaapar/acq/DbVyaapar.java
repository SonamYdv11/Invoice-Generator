/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Jk Designs Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@Jk Designsprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import static fotl.vyaapar.acq.Constant.FIFTH_COLUMN;
import static fotl.vyaapar.acq.Constant.FIRST_COLUMN;
import static fotl.vyaapar.acq.Constant.FOURTH_COLUMN;
import static fotl.vyaapar.acq.Constant.SECOND_COLUMN;
import static fotl.vyaapar.acq.Constant.SIXTH_COLUMN;
import static fotl.vyaapar.acq.Constant.THIRD_COLUMN;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.R.bool;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.GpsStatus.NmeaListener;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;



public class DbVyaapar {

	private final String MY_DATABASE_NAME = "Vyaapar.db";
	File sdcard = Environment.getExternalStorageDirectory();

	private static final String Company_CREATE = "create table IF NOT EXISTS CompanyMaster(CId INTEGER PRIMARY KEY AUTOINCREMENT , image blob , CCountry varchar(50), CTax varchar(50), CTIN varchar(50));";
	private static final String Product_CREATE = "create table IF NOT EXISTS productMaster(PId INTEGER PRIMARY KEY AUTOINCREMENT , image blob , PCode varchar(50), PDesc varchar(50), Supplier varchar(50), Rate float);";
	private static final String Customer_CREATE = "create table IF NOT EXISTS customerMaster(CId INTEGER PRIMARY KEY AUTOINCREMENT , CName varchar(100), cBranch varchar(100), CType varchar(10),CEmail varchar(150),CAddr1 varchar(150),CAddr2 varchar(150),CTIN varchar(50), CCity varchar(10),CZip varchar(10),CMobile varchar(50),CLandline varchar(50),CNotes varchar(150));";
	private static final String CompanyContact_CREATE = "create table IF NOT EXISTS  CompanyContact(CCId INTEGER PRIMARY KEY AUTOINCREMENT , CCName varchar(100), CCAddr1 varchar(100), CCAddr2 varchar(100), CCAddr3 varchar(100), CCState varchar(50), CCZip varchar(50), CCSales varchar(50), CCOffice varchar(50), CCMobile varchar(50),CCEmail varchar(50),CCWebsite varchar(50));";
	private static final String InvoiceMain_CREATE = "create table IF NOT EXISTS InvoiceMaster(InvoiceId INTEGER PRIMARY KEY AUTOINCREMENT , InvoiceType varchar(50), CustomerId Integer, InvoiceDate varchar(50),Sales Float, PkgCharges Float, Shipping Float,  TotalAmount Float, AmountReceived Float, Balance Float, Terms varchar(50), TIN  varchar(50), Status Varchar(50));";
	private static final String InvoiceDetail_CREATE = "create table IF NOT EXISTS InvoiceDetail(InvDetailId INTEGER PRIMARY KEY AUTOINCREMENT , InvoiceId Integer, ProductCode varchar(50),ProductDesc varchar(50), Quantity Integer, Rate Float, Discount Float);";
	private static final String InvoiceShipping_CREATE = "create table IF NOT EXISTS InvoiceShipping(InvShipId INTEGER PRIMARY KEY AUTOINCREMENT , InvoiceId Integer,ShipAmount float,  ShipDate varchar(50), ShipVia varchar(100), Tracking varchar(50), ShipNotes varchar(100));";
	private static final String InvoiceOptions_CREATE = "create table IF NOT EXISTS InvoiceOptions(InvOptId INTEGER PRIMARY KEY AUTOINCREMENT , Qty Integer,Shipping Integer, PCode Integer, Rate Integer, Discount Integer, Tax Integer, PDesc Integer, Pkg Integer, IHeader Integer, PmntDet Integer,PImage Integer);";
	public static final String KEY_ROWID = "_id";
	SQLiteDatabase myDB = null;
	Context context;
	Cursor c;

	public DbVyaapar(Context context) {
		this.context = context;
	}

	public DbVyaapar() {

	}


	public void DeleteInvoiceMaster() {
		openConnection();
		myDB.execSQL("Drop Table InvoiceMaster ");
		closeConnection();
	}

	public Cursor getCustomerEmailId(String InvId) {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT CEmail FROM CustomerMaster where cname in ( select customerId as cname from InvoiceMaster where InvoiceId = " + InvId + ")", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public Cursor getCustomers() {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT CName,cBranch FROM customerMaster ", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public Cursor getProductList() {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT PCode, PDesc FROM productMaster", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public Cursor getCompanycheck() {
		try {
			openConnection();
			myDB.execSQL(CompanyContact_CREATE);
			c = myDB.rawQuery("SELECT count(*) as _id FROM CompanyContact ", null);
			//closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
			c = null;
		} finally {
			return c;
		}
	}

	public Cursor getInvoiceOptions() {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT InvOptId as _id,  Qty ,Shipping , PCode , Rate , Discount , Tax , PDesc , Pkg , IHeader , PmntDet ,PImage   FROM InvoiceOptions ", null);
			//closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;

	}

	public Cursor getCompany() {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT CId as _id, image,CCountry,	CTax, 	CTIN  FROM CompanyMaster ", null);
			//closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;

	}

	public Cursor getShippingDetails(String InvoiceId) {
		try {
			openConnection();
			myDB.execSQL(InvoiceShipping_CREATE);
			c = myDB.rawQuery("Select InvShipId as _id,ShipAmount,ShipDate,ShipVia, Tracking, ShipNotes from InvoiceShipping where  InvoiceId = " + InvoiceId, null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public Cursor getProduct(String ProductCode) {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT PId as _id, PCode,  PDesc,Supplier,Rate, image  FROM productMaster where PCode ='" + ProductCode + "'", null);
			//closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;

	}

	public Cursor getProducts() {
		try {
			openConnection();
			c = myDB.rawQuery(" SELECT -1 as _id, 'Select One'  as PCode,' ' as  PDesc,'Select'  as PCode2,' ' as  Supplier, 0 as  Rate FROM productMaster  union  SELECT PId as _id, PCode,  PDesc,(PCode || '              - ' || PDesc)  as PCode2 ,Supplier,Rate FROM productMaster ", null);
			//closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;

	}

	public Cursor getProducts(String PDesc) {
		try {
			openConnection();
			c = myDB.rawQuery(" SELECT -1 as _id, 'Select One'  as PCode,' ' as  PDesc,'Select'  as PCode2,' ' as  Supplier, 0 as  Rate FROM productMaster  union  SELECT PId as _id, PCode,  PDesc,(PCode || '              - ' || PDesc)  as PCode2 ,Supplier,Rate FROM productMaster where PDesc like 'l%'", null);
			//closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;

	}

	public Cursor getProductsDetails() {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT PId as _id, PCode,  PDesc,Supplier,Rate, image FROM productMaster ", null);
			//closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;

	}

	public Cursor getProdcutDetails(String ProductCode) {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT PCode as _id, PDesc as PCode,Rate,Supplier,image  FROM productMaster where Pcode = '" + ProductCode + "'", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public Cursor getProdcutImage(String ProductCode) {
		try {
			openConnection();
			c = myDB.rawQuery("SELECT PCode as _id, image  FROM productMaster where Pcode = '" + ProductCode + "'", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public Cursor getInvoiceNumber() {
		Cursor cInvoice = null;
		try {
			openConnection();
			c = myDB.rawQuery("select case when  max(InvoiceId) is  null then 0 else max(InvoiceId)  end  as _id,InvoiceType, CustomerId,InvoiceDate,TotalAmount,AmountReceived,Balance, Terms , TIN , Status, Sales, PkgCharges, Shipping, AmountReceived, Balance  from InvoiceMaster ", null);
			//c= myDB.rawQuery(" Select max(InvoiceId) as _id,InvoiceType, CustomerId,InvoiceDate,TotalAmount,AmountReceived,Balance, Terms , TIN , Status, Sales, PkgCharges, Shipping, AmountReceived, Balance  from InvoiceMaster where invoiceId in (select  max(invoiceId) as InvoiceId  from invoiceDetail) ", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}

	public Cursor getInvoiceDetailsNumber() {
		try {
			openConnection();
			c = myDB.rawQuery("Select  case when  max(InvoiceId) = ''  then 0 else max(InvoiceId)  end  as _id  from InvoiceDetail ", null);
			//c= myDB.rawQuery(" Select max(InvoiceId) as _id,InvoiceType, CustomerId,InvoiceDate,TotalAmount,AmountReceived,Balance, Terms , TIN , Status, Sales, PkgCharges, Shipping, AmountReceived, Balance  from InvoiceMaster where invoiceId in (select  max(invoiceId) as InvoiceId  from invoiceDetail) ", null);
		} catch (Exception e) {
			// TODO: handle exception
			c = null;
		}
		return c;
	}

	public Cursor getInvoiceMasterDetails(String InvoiceId)
	{
		Cursor cInvoice=null;
		try
		{
			openConnection();
			if(InvoiceId == "")
				cInvoice= myDB.rawQuery("Select InvoiceId  as _id,InvoiceType, CustomerId,InvoiceDate,TotalAmount,AmountReceived,Balance, Terms , TIN , Status,Sales,PkgCharges, Shipping, AmountReceived , Balance  from InvoiceMaster where invoiceId =  1 ", null);//(select max(invoiceId) from InvoiceMaster)
			else
				cInvoice= myDB.rawQuery("Select InvoiceId as _id,InvoiceType, CustomerId,InvoiceDate,TotalAmount,AmountReceived,Balance, Terms , TIN , Status, Sales, PkgCharges, Shipping, AmountReceived, Balance  from InvoiceMaster where invoiceId = " + InvoiceId, null);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return cInvoice;
	}


	public Cursor getInvoiceFullDetails(String InvoiceId) {
		Cursor cInvoice = null;
		try {
			openConnection();
			if (InvoiceId != "")
				cInvoice = myDB.rawQuery("SELECT a.InvoiceId as _id, a.InvoiceType,a.CustomerId, a.InvoiceDate,a.Sales, a.PkgCharges, a.Shipping, a.TotalAmount, a.AmountReceived,a.Balance, a.Terms,a.TIN,a.Status, b.ProductCode,b.ProductDesc,b.Quantity,b.	Rate	,b.Discount,c.CName,c.cBranch,c.CType,c.CEmail,c.CAddr1,c.CAddr2,c.CTIN,c.CCity,c.CZip  FROM InvoiceDetail b inner join  invoiceMaster a on a.invoiceid = b.invoiceid inner join customerMaster c on c.CName = a.CustomerId  where b.invoiceid = " + InvoiceId + " group by b.invDetailId ", null);//(select max(invoiceId) from InvoiceMaster)
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cInvoice;
	}


	public Cursor getInvoiceList() {
		try {
			openConnection();
			c = myDB.rawQuery("Select InvoiceId  as _id,InvoiceType, CustomerId,InvoiceDate,TotalAmount,AmountReceived,Balance, Terms , TIN , Status,Sales,PkgCharges, Shipping, AmountReceived , Balance  from InvoiceMaster ", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}


	public Cursor getselectedData(String from, String to)
	{
		try
		{
			openConnection();
			c= myDB.rawQuery("Select InvoiceId  as _id,InvoiceType, CustomerId,InvoiceDate,TotalAmount,AmountReceived,Balance, Terms , TIN , Status,Sales,PkgCharges, Shipping, AmountReceived , Balance from InvoiceMaster  where InvoiceDate  where InvoiceDate between '"+from+"' and '"+to+"'  ", null);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}
	
	public Cursor getInvoiceDetails(String InvoiceId)
	{
		try		
		{	
		openConnection();
		if(InvoiceId == "")
			c= myDB.rawQuery("Select InvDetailId as _id, ProductCode ,ProductDesc,Quantity ,Rate ,Discount from InvoiceDetail where InvoiceId = (select max(invoiceId) from InvoiceMaster)", null);
		else
		c= myDB.rawQuery("Select InvDetailId as _id, ProductCode ,ProductDesc,Quantity ,Rate ,Discount from InvoiceDetail where InvoiceId =  "+ InvoiceId , null);
		//InvDetailId INTEGER PRIMARY KEY AUTOINCREMENT , InvoiceId Integer, ProductCode varchar(50), Quantity Integer, Rate Float, Discount Float);";
		}
		catch (Exception e) {
			// TODO: handle exception
		}		
		return c;
	}
	
	public Cursor getCustomerDetails(String CName)
	{	
		try
		{
			openConnection();
			// Toast.makeText(getApplicationContext(), "You had clicked................"+value  , Toast.LENGTH_LONG).show();
			c = myDB.rawQuery("SELECT CId as _id , CName ,cBranch ,CType ,CEmail ,CAddr1 ,CAddr2 ,CTIN , CCity ,CZip ,CMobile ,CLandline ,CNotes  FROM customerMaster Where CName ='"+ CName+ "'", null);			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return c;
	}
	
	public int updateProductMaster(ContentValues CCContent, String PCode)
	{
		String Result = "";		
		int i=0;
		try
		{
			openConnection();
			myDB.execSQL(Product_CREATE);
			i = myDB.update("productMaster",  CCContent," PCode= '"+ PCode+"'" , null );
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}	
		
		return i;
		
	}
	
	
	
	public int UpdateInvoiceOptions(ContentValues CCContent)
	{
		String Result = "";		
		int i=0;
		try
		{
			openConnection();
			myDB.execSQL(InvoiceOptions_CREATE);
			i = myDB.update("InvoiceOptions",  CCContent,"1=1" , null );
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}	
		
		return i;
		
	}
	
	public int DeleteInvoiceDetail(ContentValues CCContent, String WhereCond)
	{			
		int i=0;
		try
		{
			openConnection();
			openConnection();
			myDB.execSQL("Delete from InvoiceDetail where " + WhereCond + "");
			closeConnection();			
		}
		catch (Exception e) {
			// TODO: handle exception	
		}	
		
		return i;
		
	}
	
	
	
	public int UpdateInvoiceDetail(ContentValues CCContent, String WhereCond)
	{			
		int i=0;
		try
		{
			openConnection();		
			i = myDB.update("InvoiceDetail",  CCContent,WhereCond , null );
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception	
		}	
		
		return i;
		
	}
	
	public int UpdateInvoiceMaster(ContentValues CCContent, String InvId)
	{
		String Result = "";		
		int i=0;
		try
		{
			openConnection();
			myDB.execSQL(InvoiceMain_CREATE);
			i = myDB.update("InvoiceMaster",  CCContent,"InvoiceId="+InvId , null );
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage();
		}	
		
		return i;
		
	}
	
	public int UpdateCustomer(ContentValues CCContent, String CusId)
	{
		String Result = "";		
		int i=0;
		try
		{
			openConnection();
			myDB.execSQL(Customer_CREATE);
			i = myDB.update("customerMaster",  CCContent,"CName='"+CusId + "'", null );
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}	
		
		return i;
		
	}

	
	public String CreateInvoiceOptions(ContentValues CCContent)
	{
		String Result = "";
		try
		{
			openConnection();
			myDB.execSQL(InvoiceOptions_CREATE);
			myDB.insert("InvoiceOptions", null, CCContent);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}		
		return Result;
		
	}
	
	
	public String CreateInvoice(ContentValues CCContent)
	{
		String Result = "";
		try
		{
			openConnection();
			myDB.execSQL(InvoiceMain_CREATE);
			myDB.execSQL(InvoiceDetail_CREATE);
			myDB.insert("InvoiceMaster", null, CCContent);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}		
		return Result;
		
	}
	
	public String InsertInvoiceDetail(ContentValues CCContent)
	{
		String Result = "";
		try
		{
			openConnection();
			myDB.execSQL(InvoiceMain_CREATE);
			myDB.execSQL(InvoiceDetail_CREATE);
			myDB.insert("InvoiceDetail", null, CCContent);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}		
		return Result;
		
	}
	
	public String InsertCompany(ContentValues CCContent)
	{
		String Result = "";
		try
		{
			openConnection();
			myDB.execSQL(Company_CREATE);
			myDB.insert("CompanyMaster", null, CCContent);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}		
		return Result;
		
	}
	
	
	public Cursor getCompanyContact()
	{
		try		
		{
		openConnection();		
		c= myDB.rawQuery("Select CCId  as _id, CCName, CCAddr1 , CCAddr2 , CCAddr3 , CCState , CCZip , CCSales, CCOffice , CCMobile, CCEmail,CCWebsite   from CompanyContact ", null);		
		}
		catch (Exception e) {
			// TODO: handle exception
		}		
		return c;
	}
	
	public String UpdateCompanyContact(ContentValues CCContent)
	{
		String Result = "";
		try
		{
			openConnection();
			myDB.execSQL(CompanyContact_CREATE);
			myDB.update("CompanyContact",  CCContent, " CCId = 1 ", null);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}		
		return Result;
		
	}
	
	public String updateCompany(ContentValues CCContent)
	{
		String Result = "";
		try
		{
			openConnection();
			myDB.execSQL(Company_CREATE);
			myDB.update("CompanyMaster", CCContent, "CId=1", null);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}		
		return Result;
		
	}
	
	public String InsertShipping(ContentValues CCContent)
	{
		String Result = "";
		try
		{
			openConnection();
			myDB.execSQL(InvoiceShipping_CREATE);
			myDB.insert("InvoiceShipping", null, CCContent);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}		
		return Result;
		
	}

	
	public int UpdateInvoiceShipping(ContentValues CCContent, String InvId)
	{
		String Result = "";		
		int i=0;
		try
		{
			openConnection();
			myDB.execSQL(InvoiceShipping_CREATE);
			i = myDB.update("InvoiceShipping",  CCContent,"InvoiceId="+InvId , null );
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}	
		
		return i;
		
	}
	
	public String InsertCompanyContact(ContentValues CCContent)
	{
		String Result = "";
		try
		{
			openConnection();
			myDB.execSQL(CompanyContact_CREATE);
			myDB.insert("CompanyContact", null, CCContent);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result = e.getMessage().toString();
		}		
		return Result;
		
	}
	
	public String InsertProduct(ContentValues ProdContent)
	{
		String Result="";
		try
		{
			openConnection();
			myDB.execSQL(Product_CREATE);
			myDB.insert("productMaster",null, ProdContent);
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result=e.getMessage().toString();
		}
		return Result;		
	}
	
	public int DeleteInvoice( String InvoiceId)
	{
		int Result=0;
		try
		{
			openConnection();
			myDB.execSQL("Delete from InvoiceDetail where InvoiceId = '" + InvoiceId + "'");
			myDB.execSQL("Delete from InvoiceMaster where InvoiceId = '" + InvoiceId + "'");
			
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result=-1;
			closeConnection();
		}
		return Result;	
	}
	public int DeleteCustomer(String CustomerName)
	{
		int Result=0;
		try
		{
			openConnection();
			myDB.execSQL("Delete from customerMaster where CName = '" + CustomerName + "'");			
			closeConnection();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			Result=-1;
			closeConnection();
		}
		return Result;		
	}
	
	public int DeleteProduct(String PCode)
	{
		int Result=0;
		try
		{
			openConnection();
			myDB.execSQL("Delete from productMaster where PCode = '" + PCode + "'");			
			closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
			Result=-1;
			closeConnection();
		}
		return Result;		
	}
	
	public  String InsertCustomer(String CName, String cBranch, String CType,String CEmail, String CAddr1, String CAddr2 , String CTIN,String CCity,String CZip, String CMobile, String CLandline, String CNotes  ) {
		String Result= "";
		
		String sql =""; 
		try
		{
			openConnection();
			myDB.execSQL(Customer_CREATE);
			sql = "INSERT INTO customerMaster ( CName , cBranch , CType ,CEmail ,CAddr1 ,CAddr2,CTIN , CCity ,CZip ,CMobile ,CLandline ,CNotes) Values ( ";
			sql += "'" + CName  +"', '" + cBranch  +"', '"+CType  +"', '"+ CEmail +"', '"+CAddr1  +"', '"+CAddr2  +"', '"+ CTIN  +"', '"+ CCity	+"', '"+CZip  +"', '"+ CMobile  +"', '"+ CLandline +"', '"+ CNotes +"');";
			myDB.execSQL(sql);
		}
		catch (Exception e) {
			// TODO: handle exception
			Result= e.getMessage().toString();
		}
		return Result;
	}
	
	public synchronized void openConnection(){
		myDB=context.openOrCreateDatabase(sdcard.getAbsolutePath() + File.separator+ "external_sd" + File.separator + MY_DATABASE_NAME, 0 ,null);
	}
	
	public synchronized void closeConnection(){
		myDB.close();
	}
	
	public  Cursor getCustomerd()
	{
		
		try
		{
			openConnection();
			c = myDB.rawQuery("SELECT -1 as _id,'Select one' as  CName,'' as CBranch FROM customerMaster union   SELECT CId as _id, CName,CBranch FROM customerMaster ", null);			
		}
		catch (Exception e) {
			// TODO: handle exception
			
		}
		return c;
	}

	
	public ArrayList<HashMap<String,String>> getCustomer()
	{
		ArrayList<HashMap<String,String>> Customers = new ArrayList<HashMap<String,String>>();
		try
		{
			openConnection();
			Cursor c = myDB.rawQuery("SELECT CId, CName,CBranch FROM customerMaster ", null);
			if (c != null ) {
			    if  (c.moveToFirst()) {
			        do {
			            String CId = c.getString(c.getColumnIndex("CId"));		            
			            HashMap<String,String> temp = new HashMap<String,String>();
						temp.put("Id",CId);
						temp.put("Name",c.getString(c.getColumnIndex("CName")));						
						Customers.add(temp);
			        }while (c.moveToNext());
			    }
			}
			c.close();
			closeConnection();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			
		}
		return Customers;
		
	}


}
