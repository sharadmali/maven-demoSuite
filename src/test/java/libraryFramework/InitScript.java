package libraryFramework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Field;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import libraryProject.Utility;

public class InitScript {	
	/*----------------------------------------------------------------------------
	Function Name    	: readGroupData
	Description     	: Reads the Group Name from database table Groups and stores all the group file name in arrGroupList array
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: arrGroupList
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/

	public  static List<String> readGroupData(String excelSheetFilePath, String query ) throws Exception {
		List<String> arrGroupList = new ArrayList<String>();		
		try	{
			Recordset rs = getRecordSetUsingFillo(excelSheetFilePath,query);
			if(rs!= null && rs.getCount() > 0) {
				while(rs.next()) {
					String strGroupName = rs.getField(0).value();								
					if(strGroupName!= null && !strGroupName.trim().isEmpty()) {
						arrGroupList.add(strGroupName);
					}
				}
			}
		} catch (FilloException e) {
			System.out.println("Fillo Exception occurred during reading GroupData file" + e.getMessage());
			String strDesc = "Fillo Exception occurred during reading GroupData file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		} catch (Exception e) {
			System.out.println("Exception occurred during reading GroupData file" + e.getMessage());
			String strDesc = "Exception occurred during reading GroupData file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		}		
		return arrGroupList;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: readTCInSequence
	Description     	: Reads the all the TCs from database table Groups and stores in arrTCList array
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: arrTCList
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public  static List<Integer> readTCInSequence(String excelSheetFilePath, String query ) throws Exception {
		List<Integer> arrTCList = new ArrayList<Integer>();		
		try	{
			String intSequence;
			Recordset rs = getRecordSetUsingFillo(excelSheetFilePath,query);
			if(rs!= null && rs.getCount() > 0) {
				while(rs.next()) {
					if(Global.gstrSequenceFlag.equalsIgnoreCase("true")){
						intSequence = rs.getField(6).value();
					} else {
						intSequence = rs.getField(0).value();
					}
					int i = Integer.parseInt(intSequence); 
					if(intSequence!= null) {
						arrTCList.add(i);
					}
				}
			}
			/*if(Global.gstrSequenceFlag.equalsIgnoreCase("true")){
				Collections.sort(arrTCList);
			} */
			
			Collections.sort(arrTCList);
				
		} catch (FilloException e) {
			System.out.println("Fillo Exception occurred during reading GroupData file" + e.getMessage());
			String strDesc = "Fillo Exception occurred during reading GroupData file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		} catch (Exception e) {
			System.out.println("Exception occurred during reading GroupData file" + e.getMessage());
			String strDesc = "Exception occurred during reading GroupData file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		}		
		return arrTCList;
	}			
	
	/*----------------------------------------------------------------------------
	Function Name    	: readGroupSheetData
	Description     	: Reads the Group sheet name from database table Groups and stores all the group file name in Global var and return teh Batch Name
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: strBatchName
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public  static void readGroupSheetData(String excelSheetFilePath, String query ) throws Exception {		
		//String strBatchName=null;
		try	{
			Recordset rs = getRecordSetUsingFillo(excelSheetFilePath,query);
			if(rs!= null && rs.getCount() > 0) {
				while(rs.next()) {
					
					Global.gTCID = rs.getField("ID");
					Global.gTCName = rs.getField("TestCaseID");
					Global.gTCDescription = rs.getField("Description");
					Global.gstrBatchName = rs.getField("Batch_Test_File");
					Global.gstrSequence = rs.getField("Execution_Sequence");
					Global.gstrFrequency = Integer.parseInt(rs.getField("Execution_Frequency"));
					if((Global.gTCID!= null && !Global.gTCID.trim().isEmpty())&&(Global.gTCName!= null && !Global.gTCName.trim().isEmpty())&&(Global.gTCDescription!= null && !Global.gTCDescription.trim().isEmpty())&&(Global.gstrBatchName!= null && !Global.gstrBatchName.trim().isEmpty())) {
												
					}
					else {	
						Exception e = null;
						throw new Exception("Mandatory Field value null in GroupSheetData: " + Global.gTCID, e);
					}
				}
			}
		} catch (FilloException e) {
			System.out.println("Fillo Exception occurred during reading GroupSheetData file: " + e.getMessage());
			String strDesc = "Fillo Exception occurred during reading GroupSheetData file: " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		} catch (Exception e) {
			System.out.println("Exception occurred during reading GroupSheetData file : " + e.getMessage());
			String strDesc = "Exception occurred during reading GroupSheetData file : " + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		}						
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: readBatchData
	Description     	: Reads the batch Name from database table batch and stores all the batch data in arrBatchList array
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: arrBatchList
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public  static List<String> readBatchData(String excelSheetFilePath, String query ) throws Exception {
		List<String> arrBatchList = new ArrayList<String>();		
		try	{
			Recordset rs = getRecordSetUsingFillo(excelSheetFilePath,query);
			if(rs!= null && rs.getCount() > 0 && rs.getFieldNames().size() > 0) {
				while(rs.next()) {
					for (int nLoopCount = 1; nLoopCount <= rs.getFieldNames().size()-1; nLoopCount++) {
						String strActionName = rs.getField(nLoopCount).value();	
						if(strActionName!= null && !strActionName.trim().isEmpty()) {
							arrBatchList.add(strActionName);
						}
					}					
				}
			}
		} catch (FilloException e) {
			System.out.println("Fillo Exception occurred during reading BatchSheet file" + e.getMessage());
			String strDesc = "Fillo Exception occurred during reading BatchSheet file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		} catch (Exception e) {
			System.out.println("Exception occurred during reading test BatchSheet file" + e.getMessage());
			String strDesc = "Exception occurred during reading test BatchSheet file" + e.getMessage();
			Utility.writeHTMLResultLog(strDesc, "fail");							
			Global.bResult = "False";
			Global.objErr = "11";
		}		
		return arrBatchList;
	}
	
	/*----------------------------------------------------------------------------
	Function Name    	: getRecordSetUsingFillo
	Description     	: Make the Fillo connection and return the recordset
	Input Parameters 	: excelSheetFilePath - excel Sheet File Path
	                    : query - SQL Query
	Return Value    	: rs
	Author		        : 
	Date of creation	:
	Date of modification:	
	----------------------------------------------------------------------------*/
	public static Recordset getRecordSetUsingFillo(String excelFilePath, String query) throws Exception {
		Fillo fillo = new Fillo();
		Connection con = null;
		Recordset rs = null;
		try {
			try {
				con = fillo.getConnection(excelFilePath);
			} catch (FilloException e) {
				System.out.println("Exception in Connection in getRecordSetUsingFillo using Fillo is: " + e.getMessage());				
				String strDesc = "Exception in Connection in getRecordSetUsingFillo using Fillo is: " + e.getMessage();
				Utility.writeHTMLResultLog(strDesc, "fail");							
				Global.bResult = "False";
				Global.objErr = "11";
			}
			try {
				rs = con.executeQuery(query);
			} catch (FilloException e) {
				System.out.println("Exception in RecordSet in getRecordSetUsingFillo using Fillo is: " + e.getMessage());
				String strDesc = "Exception in RecordSet in getRecordSetUsingFillo using Fillo is: " + e.getMessage();
				Utility.writeHTMLResultLog(strDesc, "fail");							
				Global.bResult = "False";
				Global.objErr = "11";
			}
		} finally {
			if (null != con) {
				con.close();
			}
		}
		 //deleteTempFile(excelFilePath);
		return rs;
	}

}
