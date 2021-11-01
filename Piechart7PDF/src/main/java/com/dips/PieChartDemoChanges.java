package com.dips;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfShading;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;

public class PieChartDemoChanges extends BarRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int dsLength;
	private Paint[] colors;

	public PieChartDemoChanges(final Paint[] colors) {
		this.colors = colors;
	}

	public Paint getItemPaint(final int row, final int column) {
		return this.colors[column % this.colors.length];
	}

	public static void main(String[] args) throws IOException {
		// writeChartToPDF(50, 40, "pcChanges.pdf");
		java.util.List<Customer> customer = new ArrayList<>();
		customer.add(new Customer());
		customer.get(0).setX(81l);
		customer.get(0).setY(37l);
		customer.get(0).setFirstname("first");
		customer.get(0).setLastname("first");

		customer.add(new Customer());
		customer.get(1).setX(95l);
		customer.get(1).setY(2l);
		customer.get(1).setFirstname("second");
		customer.get(1).setLastname("second");

		customer.add(new Customer());
		customer.get(2).setX(0l);
		customer.get(2).setY(37l);
		customer.get(2).setFirstname("third");
		customer.get(2).setLastname("third");

		customer.add(new Customer());
		customer.get(3).setX(64l);
		customer.get(3).setY(2l);
		customer.get(3).setFirstname("fouth");
		customer.get(3).setLastname("fourth");

		customer.add(new Customer());
		customer.get(4).setX(2l);
		customer.get(4).setY(102l);
		customer.get(4).setFirstname("fouth");
		customer.get(4).setLastname("fourth");

		customer.add(new Customer());
		customer.get(5).setX(33l);
		customer.get(5).setY(2l);
		customer.get(5).setFirstname("fouth");
		customer.get(5).setLastname("fourth");

		customer.add(new Customer());
		customer.get(6).setX(2l);
		customer.get(6).setY(2l);
		customer.get(6).setFirstname("fouth");
		customer.get(6).setLastname("fourth");

//		customer.stream().sorted((o1, o2) ->  {
//			Long x = o1.getX();
//			Long x1 = o2.getX();
//			if (x.equals(x1)) {
//				// sort by net value if the largest per security value is the same
//				return o1.getY().compareTo(o2.getY());
//			}
//			// sort by the largest per security value
//			return x.compareTo(x1);
//		});

		Comparator<Customer> compareValue = (Customer o1, Customer o2) -> {
			Long y1 = o1.getY();
			Long y2 = o2.getY();
			int sComp = y1.compareTo(y2);
			if (sComp != 0) {
				return sComp;
			}
			Long x1 = o1.getX();
			Long x2 = o2.getX();
			return x1.compareTo(x2);
		};
//		
		Collections.sort(customer, compareValue);
//		Comparator<Customer> compareById = (Customer o1, Customer o2) -> o1.getDistance(o1).compareTo( o2.getDistance(o2) );
//		
//		Comparator<Customer> compareByI = (Customer o1, Customer o2) -> o1.getY().compareTo( o2.getY() );

//		Collections.sort(customer, compareById);
//		Collections.sort(customer, compareByI);

		for (Customer customer2 : customer) {
			System.out.println(customer2);
		}
	}

	public static String chart2;

	public static void writeChartToPDF(int width, int height, String fileName) throws FileNotFoundException {
		PdfWriter writer = new PdfWriter(fileName);

		PdfDocument pdfDoc = new PdfDocument(writer);

		try {
			writer = new PdfWriter(fileName);

			Rectangle one = new Rectangle(750, 750);
			PageSize size = new PageSize(one);
			Document doc = new Document(pdfDoc, size);
			doc.setMargins(2, 2, 2, 2);

			PdfPage pdfPage1 = pdfDoc.addNewPage();
			// PdfCanvas canvas = getCanvas(pdfPage1);
			getCanvas(pdfPage1);

			/*
			 * com.itextpdf.kernel.color.Color color =
			 * com.itextpdf.kernel.color.Color.BLACK; canvas.setColor(color, true);
			 * canvas.rectangle(0, 0, 300, 750); canvas.setExtGState(new
			 * PdfExtGState().setFillOpacity(0.79f)); canvas.fill();
			 * 
			 * float x = 0; float y = 0; float side = 1200; PdfShading.Axial axial = new
			 * PdfShading.Axial(new PdfDeviceCs.Rgb(), x, y,
			 * com.itextpdf.kernel.color.Color.BLACK.getColorValue(), x + side, y,
			 * com.itextpdf.kernel.color.Color.WHITE.getColorValue()); PdfPattern.Shading
			 * shading = new PdfPattern.Shading(axial); canvas.setFillColorShading(shading);
			 * canvas.moveTo(0, 0); canvas.lineTo(x + (side / 6), 0); canvas.lineTo(x,
			 * (float) (y + (side * Math.sin(Math.PI / 3))));
			 * com.itextpdf.kernel.color.Color color1 =
			 * com.itextpdf.kernel.color.Color.DARK_GRAY; canvas.setStrokeColor(color1);
			 * canvas.closePathFillStroke();
			 */
			Style normal = new Style();
			PdfFont font = PdfFontFactory.createFont();
			normal.setFont(font).setFontSize(30);

			Paragraph para = new Paragraph(new Text("Monthly Analysis Report").addStyle(normal)
					.setFontColor(com.itextpdf.kernel.color.Color.BLACK));
			para.setFixedPosition(340, 400, 400);
			doc.add(para);

			PdfPage pdfPage2 = pdfDoc.addNewPage();
			getCanvas(pdfPage2);
//			PdfCanvas canvas1 = getCanvas(pdfPage2);
			doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

			Style normal2 = new Style();
			PdfFont font2 = PdfFontFactory.createFont();
			normal2.setFont(font2).setFontSize(32);
			Paragraph parags = new Paragraph(new Text("Filters applied on :").addStyle(normal2.setBold().setItalic())
					.setFontColor(com.itextpdf.kernel.color.Color.DARK_GRAY));
			parags.setFixedPosition(350, 550, 400);
			doc.add(parags);

			List list = new List();

			// Add elements to the list
			list.add("Country : India");
			list.add("Asset : Delhi, Punjab, Haryana");
			list.add("Status : In Progress, Approved");
			list.setSymbolIndent(12);
			list.setListSymbol("-");
			list.setFixedPosition(340, 400, 400);
			// list.setBold();
			list.setFontSize(20);
			list.setFontColor(com.itextpdf.kernel.color.Color.BLACK);
			// list.setFont(font);
			doc.add(list);

			Rectangle rect = new Rectangle(0, 0);
			// rect.set
			PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);
			annotation.setBorder(new PdfArray());
			PdfAction action = PdfAction.createURI(
					"https://beta.mdoondemand.com/MDOSF/fuze/ngx-mdo/en/index.html#/home/report/dashboard/761650948893186853");
			annotation.setAction(action);

			// Creating a link
			Link link = new Link("Page link", annotation);
			link.setFontSize(30);
			link.setFontColor(com.itextpdf.kernel.color.Color.BLUE);

			Paragraph parag = new Paragraph(
					new Text("Here is the ").addStyle(normal).setFontColor(com.itextpdf.kernel.color.Color.BLACK));
			parag.add(link.setUnderline());
			parag.setFontSize(20);
			parag.setFixedPosition(340, 280, 400);
			parag.add(new Text(" to check out the live report.").addStyle(normal)
					.setFontColor(com.itextpdf.kernel.color.Color.BLACK));
			doc.add(parag);

			PdfPage pdfPage3 = pdfDoc.addNewPage();
			doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));

			Image barchart = generateBarChart();

			System.out.println(dsLength);
			// System.out.println(chart2);
			if (dsLength > 7) {

				// Pie chart
				Image piechart = generatePieChart();
				piechart.scaleAbsolute(500, 400);
				piechart.setFixedPosition(100f, 200f);
				doc.add(piechart);

				doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));

				// Bar Chart

				barchart.scaleAbsolute(500, 400);
				barchart.setFixedPosition(100f, 200f);
				doc.add(barchart);
				doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));

			} else {
				// Bar Chart
				barchart.scaleAbsolute(360, 250);
				barchart.setFixedPosition(370f, 250f);
				doc.add(barchart);

				// Pie chart
				Image piechart = generatePieChart();
				piechart.scaleAbsolute(360, 250);
				piechart.setFixedPosition(30f, 250f);
				doc.add(piechart);

				doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));

			}

			// TimeSeries

			Image iTextImageis = createTimeSeries();
			iTextImageis.scaleAbsolute(500, 400);
			iTextImageis.setFixedPosition(100f, 200f);

			doc.add(iTextImageis);

			doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
			Image stackedBarChart = createStackedBarChart();
			stackedBarChart.scaleAbsolute(500, 400);
			stackedBarChart.setFixedPosition(100f, 200f);

			doc.add(stackedBarChart);

			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static PdfCanvas getCanvas(PdfPage pdfPage1) {

		PdfCanvas canvas = new PdfCanvas(pdfPage1);

		com.itextpdf.kernel.color.Color color = com.itextpdf.kernel.color.Color.BLACK;
		canvas.setColor(color, true);
		canvas.rectangle(0, 0, 300, 750);
		canvas.setExtGState(new PdfExtGState().setFillOpacity(0.79f));
		canvas.fill();

		float x = 0;
		float y = 0;
		float side = 1200;
		PdfShading.Axial axial = new PdfShading.Axial(new PdfDeviceCs.Rgb(), x, y,
				com.itextpdf.kernel.color.Color.BLACK.getColorValue(), x + side, y,
				com.itextpdf.kernel.color.Color.WHITE.getColorValue());
		PdfPattern.Shading shading = new PdfPattern.Shading(axial);
		canvas.setFillColorShading(shading);
		canvas.moveTo(0, 0);
		canvas.lineTo(x + (side / 6), 0);
		canvas.lineTo(x, (float) (y + (side * Math.sin(Math.PI / 3))));
		com.itextpdf.kernel.color.Color color1 = com.itextpdf.kernel.color.Color.DARK_GRAY;
		canvas.setStrokeColor(color1);
		canvas.closePathFillStroke();
		return canvas;
	}

	public static Image generatePieChart() throws IOException {
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

		plot.setSimpleLabels(true);
//		plot.setLabelGenerator(null);
		plot.setOutlinePaint(null);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"),
				new DecimalFormat("0%")));

