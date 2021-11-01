package com.dips;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTick;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.Tick;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleEdge;
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

public class TimeSeriesLineChart2 extends DateAxis{
	public static void main(String[] args) throws IOException {
		writeChartToPDF(50, 40, "TimeSeriesLineChart2.pdf");
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

	private static JFreeChart createTimeSeriesLineChart() throws IOException, ParseException {

		TimeSeriesCollection dataSet = createDataset();

		TimeSeries stotal = new TimeSeries("Total");
		List<Object> regularTimePeriod = new ArrayList<>();
		List<Double> value = new ArrayList<>();

		for (int i = 0; i < dataSet.getSeriesCount(); i++) {
			for (int j = 0; j < dataSet.getItemCount(i); j++) {
				regularTimePeriod.add(dataSet.getSeries(i).getDataItem(j).getPeriod());
				value.add((Double) dataSet.getSeries(i).getValue(j));
			}
		}

		List<Object> newList = regularTimePeriod.stream().distinct().collect(Collectors.toList());
		double total = 0;
		for (int i = 0; i < newList.size(); i++) {
			if (dataSet.getSeries(0).getItemCount() > i) {
				total = value.get(i);
			} else {
				total = 0;
			}
			for (int j = i + 1; j < regularTimePeriod.size(); j++) {
				if (newList.get(i).equals(regularTimePeriod.get(j))) {
					total = total + value.get(j);
				}
			}
			stotal.add((RegularTimePeriod) newList.get(i), total);
		}
		dataSet.addSeries(stotal);

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
		
		// set series shapes
		XYItemRenderer xyItemRenderer = plot.getRenderer();
		if (xyItemRenderer instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) xyItemRenderer;
			for (int i = 0; i < dataSet.getSeriesCount(); i++) {
				renderer.setSeriesShape(i, new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
				renderer.setSeriesShapesVisible(i, true);
				plot.getRenderer().setSeriesStroke(i, new BasicStroke(2.0f));
			}
		}

		Random random = new Random();
		for (int i = 0; i < dataSet.getSeriesCount(); i++) {
			float rf = random.nextFloat();
			float g = random.nextFloat();
			float b = random.nextFloat();
			xyItemRenderer.setSeriesPaint(i, new Color(rf, g, b));
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM"));
		axis.setTickMarksVisible(true);
		axis.setVerticalTickLabels(true);
//		axis.setLowerMargin(0.2);
//		axis.setUpperMargin(0.2);
		axis.setLabelAngle(-45);
		plot.getRenderer().setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		plot.getRenderer().setBaseItemLabelsVisible(true);
		ItemLabelPosition position = null;
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

		return chart;
	}

	private static TimeSeriesCollection createDataset() throws ParseException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM-YYYY");
		TimeSeries s1 = new TimeSeries("L&G European Index Trust", Month.class);
//		s1.add(new Minute(dateFormat.parse(new Month(2,2001).toString())),5);
//		s1.add(new Minute(dateFormat.parse(new Month(3,2001).toString())),6);
//		s1.add(new Minute(dateFormat.parse(new Month(4,2001).toString())),7);
//		s1.add(new Minute(dateFormat.parse(new Month(5,2001).toString())),8);
//		s1.add(new Minute(dateFormat.parse(new Month(6,2001).toString())),9);
//		s1.add(new Minute(dateFormat.parse(new Month(7,2001).toString())),18);
//		s1.add(new Minute(dateFormat.parse(new Month(2,2002).toString())),5);
//		s1.add(new Minute(dateFormat.parse(new Month(3,2002).toString())),6);
//		s1.add(new Minute(dateFormat.parse(new Month(4,2002).toString())),7);
//		s1.add(new Minute(dateFormat.parse(new Month(5,2002).toString())),8);
//		s1.add(new Minute(dateFormat.parse(new Month(6,2002).toString())),9);
//		s1.add(new Minute(dateFormat.parse(new Month(7,2002).toString())),18);
		s1.add(new Month(2, 2001), 8);
		s1.add(new Month(3, 2001), 6);
		s1.add(new Month(4, 2001), 9);
		s1.add(new Month(5, 2001), 2);
		s1.add(new Month(6, 2001), 7);
		s1.add(new Month(7, 2001), 4);
		s1.add(new Month(8, 2001), 6);
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
		s1.add(new Month(2, 2003), 8);
		s1.add(new Month(3, 2003), 6);
		s1.add(new Month(4, 2003), 9);
		s1.add(new Month(5, 2003), 2);
		s1.add(new Month(6, 2003), 7);
		s1.add(new Month(2, 2004), 18);
		s1.add(new Month(3, 2004), 26);
		s1.add(new Month(4, 2004), 19);
		s1.add(new Month(5, 2004), 12);
		s1.add(new Month(6, 2004), 37);
		s1.add(new Month(2, 2021), 18);
		s1.add(new Month(3, 2021), 6);
		s1.add(new Month(4, 2021), 9);
		s1.add(new Month(5, 2021), 12);
		s1.add(new Month(6, 2021), 37);

		TimeSeries s2 = new TimeSeries("L&G UK Index Trust", Month.class);
		s2.add(new Month(2, 2001), 6);
		s2.add(new Month(3, 2001), 3);
		s2.add(new Month(4, 2001), 1);
		s2.add(new Month(5, 2001), 5);
		s2.add(new Month(6, 2001), 8);
		s2.add(new Month(7, 2001), 7);
		s2.add(new Month(8, 2001), 9);
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

		TimeSeries s3 = new TimeSeries("L&G India Index Trust", Month.class);
		s3.add(new Month(2, 2001), 2);
		s3.add(new Month(3, 2001), 4);
		s3.add(new Month(4, 2001), 15);
		s3.add(new Month(5, 2001), 8);
		s3.add(new Month(6, 2001), 7);
		s3.add(new Month(7, 2001), 6);
		s3.add(new Month(8, 2001), 5);
		s3.add(new Month(9, 2001), 4);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		dataset.addSeries(s2);
		dataset.addSeries(s3);
		dataset.setDomainIsPointsInTime(true);
		return dataset;

	}
//	/**
//	 * @see org.jfree.chart.axis.DateAxis#refreshTicksHorizontal(java.awt.Graphics2D, java.awt.geom.Rectangle2D, org.jfree.ui.RectangleEdge)
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
//		List ticks = super.refreshTicksHorizontal(g2, dataArea, edge);
//		List ret = new ArrayList();
//		for (Tick tick : (List<Tick>)ticks) {
//			if (tick instanceof DateTick) {
//				DateTick dateTick = (DateTick)tick;
//				ret.add(new DateTick(dateTick.getDate(), dateTick.getText(), dateTick.getTextAnchor(), dateTick.getRotationAnchor(), getLabelAngle()));
//			} else {
//				ret.add(tick);
//			}
//		}
//		return ret;
//	}
}
