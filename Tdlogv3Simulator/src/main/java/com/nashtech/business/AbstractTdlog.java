package com.nashtech.business;

import com.nashtech.dataCollector.pojo.TdlogResult;

public abstract class AbstractTdlog {

	protected static final String restApiUrlPath = "http://boe1ds01:5005/v1";
	protected static final boolean writeToFile = true;
	protected static final String uidLogFilePath = "/tmp/logs/";
	protected static final String uidLogFileVersion = "uid3v";
	protected static final int debugLevel = 3;

	protected static final String processingType = "WAFERRUNSTARTDATA";
	protected static final String tpVersion = "TP_Version";
	protected static final String ocr = new RandomString().nextString();
	protected static final String diffLot = new RandomString().nextString();;
	protected static final String sfcLot = new RandomString().nextString();
	protected static final String productName = "Product";
	protected static final int waferNumber = 0;
	protected static final String testStage = "HX";
	protected static final String nc12 = new RandomString(12).nextString();
	protected static final String tpName = "TestProgram";
	protected static final String dataBlockIdentitier = "UID";
	protected static final int dataBlockDefinitionVersion = 0;

	protected static final String[] tdlogDataBlock = { "CDEC", "ECAD", "AFBED", "ACD" };
	
	protected  int td;
	
	protected  int tdc;

	public TdlogResult create(int td, int tdc) {
		
		this.td=td;
		this.tdc=tdc;

		TdlogResult result = createConfiguration();
		if (result.getErrorLevel() != 0)
			return result;
		 result =  callRunStart();
		if (result.getErrorLevel() != 0)
			return result;
		 result =  pushDataStart();
		if (result.getErrorLevel() != 0)
			return result;
		result = runFinish(td * tdc * 4);

		return result;

	}

	public abstract TdlogResult createConfiguration();
	
	public abstract TdlogResult callRunStart();

	public abstract TdlogResult pushDataStart();	

	public abstract TdlogResult runFinish(int i);

}
