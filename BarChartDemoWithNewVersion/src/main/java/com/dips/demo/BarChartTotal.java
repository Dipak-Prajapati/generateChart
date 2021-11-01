package com.dips.demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.Range;
import org.jfree.data.RangeType;
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

public class BarChartTotal extends BarRenderer {

	public static void main(String[] args) throws IOException {
		writeChartToPDF(50, 40, "BarChartTotal123.pdf");
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

			JFreeChart chart = generateBarChart2();
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

	public static JFreeChart generateBarChart2() throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(191, "Population", "1750 AD");
		dataSet.setValue(978.5, "Population1", "1800 AD");
		dataSet.setValue(350, "Population2", "1850 AD");
		dataSet.setValue(1650, "Population3", "1900 AD");
		dataSet.setValue(0.4, "Population4", "1950 AD");
		dataSet.setValue(4070, "Population5", "2000 AD");
		dataSet.setValue(6070.8, "Population6", "2050 AD");
		dataSet.setValue(12070, "Population7", "2060 AD");
		dataSet.setValue(6, "Population8", "2100 AD");

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

		double total = 0;
		for (int i = 0; i < dataSet.getColumnCount(); i++) {
			if(null != dataSet.getValue(i, i)) {
				total = total + (double) dataSet.getValue(i, i);
			}
//				total = total + dataSet.getValue(i, i).doubleValue();
//			System.out.println("total :" + total + "ColumnKey Name :" + dataSet.getColumnKey(i));
		}
//		dataSet.addValue(total, "TOTAL", "TOTAL");

		System.out.println("Data Set ToString () : "+dataSet.getColumnKeys());
		System.out.println("Data Set ToString () : "+dataSet.getRowKeys());
		System.out.println("Data Set ToString () : "+dataSet.getValue(0, 0));
		
		JFreeChart chart = ChartFactory.createBarChart("", "Year", "Population in millions", dataSet,
				PlotOrientation.VERTICAL, true, true, false);

		chart.getPlot().setBackgroundPaint(Color.WHITE);
		chart.setTitle("Bar Chart");

		CategoryPlot plot = chart.getCategoryPlot();
		Stroke solid = new BasicStroke(0);
		plot.setRangeGridlineStroke(solid);
		plot.setDomainGridlineStroke(solid);
		plot.setRangeGridlinesVisible(true);

		plot.setRangePannable(true);

		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.gray);

		plot.setRangeGridlinePaint(Color.gray);
		plot.setOutlinePaint(Color.gray);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		Long scaleSize = 1500L;
		NumberAxis xAxis = (NumberAxis) plot.getRangeAxis(); 
		
		
		if(scaleSize != null) {
//			xAxis.setLowerBound(500.0);
//			xAxis.getLowerBound();
//			xAxis.setUpperBound(7000.0);
//			xAxis.setAutoTickUnitSelection(false);
//			xAxis.setTickUnit(new NumberTickUnit(scaleSize));
			xAxis.setRange(500, 7000);
			xAxis.setRange(new Range(500,7000),true,false);
			xAxis.setAutoRangeMinimumSize(1, false);
//			xAxis.setDefaultAutoRange(new Range(500,7000));
			xAxis.setTickUnit(new NumberTickUnit(scaleSize.doubleValue(),new DecimalFormat("0")));
			System.out.println(xAxis.getRangeType());
			System.out.println(xAxis.getTickUnit());
			System.out.println(xAxis.getLowerBound());
			System.out.println(xAxis.getUpperBound());
//			xAxis.setAutoRange(false);
		}
		
//		xAxis.setAutoRangeIncludesZero(true);
//		ValueAxis vAxis = plot.getRangeAxis();
//		double lb = 500;
//		double ub = 10000;
//		vAxis.setLowerBound(lb);
//		vAxis.setUpperBound(ub);
		
		plot.setRangeAxis(xAxis);	//set range of x-axis

		
	 //Now add your markers
	  ValueMarker vm = new ValueMarker(1500); //200 is the position you like it to be
	  vm.setPaint(Color.RED);
	  vm.setStroke(new BasicStroke(1));
	  vm.setLabel("BeanchMark value"); //The label
	  vm.setLabelAnchor(RectangleAnchor.TOP);
	  vm.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
	  plot.addRangeMarker(vm);
		
		
		CategoryItemRenderer renderer = plot.getRenderer();
		Random random = new Random();
		for (int i = 0; i < dataSet.getRowCount(); i++) {
			float r = random.nextFloat();
			float g = random.nextFloat();
			float b = random.nextFloat();
			renderer.setSeriesPaint(dataSet.getRowIndex(dataSet.getRowKey(i)),new Color(r, g, b));
		}

		String position = "top";
		if (chart.getLegend() != null) {
			RectangleEdge p = RectangleEdge.BOTTOM;
			LegendTitle legend = chart.getLegend();
			switch (position.toLowerCase()) {
			case "top":
				p = RectangleEdge.TOP;
				break;
			case "bottom":
				p = RectangleEdge.BOTTOM;
				break;
			case "left":
				p = RectangleEdge.LEFT;
				break;
			case "right":
				p = RectangleEdge.RIGHT;
				break;
			}
			legend.setPosition(p);
//			legend.getItemPaint();
		}
		final BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
//		barRenderer.setMaximumBarWidth(30.05);
		
		if(dataSet.getColumnCount() >= 7) {			
			barRenderer.setItemMargin(-5);
		}else {
			barRenderer.setItemMargin(-1);
		}

//		barRenderer.setMaximumBarWidth(0.30);
		// remove white line from shadow
		barRenderer.setBarPainter(new StandardBarPainter());
		// remove shadow from bar
		barRenderer.setShadowVisible(false);

		
		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();
		ciRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		ciRenderer.setDefaultItemLabelsVisible(true);
		ItemLabelPosition position1 = null;
		ItemLabelPosition fallBackPosition = null;
		String choice = "end";
		if (plot.getOrientation().equals(PlotOrientation.VERTICAL)) {
			switch (choice) {
			case "end":
				position1 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
				break;
			case "center":
				position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER);
				break;
			case "start":
				position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER);
				break;
			}
		} else if (plot.getOrientation().equals(PlotOrientation.HORIZONTAL)) { // check the orientation HORIZONTAL is true
			switch (choice) {
			case "end":
				position1 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_RIGHT);
				break;
			case "center":
				position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.INSIDE9, TextAnchor.CENTER_LEFT);
				break;
			case "start":
				position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE9, TextAnchor.CENTER_LEFT);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.INSIDE9, TextAnchor.CENTER_LEFT);
				break;
			}
		}
		ciRenderer.setDefaultItemLabelFont(new Font("Arial", Font.PLAIN, 8));
		ciRenderer.setDefaultPositiveItemLabelPosition(position1);
		barRenderer.setPositiveItemLabelPositionFallback(fallBackPosition);
		
		return chart;
	}
}