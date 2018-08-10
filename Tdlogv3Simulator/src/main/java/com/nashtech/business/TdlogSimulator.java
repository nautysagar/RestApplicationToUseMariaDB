package com.nashtech.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.dataCollector.interfaces.DataCollectorInterface;
import com.nashtech.dataCollector.pojo.TdlogDataBlock;
import com.nashtech.dataCollector.pojo.TdlogResult;

@Service
public class TdlogSimulator extends AbstractTdlog{
	
	@Autowired
	DataCollectorInterface service;

	@Override
	public TdlogResult createConfiguration() {
		return  service.configurationSetup(restApiUrlPath, writeToFile, uidLogFilePath, uidLogFileVersion, debugLevel);
	}

	@Override
	public TdlogResult callRunStart() {
		return service.runStart(ocr, diffLot, sfcLot, productName+td, waferNumber+td, testStage+td, nc12, tpName+td, tpVersion+td, processingType, dataBlockIdentitier+td, dataBlockDefinitionVersion+td, getDataBlock());
	}

	@Override
	public TdlogResult pushDataStart() {
		TdlogResult result = null;
		for (int i=0;i < td;i++) {
			 result = service.pushDataStart();
			 if (result.getErrorLevel() != 0)
					return result;
			  result = pushData();
			 if (result.getErrorLevel() != 0)
					return result;
			  result = service.pushDataEnd();
		}
		return result;
	}

	private TdlogResult pushData() {
		Random rand = new Random();
		TdlogResult result = null;
		int  x = rand.nextInt(tdc);
		int  y = rand.nextInt(tdc);
		
		for (int i=0;i<tdc;i++) {
			 x = rand.nextInt(200);
			 y = rand.nextInt(200);
			for (int k=0;k<4;k++)
				 result =service.pushData(1, x, y, k,getGeneratedUUID());
}
		return result;
	}

	@Override
	public TdlogResult runFinish(int i) {
		return service.runFinish(i);
	}
	
	private List<TdlogDataBlock> getDataBlock(){
		List<TdlogDataBlock> b = new ArrayList<>();
		for(int k=0;k< tdlogDataBlock.length;k++) {
			TdlogDataBlock bb = new TdlogDataBlock();
			bb.setKey(k);
			bb.setValue(tdlogDataBlock[k]);
			b.add(bb);
		}
		return b;
	}
	
	private static String getGeneratedUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}
	
	

}
