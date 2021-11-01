package com.dips;

import java.io.FileOutputStream;

import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.sl.usermodel.TableCell.BorderEdge;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Color;

public class PptTableFormat {

	// bug fix to
	// https://svn.apache.org/viewvc/poi/tags/REL_5_0_0/src/ooxml/java/org/apache/poi/xslf/usermodel/XSLFSheet.java?view=markup#l289
	// creates new cells in all new rows and so damages the table
	static XSLFTable createTable(XSLFSheet sheet, int numRows, int numCols) throws Exception {
		if (numRows < 1 || numCols < 1) {
			throw new IllegalArgumentException("numRows and numCols must be greater than 0");
		}
		XSLFTable sh = sheet.createTable();
		for (int r = 0; r < numRows; r++) {
			XSLFTableRow row = sh.addRow(); // this takes over all cells from present rows
			if (r == 0) { // so add new cells only in first row
				for (int c = 0; c < numCols; c++) {
					row.addCell();
				}
			}
		}
		return sh;
	}

	static void setAllCellBorders(XSLFTableCell cell, Color color) {
		cell.setBorderColor(BorderEdge.top, color);
		cell.setBorderColor(BorderEdge.right, color);
		cell.setBorderColor(BorderEdge.bottom, color);
		cell.setBorderColor(BorderEdge.left, color);
	}

	public static void main(String[] args) throws Exception {

//		XMLSlideShow ppt = new XMLSlideShow();
//
//		XSLFSlide slide = ppt.createSlide();
//
//		XSLFTable tbl;
//		XSLFTableRow row;
//		XSLFTableCell cell;
//
//		tbl = slide.createTable();
//		tbl.setAnchor(new Rectangle(new Point(100, 100)));
//
//		row = tbl.addRow();
//		cell = row.addCell();
//		setAllCellBorders(cell, Color.GRAY);
//		cell.setText("Cell 1.1");
//		cell = row.addCell();
//		setAllCellBorders(cell, Color.GRAY);
//		cell.setText("Cell 1.2");
//
//		row = tbl.addRow();
//		cell = tbl.getCell(1, 0);
//		setAllCellBorders(cell, Color.GRAY);
//		cell.setText("Cell 2.1");
//		cell = tbl.getCell(1, 1);
//		setAllCellBorders(cell, Color.GRAY);
//		cell.setText("Cell 2.2");
//
//		String[][] data = { { "R1C1", "R1C2", "R1C3" }, { "R2C1", "R2C2", "R2C3" }, { "R3C1", "R3C2", "R3C3" } };
//
//		tbl = createTable(slide, data.length, data[0].length);
//		tbl.setAnchor(new Rectangle(new Point(100, 300)));
//
//		for (int r = 0; r < data.length; r++) {
//			String[] dataRow = data[r];
//			for (int c = 0; c < dataRow.length; c++) {
//				String value = dataRow[c];
//				cell = tbl.getCell(r, c);
//				setAllCellBorders(cell, Color.GRAY);
//				cell.setText(value);
//			}
//		}
//
		FileOutputStream out = new FileOutputStream("fileName.pptx");
			XWPFDocument document =new XWPFDocument();
		 XWPFTable tab = document.createTable();  
         XWPFTableRow row = tab.getRow(0); // First row  
         // Columns  
         row.getCell(0).setText("Sl. No.");  
         row.addNewTableCell().setText("Name");  
         row.addNewTableCell().setText("Email");  
         row = tab.createRow(); // Second Row  
         row.getCell(0).setText("1.");  
         row.getCell(1).setText("Irfan");  
       //  row.getcell(2).settext("irfan@gmail.com");  
         row = tab.createRow(); // Third Row  
         row.getCell(0).setText("2.");  
         row.getCell(1).setText("Mohan");  
       //  row.getcell(2).settext("mohan@gmail.com");        
		document.write(out);
		out.close();
	}
}
