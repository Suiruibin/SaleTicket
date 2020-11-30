package com.fuful.service.admin;


import com.fuful.domain.Book;
import com.fuful.domain.Ticket;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {

	public List<Ticket> imp(String filename)
	{
		
		List<Ticket> book=new ArrayList();
		try {
			Workbook workbook=WorkbookFactory.create(new File("/Users/SunRuiBin/Desktop/upload/"+filename));
			
			Sheet sheet=workbook.getSheetAt(0);
			int rowNum=sheet.getLastRowNum();
			for(int i=0;i<=rowNum;i++)
			{
				Row row=sheet.getRow(i);
				Ticket p=new Ticket();
				p.setTypeBID((int)row.getCell(1).getNumericCellValue());
				p.setTypeID((int)row.getCell(2).getNumericCellValue());
				p.setTicketName(row.getCell(3).getStringCellValue());
				p.setIntroduce(row.getCell(4).getStringCellValue());
				p.setPrice(row.getCell(5).getNumericCellValue());
				p.setNowPrice(row.getCell(6).getNumericCellValue());
				p.setPicture(row.getCell(7).getStringCellValue());
				p.setINTime(row.getCell(8).getStringCellValue());
				p.setNewTicket((int)row.getCell(9).getNumericCellValue());
				p.setSale((int)row.getCell(10).getNumericCellValue());
				p.setAuthor(row.getCell(11).getStringCellValue());
				p.setAmount(row.getCell(12).getStringCellValue());
				book.add(p);
			
				
			}
				
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return book;
	}
	
	
	
	public  Workbook export(boolean isXlsx, List<Ticket> booklist)
	{
		Workbook workbook;
		
		if(isXlsx)
		{
			
			workbook=new XSSFWorkbook();
		}
		else
		{
			workbook=new HSSFWorkbook();
		}
		
		Sheet sheet=workbook.createSheet("MySheet");
		
		for(int i=0;i<booklist.size();i++)
		{
			
//			p.setID(rs.getInt("ID"));
//			p.setTypeBID(rs.getInt("typeBID"));
//			p.setTypeID(rs.getInt("typeID"));
//			p.setBookName(rs.getString("bookName"));
//			p.setIntroduce(rs.getString("introduce"));
//			p.setPrice(rs.getDouble("price"));
//			p.setNowPrice(rs.getDouble("nowPrice"));
//			p.setPicture(rs.getString("picture"));
//			p.setINTime(rs.getString("INTime"));
//			p.setNewBook(rs.getInt("newBook"));
//			p.setSale(rs.getInt("sale"));
//			p.setAuthor(rs.getString("author"));
//			p.setPublish(rs.getString("publish"));
			Row row=sheet.createRow(i);
			Ticket p=booklist.get(i);
			
			row.createCell(0).setCellValue(p.getID());
			row.createCell(1).setCellValue(p.getTypeBID());
			row.createCell(2).setCellValue(0);
			row.createCell(3).setCellValue(p.getTicketName());
			row.createCell(4).setCellValue(p.getIntroduce());
			row.createCell(5).setCellValue(p.getPrice());
			row.createCell(6).setCellValue(p.getNowPrice());
			row.createCell(7).setCellValue(p.getPicture());
			row.createCell(8).setCellValue(p.getINTime());
			row.createCell(9).setCellValue(p.getNewTicket());
			row.createCell(10).setCellValue(p.getSale());
			row.createCell(11).setCellValue(p.getAuthor());
			row.createCell(12).setCellValue(p.getAmount());
			}
		
		
		return workbook;
			
		}
		
	
		
		

	 private List<List<String>> getContent() {
	        List<List<String>> result = new ArrayList<>();
	        List<String> row = new ArrayList<>();
	        result.add(row);
	        row.add("序号");
	        row.add("姓名");
	        row.add("年龄");
	        row.add("时间");

	        row = new ArrayList<>();
	        result.add(row);
	        row.add("1");
	        row.add("路人甲");
	        row.add("18");
	        row.add("2010-01-01");

	        row = new ArrayList<>();
	        result.add(row);
	        row.add("2");
	        row.add("路人乙");
	        row.add("19");
	        row.add("2010-01-02");

	        row = new ArrayList<>();
	        result.add(row);
	        row.add("3");
	        row.add("路人丙");
	        row.add("20");
	        row.add("2010-01-03");
	        return result;
	    }
}
