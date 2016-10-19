package obor.config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * @author fei.yu
 * @date 2016-08-15
 * @version 1.0
 * 
 * 转换工具类
 * 1.用于处理Excel转xml
 * 2.调用ExcelToXml方法
 * 3.参数为Map<标签名,标签值>（MessageHead与ConsignInformation的值）
 * 4.返回Map<落地xml路径,错误信息>
 * 
 */
public class ConvertTools {
	
	public static Map<String, String> ExcelToXml(Map<String, String> headMap, String excelFileName,String custcode) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> mouldMap_orderHead = getMould("OrderHead","1",custcode);
		Map<String, String> mouldMap_goodsList = getMould("GoodsList","1",custcode);
		Map<String, String> mouldMap_consignInfoList = getMould("ConsignInformation","0",custcode);
		Map<String, String> resultMap = new HashMap<String, String>();

		if (!"".equals(mouldMap_orderHead.get("error")) || !"".equals(mouldMap_goodsList.get("error"))) {
			resultMap.put("filepath", "");
			resultMap.put("error", mouldMap_orderHead.get("error") + mouldMap_goodsList.get("error"));
			return resultMap;
		}

		Map<String, Integer> comMap = new HashMap<String, Integer>();
		Map<String, List<String[]>> mergeMap = new HashMap<String, List<String[]>>();
		
