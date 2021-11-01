package com.dips;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
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

public class TimeSeriesChart extends BarRenderer {

	public static void main(String[] args) throws IOException {
		writeChartToPDF(50, 40, "TimeSeriesChart.pdf");
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

			JFreeChart chart = createTimeSeriesBarChart();
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

	public static JFreeChart createTimeSeriesBarChart() throws IOException {

		DefaultCategoryDataset dataSet = getTimeSeriesBarChartDataSetValues();

		JFreeChart chart = null;
		String xAxisLable = "20";
		String yAxisLable = "30";
		String widgetName = "Time Series Bar chart";

		chart = ChartFactory.createBarChart(widgetName, xAxisLable, yAxisLable, dataSet, PlotOrientation.VERTICAL, true,
				true, false);

		chart.getPlot().setBackgroundPaint(Color.WHITE);
		CategoryPlot plot = chart.getCategoryPlot();
		Stroke solid = new BasicStroke(0);
		plot.setRangeGridlineStroke(solid);
		plot.setDomainGridlineStroke(solid);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.gray);
		plot.setRangeGridlinePaint(Color.gray);
		plot.setOutlinePaint(Color.gray);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
//		plot.mapDatasetToRangeAxis(1, 1);
//		XYPlot plot2 = (XYPlot) chart.getPlot();
//		plot2.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		CategoryItemRenderer renderer = plot.getRenderer();
		// set the random color in bar
		Random random = new Random();
		for (int color = 0; color < dataSet.getRowCount(); color++) {
			float r = random.nextFloat();
			float g = random.nextFloat();
			float b = random.nextFloat();
			renderer.setSeriesPaint(dataSet.getRowIndex(dataSet.getRowKey(color)), new Color(r, g, b));
		}

		// set legend position
		if (chart.getLegend() != null) {
			RectangleEdge position = RectangleEdge.BOTTOM;

			LegendTitle legend = chart.getLegend();
			switch ("top") {
			case "top":
				position = RectangleEdge.TOP;
				break;
			case "bottom":
				position = RectangleEdge.BOTTOM;
				break;
			case "left":
				position = RectangleEdge.LEFT;
				break;
			case "right":
				position = RectangleEdge.RIGHT;
				break;
			}
			legend.setPosition(position);
		}

		CategoryAxis axis = plot.getDomainAxis();
		axis.setLowerMargin(0.01);
		axis.setUpperMargin(0.01);

		final BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
		barRenderer.setMaximumBarWidth(0.30);
		// remove white line from shadow
		barRenderer.setBarPainter(new StandardBarPainter());
		// remove shadow from bar
		barRenderer.setShadowVisible(false);

		// check datalable is true
		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();
		ciRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		ciRenderer.setBaseItemLabelsVisible(true);
		ItemLabelPosition position1 = null;
		ItemLabelPosition fallBackPosition = null;
		String choice = "end";
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
		ciRenderer.setBaseItemLabelFont(new Font("Arial", Font.PLAIN, 8));
		ciRenderer.setBasePositiveItemLabelPosition(position1);
		barRenderer.setPositiveItemLabelPositionFallback(fallBackPosition);

		DefaultCategoryDataset totalDs = new DefaultCategoryDataset();
		for (int i = 0; i < dataSet.getColumnCount(); i++) {
			double total = 0;
			for (int j = 0; j < dataSet.getRowCount(); j++) {
				if (null != dataSet.getValue(j, i)) {
					total = total + (double) dataSet.getValue(j, i);
				}
			}
			System.out.println("total :" + total + "ColumnKey Name :" + dataSet.getColumnKey(i));
			totalDs.addValue(total, "TOTAL", dataSet.getColumnKey(i));
		}

		plot.setDataset(1, totalDs);
		BarRenderer renderer2 = new BarRenderer();
		renderer2.setSeriesPaint(1, Color.black);
		// renderer2.setMaximumBarWidth(0.03);
		// renderer2.setFillPaint(false);
		renderer2.setBaseOutlinePaint(Color.black);
		// renderer2.setArtifactPaint(Color.black);
		// remove white line from shadow
		renderer2.setBarPainter(new StandardBarPainter());
		// remove shadow from bar
		renderer2.setShadowVisible(false);

		plot.setRenderer(1, renderer2);

		CategoryItemRenderer cIrenderer = plot.getRenderer(1);
		// set the random color in bar
		Random random1 = new Random();
		for (int color = 0; color < totalDs.getRowCount(); color++) {
			float r = random1.nextFloat();
			float g = random1.nextFloat();
			float b = random1.nextFloat();
			cIrenderer.setSeriesPaint(totalDs.getRowIndex(totalDs.getRowKey(color)), new Color(r, g, b));
//			cIrenderer.setSeriesPaint(totalDs.getRowIndex(totalDs.getRowKey(color)), new Color(230,230,230));
		}

		// check datalable is true
		if (true) {
			CategoryItemRenderer ciRenderer1 = ((CategoryPlot) chart.getPlot()).getRenderer(1);
			ciRenderer1.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			ciRenderer1.setBaseItemLabelsVisible(true);
			ItemLabelPosition fallBackPosition2 = null;
			ItemLabelPosition position = null;
			switch (choice) {
			case "end":
				position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
				fallBackPosition2 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
				break;
			case "center":
				position = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
				fallBackPosition2 = new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER);
				break;
			case "start":
				position = new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER);
				fallBackPosition2 = new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER);
				break;
			}
			ciRenderer1.setBaseItemLabelFont(new Font("Arial", Font.PLAIN, 8));
			ciRenderer1.setBasePositiveItemLabelPosition(position);
			barRenderer.setPositiveItemLabelPositionFallback(fallBackPosition);
		}

		return chart;
	}

	private static DefaultCategoryDataset getTimeSeriesBarChartDataSetValues() {
		// TODO Auto-generated method stub
		final String fiat = "FIAT";
		final String audi = "AUDI";
		final String ford = "FORD";
		final String speed = "Speed";
		final String millage = "Millage";
		final String userrating = "User Rating";
		final String safety = "safety";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(10.0, fiat, speed);
		dataset.addValue(30.0, fiat, userrating);
		dataset.addValue(50.0, fiat, millage);
		dataset.addValue(50.0, fiat, safety);

		dataset.addValue(500.0, audi, speed);
		dataset.addValue(60.0, audi, userrating);
		dataset.addValue(100.0, audi, millage);
		dataset.addValue(350.0, audi, safety);

		dataset.addValue(4.0, ford, speed);
		dataset.addValue(2.0, ford, userrating);
		dataset.addValue(3.0, ford, millage);
		dataset.addValue(6.0, ford, safety);

		return dataset;
	}
}