//		 PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
//		            "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
//		        plot.setLabelGenerator(gen);

		BufferedImage images = chart.createBufferedImage(500, 400);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(images, "png", baos);
		ImageData data = ImageDataFactory.create(baos.toByteArray());
		Image image = new Image(data);
		return image;
	}

	public static Image generateBarChart() throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(191, "Population", "1750 AD");
		dataSet.setValue(978, "Population", "1800 AD");
		dataSet.setValue(1262, "Population", "1850 AD");
		dataSet.setValue(1650, "Population", "1900 AD");
		dataSet.setValue(2519, "Population", "1950 AD");
		dataSet.setValue(4070, "Population", "2000 AD");
		dataSet.setValue(6070, "Population", "2050 AD");
//		dataSet.setValue(2519, "Population", "2100 AD");

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

		dsLength = dataSet.getColumnCount();
		JFreeChart chart = ChartFactory.createBarChart("", "Year", "Population in millions", dataSet,
				PlotOrientation.VERTICAL, false, true, false);

		// chart.getCategoryPlot().getRangeAxis().setLowerBound(20.0);
		chart.getPlot().setBackgroundPaint(Color.WHITE);
		chart.setTitle("Bar Chart");
		System.out.println(chart.getTitle().getText());
		CategoryPlot plot = chart.getCategoryPlot();
		Stroke solid = new BasicStroke(0);
		plot.setRangeGridlineStroke(solid);
		plot.setDomainGridlineStroke(solid);
		plot.setRangeGridlinesVisible(true);

