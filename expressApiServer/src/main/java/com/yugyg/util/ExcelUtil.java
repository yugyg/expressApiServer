package com.yugyg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monitorjbl.xlsx.StreamingReader;

public class ExcelUtil {
	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    public static JSONArray getCompanyCode(String path) {
        JSONArray array = new JSONArray();
        logger.info(ExcelUtil.class.getClassLoader().getResource(path).toString()+"======================");
        logger.info(ExcelUtil.class.getClassLoader().getResource(path).toString().replaceFirst("file:", "")+"======================");
		try (InputStream is = new FileInputStream(new File(ExcelUtil.class.getClassLoader().getResource(path).toString().replaceFirst("file:", "")));
				Workbook workbook = StreamingReader.builder().open(is);) {
			Sheet sheet = workbook.getSheetAt(0);
			while (sheet.rowIterator().hasNext()) {
				Row row = sheet.rowIterator().next();
				JSONObject object = new JSONObject();
				for (int i = 0; i < row.getLastCellNum(); i++) {
					String value = row.getCell(i).getStringCellValue();
					if (value !="") {
						switch (i) {
						case 0:
							object.put("com",value);
							break;
						case 1:
							object.put("cn",value);
							break;
						case 2:
							object.put("track",value);
							break;
						case 3:
							object.put("dzmd",value);
							break;
						case 4:
							object.put("yyqj",value);
							break;
						default:
							break;
						}
					}
				}
				array.add(object);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return array;
    }
}