		try {
			File excelFile = new File(excelFileName);
			FileInputStream is = new FileInputStream(excelFile);
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(0);

			int orderCellNum = 0;
			int rowCount=0;
			int cellCount=0;
			DecimalFormat df = new DecimalFormat("0.00");  
			Iterator<Row> rows = sheet.rowIterator(); 
        	while (rows.hasNext()) {
        		Row row = rows.next(); 
        		int cellnum = row.getPhysicalNumberOfCells();
        		if(rowCount==0){
        			cellCount = cellnum;
        		}
				String[] rowData = new String[cellCount];
				for (int c = 0; c < cellCount; c++) {
					Cell cell = row.getCell(c);
					String cellValue = "";
					int cellType = 0;
					
					if (cell == null) {
						cellValue = "";
					}else{
						cellType = cell.getCellType();
						
						switch (cellType) {
							case Cell.CELL_TYPE_STRING: // 文本
								cellValue = cell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_NUMERIC: // 数字、日期
								if (DateUtil.isCellDateFormatted(cell)) {
									cellValue = fmt.format(cell.getDateCellValue()); // 日期型
								} else {
									cellValue = df.format(cell.getNumericCellValue());  
									if(cellValue.endsWith(".00")){
										cellValue = cellValue.substring(0, cellValue.length()-3);
									}
								}
								break;
							case Cell.CELL_TYPE_BOOLEAN: // 布尔型
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							case Cell.CELL_TYPE_BLANK: // 空白
								cellValue = cell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_ERROR: // 错误
								cellValue = "错误";
								break;
							case Cell.CELL_TYPE_FORMULA: // 公式
								try {  
									cellValue = String.valueOf(cell.getStringCellValue());  
								} catch (IllegalStateException e) {  
									cellValue = String.valueOf(cell.getNumericCellValue());  
								}
								break;
							default:
								cellValue = "错误";
						}
					}
					
					if (rowCount == 0) {
						cellValue = cellValue.replace("(", "（").replace(")", "）").replace("　", " ").trim();
						comMap.put(cellValue, c);
						if ("订单编号".equals(cellValue)) {
							orderCellNum = c;
						}
						if(c == cellCount - 1){
							rowCount++;
						}
						
						continue;
					}
//					cellValue = cellValue.replace("'", "").replace("　", " ").trim();//身份证,支付单流水号等有 ' 的 去掉
					rowData[c] = cellValue;
					
					if (c == cellCount - 1) {
						Cell orderCell = row.getCell(orderCellNum);
						String val = "";
						if(orderCell != null){
							cellType = orderCell.getCellType();
							
							if(cellType == 0){
								val = df.format(row.getCell(orderCellNum).getNumericCellValue());  
							}else if(cellType == 1){
								val =row.getCell(orderCellNum).getStringCellValue();
							}else{
								val = "";
							}
							
//							if(!"".equals(val)){
//								if (mergeMap.containsKey(val)) {
//									List<String[]> tempList = mergeMap.get(val);
//									tempList.add(rowData);
//								} else {
//									List<String[]> newList = new ArrayList<String[]>();
//									newList.add(rowData);
//									mergeMap.put(val, newList);
//								}
//							}
						}
						if(val != null && !"".equals(val)){
							if (mergeMap.containsKey(val)) {
								List<String[]> tempList = mergeMap.get(val);
								tempList.add(rowData);
							} else {
								List<String[]> newList = new ArrayList<String[]>();
								newList.add(rowData);
								mergeMap.put(val, newList);
							}
						}
						rowCount++;
					}
				}
            }
               
			String xml = generateXml(mergeMap, headMap, mouldMap_orderHead, mouldMap_goodsList,mouldMap_consignInfoList, comMap);
			String path = generateFile(xml,excelFileName);
			resultMap.put("filepath", path);
			resultMap.put("error", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	public static String generateXml(Map<String, List<String[]>> mergeMap, Map<String, String> headMap,
			Map<String, String> mouldMap_orderHead, Map<String, String> mouldMap_goodsList,
			Map<String, String> mouldMap_consignInfoList,Map<String, Integer> comMap) {
		Document document = DocumentHelper.createDocument();
		Element requestMessager = document.addElement("RequestMessage");
		Element messageHead = requestMessager.addElement("MessageHead");

		Element messageHead_1 = messageHead.addElement("MESSAGE_ID");
		messageHead_1.setText(headMap.get("MESSAGE_ID"));
		Element messageHead_2 = messageHead.addElement("MESSAGE_TYPE");
		messageHead_2.setText(headMap.get("MESSAGE_TYPE"));
		Element messageHead_3 = messageHead.addElement("MESSAGE_TIME");
		messageHead_3.setText(headMap.get("MESSAGE_TIME"));
		Element messageHead_4 = messageHead.addElement("MESSAGE_SOURCE");
		messageHead_4.setText(headMap.get("MESSAGE_SOURCE"));
		Element messageHead_5 = messageHead.addElement("MESSAGE_DEST");
		messageHead_5.setText(headMap.get("MESSAGE_DEST"));

		Element messageBody = requestMessager.addElement("MessageBody");
		
		Element consignInformation = messageBody.addElement("ConsignInformation");
		
		Element orderRequest = messageBody.addElement("OrderRequest");

		int i = 0;
		for (String key : mergeMap.keySet()) {
			
			Element order = orderRequest.addElement("Order");
			Element orderHead = order.addElement("OrderHead");
			List<String[]> items = mergeMap.get(key);

			if (items != null && items.size() > 0) {
				if(i==0){
					for(String consignInfoKey : mouldMap_consignInfoList.keySet()){
						if("error".equals(consignInfoKey)){
							continue;
						}
						Element itemElement = null;
						if (comMap.containsKey(consignInfoKey.toString())) {
							itemElement = consignInformation.addElement(mouldMap_consignInfoList.get(consignInfoKey));
							if("ORDERSCOUNT".equals(mouldMap_consignInfoList.get(consignInfoKey))){
								itemElement.setText(String.valueOf(mergeMap.size()));
							}else{
								String[] strs = items.get(0);
								itemElement.setText(strs[comMap.get(consignInfoKey)]);
							}
						}else{
							if("ORDERSCOUNT".equals(mouldMap_consignInfoList.get(consignInfoKey))){
								itemElement = consignInformation.addElement(mouldMap_consignInfoList.get(consignInfoKey));
								itemElement.setText(String.valueOf(mergeMap.size()));
							}
						}
						
					}
				}
				for (String headKey : mouldMap_orderHead.keySet()) {
					if("error".equals(headKey)){
						continue;
					}
					String tempKey = headKey.replace("(", "（").replace(")", "）");
					if (comMap.containsKey(tempKey)) {
						Element itemElement = orderHead.addElement(mouldMap_orderHead.get(headKey));
						String[] strs = items.get(0);
						itemElement.setText(strs[comMap.get(tempKey)]);
					}
				}

				for (String[] goods : items) {
					Element goodsList = order.addElement("GoodsList");
					for (String goodsKey : mouldMap_goodsList.keySet()) {
						if("error".equals(goodsKey)){
							continue;
						}
						if (comMap.containsKey(goodsKey)) {
							Element itemElement = goodsList.addElement(mouldMap_goodsList.get(goodsKey));
							itemElement.setText(goods[comMap.get(goodsKey)]);
						}
					}
				}
			}
			i++;
		}

		return doc2String(document);
	}

	public static Map<String, String> getMould(String nodeName,String sign, String custcode) {
		Map<String, String> mouldMap = new HashMap<String, String>();

		try {
			String fileName = "/dataXML/"+custcode+"/Model.xml";
			InputStream in = ConvertTools.class.getResourceAsStream(fileName);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			Element root = null;
			if("0".equals(sign)){
				root = doc.getRootElement().element("MessageBody");
				
				List<Element> items = root.element(nodeName).elements();
				for (Element node : items) {
					mouldMap.put(node.attributeValue("name"), node.getName());
				}
			}else{
				root = doc.getRootElement().element("MessageBody").element("OrderRequest").element("Order");
				
				List<Element> items = root.element(nodeName).elements();
				for (Element node : items) {
					mouldMap.put(node.attributeValue("name"), node.getName());
				}
			}
			
			mouldMap.put("error", "");
		} catch (DocumentException e) {
			e.printStackTrace();
			mouldMap.put("error", "读取xml失败");
		} catch (Exception e){
			e.printStackTrace();
			mouldMap.put("error", "读取文件失败");
		}
		return mouldMap;
	}

	public static String doc2String(Document document) {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			OutputFormat format = new OutputFormat("\t", true, "UTF-8");
			format.setNewLineAfterDeclaration(false);
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	public static String generateFile(String xml, String excelFileName) {
//		String movefile = "D://a/a.xml";
		String[] str = excelFileName.split("[.]");
		String fileType = str[str.length-1];
		int index = excelFileName.indexOf(str[str.length-1]);
		String movefile = excelFileName.substring(0, index-1) + ".xml";
		File file = new File(movefile);
		try (FileOutputStream fop = new FileOutputStream(file)) {

			OutputStreamWriter osw = new OutputStreamWriter(fop, "UTF-8");
			osw.write(xml);
			osw.flush();
			osw.close();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
			return "generateFile " + movefile + " error: " + e.toString();
		}
		return movefile;
	}

	public static void main(String[] args) {
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("MESSAGE_ID", "1");
		headMap.put("MESSAGE_TYPE", "2");
		headMap.put("MESSAGE_TIME", "20110101111111");
		headMap.put("MESSAGE_SOURCE", "4");
		headMap.put("MESSAGE_DEST", "5");
		headMap.put("CUSCODE", "6");
		headMap.put("AGENTCODE", "7");
		headMap.put("CONSIGNTEMPLATENO", "8");
		headMap.put("ORDERSCOUNT", "9");
		//dataHandleService.getXMLHeadInfo();
//		Map<String, String> resultMap = ConvertTools.ExcelToXml(headMap, "C:\\Users\\thinkpad\\Desktop\\郑州和西安的跨境出口综合服务平台\\测试\\emp_dir\\1test.xlsx");
	}
}
