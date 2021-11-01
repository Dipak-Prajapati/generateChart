package com.dips;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.AreaBreakType;

public class TableFormatForAllWidget extends BarRenderer {

	public static void main(String[] args) throws IOException {
		writeChartToPDF(50, 40, "TableFormat.pdf");
	}

	public static void writeChartToPDF(int width, int height, String fileName) throws FileNotFoundException {
		PdfWriter writer = new PdfWriter(fileName);

		PdfDocument pdfDoc = new PdfDocument(writer);

		try {
			writer = new PdfWriter(fileName);

			Rectangle one = new Rectangle(750, 750);
			PageSize size = new PageSize(one);
			Document doc = new Document(pdfDoc, size);
			doc.setMargins(2, 2, 2, 2);

			PdfPage pdfPage3 = pdfDoc.addNewPage();

			JTable table = generateBarChart2();
			BufferedImage image1 = (BufferedImage) table.createImage(500, 600);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image1, "png", baos);
			ImageData data = ImageDataFactory.create(baos.toByteArray());
			Image iTextImageis = new Image(data);

			iTextImageis.scaleAbsolute(500, 400);
			iTextImageis.setFixedPosition(100f, 200f);
			doc.add(iTextImageis);
			doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JTable generateBarChart2() throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(191.5, "Population", "1750 AD");
		dataSet.setValue(978.5, "Population1", "1800 AD");
		dataSet.setValue(1262, "Population2", "1850 AD");
		dataSet.setValue(1650, "Population3", "1900 AD");
		dataSet.setValue(2519.4, "Population4", "1950 AD");
		dataSet.setValue(4070, "Population5", "2000 AD");
		dataSet.setValue(6070.8, "Population6", "2050 AD");
//		dataSet.setValue(2519, "Population7", "2100 AD");

//		dataSet.setValue(4070, "Population", "2150 AD");
//		dataSet.setValue(6070, "Population", "2200 AD");
//		dataSet.setValue(2519, "Population", "2250 AD");
//		dataSet.setValue(4070, "Population", "2300 AD");
//		dataSet.setValue(6070, "Population", "2350 AD");
//		dataSet.setValue(2519, "Population", "2400 AD");
//		dataSet.setValue(4070, "Population", "2450 AD");
//		dataSet.setValue(6070, "Population", "2500 AD");
//		dataSet.setValue(2519, "Population", "2550 AD");
//		dataSet.setValue(4070, "Population", "2600 AD");
//		dataSet.setValue(6070, "Population", "2650 AD");
		
		TableModel dataSet1 = (TableModel) dataSet;
		DefaultTableModel dm = new DefaultTableModel();
		

		JTable table = new JTable(dataSet1);
		
		return table;
	}
}