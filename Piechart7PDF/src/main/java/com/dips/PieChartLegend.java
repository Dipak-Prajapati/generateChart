package com.dips;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;

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

public class PieChartLegend {
	public static void main(String[] args) throws IOException {
		writeChartToPDF(50, 40, "PieChart.pdf");
		
		List<String> check = new ArrayList<>();
		check.add("a");
		check.stream().forEach((k) -> {
		    System.out.print(k + " ");
		});
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

			JFreeChart chart = generatePieChart();
			BufferedImage image1 = chart.createBufferedImage(600, 600);
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
	
	public static JFreeChart generatePieChart() throws IOException {
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("China", 19.64);
		dataSet.setValue("India", 17.3);
		dataSet.setValue("United States", 4.54);
		dataSet.setValue("Indonesia", 3.4);
		dataSet.setValue("Brazil", 2.83);
		dataSet.setValue("Pakistan", 2.48);
		dataSet.setValue("Bangladesh", 2.38);

		JFreeChart chart = ChartFactory.createPieChart("", dataSet, true, false, false);

		chart.getPlot().setBackgroundPaint(Color.WHITE);
		PiePlot plot = (PiePlot) chart.getPlot();

		plot.setLegendItemShape(new java.awt.Rectangle(50, 15));
		LegendTitle legend = chart.getLegend();
		legend.setFrame(BlockBorder.NONE);
		legend.setPosition(RectangleEdge.TOP);

		plot.setSimpleLabels(false);
//		plot.setLabelGenerator(null);
		plot.setOutlinePaint(null);
		plot.setLabelOutlinePaint(null);
		plot.setLabelShadowPaint(null);
		plot.setLabelBackgroundPaint(null);
		plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
		plot.setLabelFont(new Font("TimesRoman", Font.PLAIN, 20));
		
		SecureRandom random = new SecureRandom();
		for (int color = 0; color < dataSet.getItemCount(); color++) {
			float r = random.nextFloat();
			float g = random.nextFloat();
			float b = random.nextFloat();
			plot.setSectionPaint(dataSet.getKey(color), new Color(r, g, b));
			
		}
//		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"),
//				new DecimalFormat("0%")));
		
//		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} : {2}", new DecimalFormat("0"),
//				new DecimalFormat("0%")));

//		plot.setLabelGenerator(
//				new StandardPieSectionLabelGenerator("{0} : {2}", new DecimalFormat("0"), new DecimalFormat("0%")));

		plot.setLabelGenerator(
				new StandardPieSectionLabelGenerator("{0} : {1}", new DecimalFormat("0"), new DecimalFormat("0%")));
		
		
//		 PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
//		            "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
//		        plot.setLabelGenerator(gen);

		return chart;
	}
	
}
