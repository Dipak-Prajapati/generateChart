package com.dips;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
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

public class TimeSeriesLineChartTotal {
	public static void main(String[] args) throws IOException {
		writeChartToPDF(50, 40, "TimeSeriesLineChart.pdf");
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

			JFreeChart chart = createTimeSeriesLineChart();
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

	private static JFreeChart createTimeSeriesLineChart() throws IOException {

		TimeSeriesCollection dataSet = createDataset();

		JFreeChart chart = ChartFactory.createTimeSeriesChart("General Unit Trust Prices", // title
				"Date", // x-axis label
				"", // y-axis label
				dataSet, // data
				true, // create legend?
				false, // generate tooltips?
				false // generate URLs?
		);

		chart.getXYPlot().setRenderer(new XYSplineRenderer(7));
		chart.setBackgroundPaint(Color.white);
		chart.setTitle("TimeSeries");
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setSeriesShape(0, new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
			renderer.setSeriesShape(1, new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
			renderer.setSeriesShapesVisible(0, true);
			renderer.setSeriesShapesVisible(1, true);
			// renderer.setSeriesFillPaint(0, Color.BLACK);

		}
		plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
		plot.getRenderer().setSeriesStroke(1, new BasicStroke(2.0f));
//		System.out.println("series count :"+dataSet.getSeriesCount());
//		System.out.println("item count :"+dataSet.getItemCount(0));
//		System.out.println("series key :"+dataSet.getSeriesKey(0));

		Random random = new Random();
		for (int i = 0; i < dataSet.getSeriesCount(); i++) {
			float rf = random.nextFloat();
			float g = random.nextFloat();
			float b = random.nextFloat();
			r.setSeriesPaint(i, new Color(rf, g, b));
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM"));

		plot.getRenderer().setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		plot.getRenderer().setBaseItemLabelsVisible(true);
		ItemLabelPosition position = null;

//		switch (choise) {
//		case "end":
//			position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
//			break;
//		case "center":
//			position = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
//			break;
//		case "start":
//			position = new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER);
//			break;
//		}

		switch ("end") {
		case "end":
			position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);
			break;
		case "center":
			position = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
			break;
		case "start":
			position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);
			break;
		}
		plot.getRenderer().setBaseItemLabelFont(new Font("Arial", Font.PLAIN, 8));
		plot.getRenderer().setBasePositiveItemLabelPosition(position);

		// TimeSeriesCollection totalDs = new TimeSeriesCollection();

//		TimeSeries stotal = new TimeSeries("Total");
//		for (int i = 0; i < dataSet.getSeriesCount(); i++) {
//			double total = 0;
//			for (int j = 0; j < dataSet.getItemCount(i); j++) {
//				total = total + (double) dataSet.getXValue(i, j);
//			}
//			System.out.println("total :" + total + "ColumnKey Name :" + dataSet.getSeries(i));
//			
//		}
//		totalDs.addSeries(stotal);		
		TimeSeriesCollection totalDs = new TimeSeriesCollection();
		TimeSeries stotal = new TimeSeries("Total");
//		System.out.println(dataSet.getSeries(0).getIndex(dataSet.getSeries(0).getDataItem(0).getPeriod()));
//		System.out.println(dataSet.getSeries(0).getDataItem(0).getPeriod());
//		System.out.println(dataSet.getSeriesCount());
		
//		int itemCount = 0;
		double total = 0;
		List<Object> regularTimePeriod = new ArrayList<>();
		List<Double> value = new ArrayList<>();
		for (int i = 0; i < dataSet.getSeriesCount(); i++) {

//			total = 0;
			for (int j = 0; j < dataSet.getItemCount(i); j++) {
					//if (dataSet.getSeries(i).getDataItem(i).getPeriod().equals(dataSet.getSeries(i).getDataItem(j).getPeriod()))
//					System.out.println(dataSet.getSeries(i).getDataItem(j).getPeriod() + " -- " + dataSet.getSeries(i).getValue(j));
				regularTimePeriod.add(dataSet.getSeries(i).getDataItem(j).getPeriod());
				value.add((Double) dataSet.getSeries(i).getValue(j));
//					total = total + (double) dataSet.getSeries(j).getValue(j);
//					System.out.println("Data item :" + dataSet.getSeries(j).getDataItem(k).getPeriod());
//					System.out.println("total :" + total + "ColumnKey Name :" + dataSet.getSeries(j).getDataItem(k).getPeriod());
			}
//			itemCount += dataSet.getItemCount(i);
//			stotal.add(dataSet.getSeries(i).getDataItem(i).getPeriod(), total);
		}
		System.out.println(regularTimePeriod.toString());
		System.out.println(value.toString());
//		System.out.println("item count:" + itemCount);
		for(int i = 0 ; i < regularTimePeriod.size()/2 ; i++)
		{
			total = 0;
			for(int j = 0 ; j < regularTimePeriod.size() ; j++) {
				if(regularTimePeriod.get(i).equals(regularTimePeriod.get(j))) {
					total = value.get(i) + value.get(j);  
				}
			}
			stotal.add((RegularTimePeriod) regularTimePeriod.get(i), total);
			System.out.println(regularTimePeriod.get(i) + " " + total);
		}
		totalDs.addSeries(stotal);

		plot.setDataset(1, totalDs);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return chart;

	}

	private static TimeSeriesCollection createDataset() {

		TimeSeries s1 = new TimeSeries("L&G European Index Trust", Month.class);
		s1.add(new Month(2, 2001), 8);
		s1.add(new Month(3, 2001), 3);
		s1.add(new Month(4, 2001), 8);
		s1.add(new Month(5, 2001), 6);
		s1.add(new Month(6, 2001), 8);
		s1.add(new Month(7, 2001), 3);
		s1.add(new Month(8, 2001), 9);
		s1.add(new Month(9, 2001), 7);
		s1.add(new Month(10, 2001), 2);
		s1.add(new Month(11, 2001), 8);
		s1.add(new Month(12, 2001), 6);
		s1.add(new Month(1, 2002), 9);
		s1.add(new Month(2, 2002), 7);
		s1.add(new Month(3, 2002), 3);
		s1.add(new Month(4, 2002), 9);
		s1.add(new Month(5, 2002), 8);
		s1.add(new Month(6, 2002), 0);
		s1.add(new Month(7, 2002), 8);

		TimeSeries s2 = new TimeSeries("L&G UK Index Trust", Month.class);
		s2.add(new Month(2, 2001), 6);
		s2.add(new Month(3, 2001), 2);
		s2.add(new Month(4, 2001), 2);
		s2.add(new Month(5, 2001), 1);
		s2.add(new Month(6, 2001), 6);
		s2.add(new Month(7, 2001), 2);
		s2.add(new Month(8, 2001), 5);
		s2.add(new Month(9, 2001), 7);
		s2.add(new Month(10, 2001), 5);
		s2.add(new Month(11, 2001), 1);
		s2.add(new Month(12, 2001), 3);
		s2.add(new Month(1, 2002), 7);
		s2.add(new Month(2, 2002), 0);
		s2.add(new Month(3, 2002), 6);
		s2.add(new Month(4, 2002), 2);
		s2.add(new Month(5, 2002), 6);
		s2.add(new Month(6, 2002), 8);
		s2.add(new Month(7, 2002), 6);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		dataset.addSeries(s2);
		dataset.setDomainIsPointsInTime(true);
		return dataset;

	}

}
