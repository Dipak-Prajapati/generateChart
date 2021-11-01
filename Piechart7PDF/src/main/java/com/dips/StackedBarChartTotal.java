package com.dips;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

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

public class StackedBarChartTotal {

	public static void main(String[] args) throws IOException {
		writeChartToPDF(50, 40, "StackedBarChartTotal.pdf");
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

			JFreeChart chart = createStackedBarChart();
			BufferedImage image1 = chart.createBufferedImage(500, 600);
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

	private static JFreeChart createStackedBarChart() throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

		dataSet.addValue(20.3, "Product 1 (US)", "Jan 08");
		dataSet.addValue(27.2, "Product 1 (US)", "Feb 08");
		dataSet.addValue(19.7, "Product 1 (US)", "Mar 08");
		dataSet.addValue(20.7, "Product 1 (US)", "Apr 08");
		
		dataSet.addValue(19.4, "Product 1 (Europe)", "Jan 08");
		dataSet.addValue(10.9, "Product 1 (Europe)", "Feb 08");
		dataSet.addValue(18.4, "Product 1 (Europe)", "Mar 08");
		dataSet.addValue(12.4, "Product 1 (Europe)", "Apr 08");
		
//		dataSet.addValue(16.5, "Product 1 (Asia)", "Jan 08");
		dataSet.addValue(15.9, "Product 1 (Asia)", "Feb 08");
		dataSet.addValue(16.1, "Product 1 (Asia)", "Mar 08");
		dataSet.addValue(14.4, "Product 1 (Asia)", "Apr 08");

		dataSet.addValue(23.3, "Product 2 (US)", "Jan 08");
		dataSet.addValue(16.2, "Product 2 (US)", "Feb 08");
		dataSet.addValue(28.7, "Product 2 (US)", "Mar 08");
		dataSet.addValue(22.7, "Product 2 (US)", "Apr 08");
		
		dataSet.addValue(12.7, "Product 2 (Europe)", "Jan 08");
		dataSet.addValue(17.9, "Product 2 (Europe)", "Feb 08");
		dataSet.addValue(12.6, "Product 2 (Europe)", "Mar 08");
		dataSet.addValue(14.6, "Product 2 (Europe)", "Apr 08");
		
		dataSet.addValue(15.4, "Product 2 (Asia)", "Jan 08");
		dataSet.addValue(21.0, "Product 2 (Asia)", "Feb 08");
		dataSet.addValue(11.1, "Product 2 (Asia)", "Mar 08");
		dataSet.addValue(16.1, "Product 2 (Asia)", "Apr 08");

		dataSet.addValue(11.9, "Product 3 (US)", "Jan 08");
		dataSet.addValue(31.0, "Product 3 (US)", "Feb 08");
		dataSet.addValue(22.7, "Product 3 (US)", "Mar 08");
//		dataSet.addValue(18.7, "Product 3 (US)", "Apr 08");
//		dataSet.addValue(1, "Product 3 (US)", "May 08");
		
		dataSet.addValue(15.3, "Product 3 (Europe)", "Jan 08");
		dataSet.addValue(14.4, "Product 3 (Europe)", "Feb 08");
		dataSet.addValue(25.3, "Product 3 (Europe)", "Mar 08");
		dataSet.addValue(16.3, "Product 3 (Europe)", "Apr 08");
//		dataSet.addValue(1, "Product 3 (Europe)", "May 08");
		
		dataSet.addValue(23.9, "Product 3 (Asia)", "Jan 08");
		dataSet.addValue(19.0, "Product 3 (Asia)", "Feb 08");
		dataSet.addValue(5, "Product 3 (Asia)", "Mar 08");
		dataSet.addValue(18.1, "Product 3 (Asia)", "Apr 08");
		dataSet.addValue(1, "Product 3 (Asia)", "May 08");
		
		

		List<Object> columnKey = new ArrayList<>();
		List<Double> value = new ArrayList<>();
		System.out.println(dataSet.getColumnCount());
		System.err.println(dataSet.getRowCount());
		for (int i = 0; i < dataSet.getColumnCount(); i++) {
			double total = 0;
			for (int j = 0; j < dataSet.getRowCount(); j++) {
				if(null != dataSet.getValue(j, i)) {
					total =total + (double) dataSet.getValue(j, i);
				}
			}
			System.out.println("total :" + total + "ColumnKey Name :" + dataSet.getColumnKey(i));
			columnKey.add(dataSet.getColumnKey(i));
			value.add(total);
//			dataSet.addValue(total, "TOTAL", dataSet.getColumnKey(i));
		}
		
		System.out.println(columnKey.size());
		System.out.println(value.size());
		
		for(int i = 0 ; i < columnKey.size() ; i++ )
		{
			dataSet.addValue(value.get(i), "TOTAL", (Comparable) columnKey.get(i));
		}
		
		JFreeChart chart = ChartFactory.createStackedBarChart("Stacked Bar Chart", "Category", "Value", dataSet,
				PlotOrientation.VERTICAL, true, true, false);

//		chart.setBackgroundPaint(new Color(249, 231, 236));
		chart.setTitle("Stacked BarChart");
		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		Random rand = new Random();
		for (int i = 0; i < dataSet.getRowCount(); i++) {
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			renderer.setSeriesPaint(dataSet.getRowIndex(dataSet.getRowKey(i)), new Color(r, g, b));
		}
		
		Long scaleSize = 1000L;
		NumberAxis xAxis = (NumberAxis) plot.getRangeAxis(); 
		xAxis.setLowerBound(200);
		if(scaleSize != null) {
			
//			xAxis.setRange(0, 7000);
			xAxis.setTickUnit(new NumberTickUnit(scaleSize));
		}
		xAxis.setAutoRange(false);
		xAxis.setAutoRangeIncludesZero(false);
		
		xAxis.setUpperBound(7000);
		plot.setRangeAxis(xAxis);	//set range of x-axis

		SubCategoryAxis domainAxis = new SubCategoryAxis("Product / Month");
		domainAxis.setCategoryMargin(0.05);
//		domainAxis.addSubCategory("Product 1");
//		domainAxis.addSubCategory("Product 2");
//		domainAxis.addSubCategory("Product 3");
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		plot.setDomainAxis(domainAxis);
		plot.setRenderer(renderer);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
//		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		final BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
		barRenderer.setMaximumBarWidth(0.30);
		// remove white line from shadow
		barRenderer.setBarPainter(new StandardBarPainter());
		// remove shadow from bar
		barRenderer.setShadowVisible(false);

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
		}

		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();
		ciRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		ciRenderer.setBaseItemLabelsVisible(true);
		ItemLabelPosition position1 = null;
		ItemLabelPosition fallBackPosition = null;
		String choice = "end";
		if (plot.getOrientation().equals(PlotOrientation.VERTICAL)) {
			switch (choice) {
			case "end":
				position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.TOP_CENTER);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
				break;
			case "center":
				position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
				break;
			case "start":
				position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
				break;
			}
		} else if (plot.getOrientation().equals(PlotOrientation.HORIZONTAL)) { // check the orientation HORIZONTAL is true
			switch (choice) {
			case "end":
				position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE3, TextAnchor.BASELINE_RIGHT);
				fallBackPosition = new ItemLabelPosition(ItemLabelAnchor.INSIDE9, TextAnchor.CENTER_LEFT);
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
		ciRenderer.setBaseItemLabelFont(new Font("Arial", Font.PLAIN, 8));
		ciRenderer.setBasePositiveItemLabelPosition(position1);
		barRenderer.setPositiveItemLabelPositionFallback(fallBackPosition);
		
//		DefaultCategoryDataset totalDs = new DefaultCategoryDataset();
//		for (int i = 0; i < dataSet.getColumnCount(); i++) {
//			double total = 0;
//			for (int j = 0; j < dataSet.getRowCount(); j++) {
//				total = total + (double) dataSet.getValue(j, i);
//			}
//			System.out.println("total :" + total + "ColumnKey Name :" + dataSet.getColumnKey(i));
//			totalDs.addValue(total, "TOTAL", dataSet.getColumnKey(i));
//		}
//
//		plot.setDataset(1, totalDs);
//		BarRenderer renderer2 = new BarRenderer();
//		renderer2.setSeriesPaint(1, Color.black);
//		renderer2.setBaseOutlinePaint(Color.black);
//		// renderer2.setMaximumBarWidth(0.03);
//		// renderer2.setFillPaint(false);
//		// renderer2.setArtifactPaint(Color.black);
//		renderer2.setBarPainter(new StandardBarPainter());
//		renderer2.setShadowVisible(false);
//
//		plot.setRenderer(1, renderer2);
//
//		CategoryItemRenderer cIrenderer = plot.getRenderer(1);
//		// set the random color in bar
//		Random random1 = new Random();
//		for (int color = 0; color < totalDs.getRowCount(); color++) {
//			float r = random1.nextFloat();
//			float g = random1.nextFloat();
//			float b = random1.nextFloat();
//			cIrenderer.setSeriesPaint(totalDs.getRowIndex(totalDs.getRowKey(color)), new Color(r, g, b));
//			//cIrenderer.setSeriesPaint(totalDs.getRowIndex(totalDs.getRowKey(color)), new Color(230,230,230));
//		}

		return chart;

	}

}