//		ChartPanel chartpanel = new ChartPanel(chart);
//		chartpanel.setDomainZoomable(true);
		plot.setRangePannable(true);

		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.gray);

		plot.setRangeGridlinePaint(Color.gray);
		plot.setOutlinePaint(Color.gray);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		final CategoryItemRenderer renderer = new PieChartDemoChanges(
				new Paint[] { Color.red, Color.blue, Color.green, Color.yellow, Color.pink, Color.cyan });

		plot.setRenderer(renderer);

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
		final BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
		barRenderer.setMaximumBarWidth(0.30);
		// remove white line from shadow
		barRenderer.setBarPainter(new StandardBarPainter());
		// remove shadow from bar
		barRenderer.setShadowVisible(false);

		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();

		ciRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		ciRenderer.setBaseItemLabelsVisible(true);
		// END
//		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
		// CENTER
		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		// start
//		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE6,TextAnchor.BOTTOM_CENTER);
		ciRenderer.setBasePositiveItemLabelPosition(position1);

		// ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE2,
		// TextAnchor.TOP_CENTER);
		// ciRenderer.setBasePositiveItemLabelPosition(position);

//		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();
//
//		ciRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//		ciRenderer.setBaseItemLabelsVisible(true);
//		
//		ItemLabelPosition position;
//			switch (position.toString().toLowerCase()) {
//			case "end":
//				position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
//				break;
//			case "center":
//				position = new ItemLabelPosition(ItemLabelAnchor.CENTER,TextAnchor.CENTER);
//				break;
//			case "start":
//				position = new ItemLabelPosition(ItemLabelAnchor.INSIDE6,TextAnchor.BOTTOM_CENTER);
//				break;
//			}
//		ciRenderer.setBasePositiveItemLabelPosition(position);

		BufferedImage images = chart.createBufferedImage(500, 400);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(images, "png", baos);
		ImageData data = ImageDataFactory.create(baos.toByteArray());

		Image image = new Image(data);

		return image;
	}

	private static Image createTimeSeries() throws IOException {

		JFreeChart chart = ChartFactory.createTimeSeriesChart("General Unit Trust Prices", // title
				"Date", // x-axis label
				"", // y-axis label
				createDataset(), // data
				true, // create legend?
				false, // generate tooltips?
				false // generate URLs?
		);

		chart.setBackgroundPaint(Color.white);
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

		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM"));

		BufferedImage images = chart.createBufferedImage(500, 400);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(images, "png", baos);
		ImageData data = ImageDataFactory.create(baos.toByteArray());
		Image image = new Image(data);
		return image;

	}

	private static XYDataset createDataset() {

		TimeSeries s1 = new TimeSeries("L&G European Index Trust", Month.class);
		s1.add(new Month(2, 2001), 181.8);
		s1.add(new Month(3, 2001), 167.3);
		s1.add(new Month(4, 2001), 153.8);
		s1.add(new Month(5, 2001), 167.6);
		s1.add(new Month(6, 2001), 158.8);
		s1.add(new Month(7, 2001), 148.3);
		s1.add(new Month(8, 2001), 153.9);
		s1.add(new Month(9, 2001), 142.7);
		s1.add(new Month(10, 2001), 123.2);
		s1.add(new Month(11, 2001), 131.8);
		s1.add(new Month(12, 2001), 139.6);
		s1.add(new Month(1, 2002), 142.9);
		s1.add(new Month(2, 2002), 138.7);
		s1.add(new Month(3, 2002), 137.3);
		s1.add(new Month(4, 2002), 143.9);
		s1.add(new Month(5, 2002), 139.8);
		s1.add(new Month(6, 2002), 137.0);
		s1.add(new Month(7, 2002), 132.8);

		TimeSeries s2 = new TimeSeries("L&G UK Index Trust", Month.class);
		s2.add(new Month(2, 2001), 129.6);
		s2.add(new Month(3, 2001), 123.2);
		s2.add(new Month(4, 2001), 117.2);
		s2.add(new Month(5, 2001), 124.1);
		s2.add(new Month(6, 2001), 122.6);
		s2.add(new Month(7, 2001), 119.2);
		s2.add(new Month(8, 2001), 116.5);
		s2.add(new Month(9, 2001), 112.7);
		s2.add(new Month(10, 2001), 101.5);
		s2.add(new Month(11, 2001), 106.1);
		s2.add(new Month(12, 2001), 110.3);
		s2.add(new Month(1, 2002), 111.7);
		s2.add(new Month(2, 2002), 111.0);
		s2.add(new Month(3, 2002), 109.6);
		s2.add(new Month(4, 2002), 113.2);
		s2.add(new Month(5, 2002), 111.6);
		s2.add(new Month(6, 2002), 108.8);
		s2.add(new Month(7, 2002), 101.6);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		dataset.addSeries(s2);
		dataset.setDomainIsPointsInTime(true);
		return dataset;

	}

	private static Image createStackedBarChart() throws IOException {
		// TODO Auto-generated method stub
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

		dataSet.addValue(20.3, "Product 1 (US)", "Jan 08");
		dataSet.addValue(27.2, "Product 1 (US)", "Feb 08");
		dataSet.addValue(19.7, "Product 1 (US)", "Mar 08");
		dataSet.addValue(20.7, "Product 1 (US)", "Apr 08");
		dataSet.addValue(19.4, "Product 1 (Europe)", "Jan 08");
		dataSet.addValue(10.9, "Product 1 (Europe)", "Feb 08");
		dataSet.addValue(18.4, "Product 1 (Europe)", "Mar 08");
		dataSet.addValue(12.4, "Product 1 (Europe)", "Apr 08");
		dataSet.addValue(16.5, "Product 1 (Asia)", "Jan 08");
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
		dataSet.addValue(14.6, "Product 2 (Europe)", "Mar 08");
		dataSet.addValue(15.4, "Product 2 (Asia)", "Jan 08");
		dataSet.addValue(21.0, "Product 2 (Asia)", "Feb 08");
		dataSet.addValue(11.1, "Product 2 (Asia)", "Mar 08");
		dataSet.addValue(16.1, "Product 2 (Asia)", "Apr 08");

		dataSet.addValue(11.9, "Product 3 (US)", "Jan 08");
		dataSet.addValue(31.0, "Product 3 (US)", "Feb 08");
		dataSet.addValue(22.7, "Product 3 (US)", "Mar 08");
		dataSet.addValue(18.7, "Product 3 (US)", "Apr 08");
		dataSet.addValue(15.3, "Product 3 (Europe)", "Jan 08");
		dataSet.addValue(14.4, "Product 3 (Europe)", "Feb 08");
		dataSet.addValue(25.3, "Product 3 (Europe)", "Mar 08");
		dataSet.addValue(16.3, "Product 3 (Europe)", "Apr 08");
		dataSet.addValue(23.9, "Product 3 (Asia)", "Jan 08");
		dataSet.addValue(19.0, "Product 3 (Asia)", "Feb 08");
		dataSet.addValue(10.1, "Product 3 (Asia)", "Mar 08");
		dataSet.addValue(18.1, "Product 3 (Asia)", "Apr 08");

		dsLength = dataSet.getColumnCount();
		JFreeChart chart = ChartFactory.createStackedBarChart("Stacked Bar Chart", "Category", "Value", dataSet,
				PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(new Color(249, 231, 236));

		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
		KeyToGroupMap map = new KeyToGroupMap("G1");

		map.mapKeyToGroup("Product 1 (US)", "G1");
		map.mapKeyToGroup("Product 1 (Europe)", "G1");
		map.mapKeyToGroup("Product 1 (Asia)", "G1");

		map.mapKeyToGroup("Product 2 (US)", "G2");
		map.mapKeyToGroup("Product 2 (Europe)", "G2");
		map.mapKeyToGroup("Product 2 (Asia)", "G2");

		map.mapKeyToGroup("Product 3 (US)", "G3");
		map.mapKeyToGroup("Product 3 (Europe)", "G3");
		map.mapKeyToGroup("Product 3 (Asia)", "G3");

		renderer.setSeriesToGroupMap(map);

		renderer.setItemMargin(0.0);
		Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(16, 89, 172), 0.0f, 0.0f, new Color(201, 201, 244));
		renderer.setSeriesPaint(0, p1);
		renderer.setSeriesPaint(3, p1);
		renderer.setSeriesPaint(6, p1);

		Paint p2 = new GradientPaint(0.0f, 0.0f, new Color(10, 144, 40), 0.0f, 0.0f, new Color(160, 240, 180));
		renderer.setSeriesPaint(1, p2);
		renderer.setSeriesPaint(4, p2);
		renderer.setSeriesPaint(7, p2);

		Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(255, 35, 35), 0.0f, 0.0f, new Color(255, 180, 180));
		renderer.setSeriesPaint(2, p3);
		renderer.setSeriesPaint(5, p3);
		renderer.setSeriesPaint(8, p3);

		renderer.setGradientPaintTransformer(
				new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));

		SubCategoryAxis domainAxis = new SubCategoryAxis("Product / Month");
		domainAxis.setCategoryMargin(0.05);
		domainAxis.addSubCategory("Product 1");
		domainAxis.addSubCategory("Product 2");
		domainAxis.addSubCategory("Product 3");

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setDomainAxis(domainAxis);
		plot.setRenderer(renderer);
		plot.setFixedLegendItems(createLegendItems());

		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();

		ciRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		ciRenderer.setBaseItemLabelsVisible(true);
		// END
//		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
		// CENTER
		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		// start
//		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE6,TextAnchor.BOTTOM_CENTER);
		ciRenderer.setBasePositiveItemLabelPosition(position1);

		BufferedImage images = chart.createBufferedImage(500, 400);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(images, "png", baos);
		ImageData data = ImageDataFactory.create(baos.toByteArray());
		Image image = new Image(data);
		return image;

	}

	private static LegendItemCollection createLegendItems() {
		LegendItemCollection result = new LegendItemCollection();
		LegendItem item1 = new LegendItem("US", "US", "US", "US", new java.awt.Rectangle(10, 10),
				new GradientPaint(0.0f, 0.0f, new Color(16, 89, 172), 0.0f, 0.0f, new Color(201, 201, 244)));
		LegendItem item2 = new LegendItem("Europe", "Europe", "Europe", "Europe", new java.awt.Rectangle(10, 10),
				new GradientPaint(0.0f, 0.0f, new Color(10, 144, 40), 0.0f, 0.0f, new Color(160, 240, 180)));
		LegendItem item3 = new LegendItem("Asia", "Asia", "Asia", "Asia", new java.awt.Rectangle(10, 10),
				new GradientPaint(0.0f, 0.0f, new Color(255, 35, 35), 0.0f, 0.0f, new Color(255, 180, 180)));

		result.add(item1);
		result.add(item2);
		result.add(item3);

		return result;
	}

}