package com.dips;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
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
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
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
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
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
import org.jfree.util.PaintList;

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

public class ChartAdjustmentLogic extends BarRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int dsLength;
	private Paint[] colors;

	public ChartAdjustmentLogic(Paint[] colors) {
		this.colors = colors;
	}

	public Paint getItemPaint(final int row, final int column) {
		return this.colors[column % this.colors.length];
	}

	public static void main(String[] args) throws IOException {
		writeChartToPDF(50, 40, "chartAdjustment.pdf");
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
			getCanvas(pdfPage1);

			Style normal = new Style();
			PdfFont font = PdfFontFactory.createFont();
			normal.setFont(font).setFontSize(30);

			Paragraph para = new Paragraph(new Text("Monthly Analysis Report").addStyle(normal)
					.setFontColor(com.itextpdf.kernel.color.Color.BLACK));
			para.setFixedPosition(340, 400, 400);
			doc.add(para);

			PdfPage pdfPage2 = pdfDoc.addNewPage();
			getCanvas(pdfPage2);
			doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

			Style normal2 = new Style();
			PdfFont font2 = PdfFontFactory.createFont();
			normal2.setFont(font2).setFontSize(32);
			Paragraph parags = new Paragraph(new Text("Filters applied on :").addStyle(normal2.setBold().setItalic())
					.setFontColor(com.itextpdf.kernel.color.Color.DARK_GRAY));
			parags.setFixedPosition(350, 550, 400);
			doc.add(parags);

			List list = new List();

			// Add elements to the list list.add("Country : India");
			list.add("Asset : Delhi, Punjab, Haryana");
			list.add("Status : In Progress, Approved");
			list.setSymbolIndent(12);
			list.setListSymbol("-");
			list.setFixedPosition(340, 400, 400);
			list.setFontSize(20);
			list.setFontColor(com.itextpdf.kernel.color.Color.BLACK);
			doc.add(list);

			Rectangle rect = new Rectangle(0, 0);
			PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);
			annotation.setBorder(new PdfArray());
			PdfAction action = PdfAction.createURI(
					"https://beta.mdoondemand.com/MDOSF/fuze/ngx-mdo/en/index.html#/home/report/dashboard/761650948893186853");
			annotation.setAction(action);

			//Creating a link 
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

			java.util.List<JFreeChart> chartList = new ArrayList<>();

//			chartList.add(generatePieChart());
//			chartList.add(generatePieChart());
//			chartList.add(generatePieChart());
//			chartList.add(createTimeSeries());
//			chartList.add(generateBarChart());
			chartList.add(createTimeSeries());
			chartList.add(generateBarChart2());
			chartList.add(generatePieChart());
			chartList.add(generatePieChart());

			chartList.add(generateBarChart());
			chartList.add(generateBarChart());
			chartList.add(createStackedBarChart());
			chartList.add(generatePieChart());
			chartList.add(generateBarChart());
			chartList.add(createTimeSeries());
			chartList.add(generatePieChart());
			chartList.add(generatePieChart());
			chartList.add(generateBarChart());
//			chartList.add(generateBarChart());
//			chartList.add(generateBarChart2());
//
//
//			chartList.add(createStackedBarChart());

			int count = 0;
			boolean flag = false;
			System.out.println(dsLength);

			for (int i = 0; i < chartList.size(); i++) {

				// System.out.println(chartList.get(i).getTitle().getText().toLowerCase());
				int j = i + 1;
				if (j >= chartList.size()) {
					j = i;
				}
				Image iTextImageis = getImage(chartList.get(i));

				if (chartList.get(i).getTitle().getText().toLowerCase().equals("pie chart")
						|| chartList.get(i).getTitle().getText().toLowerCase().equals("bar chart")) { // System.out.println(chartList.get(i).getCategoryPlot().getDataset().getColumnCount());
					if (chartList.get(i).getTitle().getText().toLowerCase().equals("pie chart")
							&& chartList.get(j).getTitle().getText().toLowerCase().equals("pie chart")) {
//						System.out.println("pie pie");
						if (i == j && count == 0) {
							iTextImageis.scaleAbsolute(500, 400);
							iTextImageis.setFixedPosition(100f, 200f);
							doc.add(iTextImageis);
							doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
						} else {
							iTextImageis.scaleAbsolute(360, 250);
							if (count == 0) {
								iTextImageis.setFixedPosition(30f, 250f);
								count++;
								doc.add(iTextImageis);
							} else if (count == 1) {
								iTextImageis.setFixedPosition(370f, 250f);
								count++;
								doc.add(iTextImageis);
							}
							if (count == 2) {
								doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
								count = 0;
							}
						}
					} else if ((chartList.get(i).getTitle().getText().toLowerCase().equals("bar chart")
							&& chartList.get(i).getCategoryPlot().getDataset().getColumnCount() <= 7)
							&& (chartList.get(j).getTitle().getText().toLowerCase().equals("bar chart")
									&& chartList.get(j).getCategoryPlot().getDataset().getColumnCount() <= 7)) {
//						System.out.println("bar bar");
						if (i == j && count == 0) {
							iTextImageis.scaleAbsolute(500, 400);
							iTextImageis.setFixedPosition(100f, 200f);
							doc.add(iTextImageis);
							doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
						} else {
							iTextImageis.scaleAbsolute(360, 250);
							if (count == 0) {
								iTextImageis.setFixedPosition(30f, 250f);
								count++;
								doc.add(iTextImageis);
							} else if (count == 1) {
								iTextImageis.setFixedPosition(370f, 250f);
								count++;
								doc.add(iTextImageis);
							}
							if (count == 2) {
								doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
								count = 0;
							}
						}
					} else if (chartList.get(i).getTitle().getText().toLowerCase().equals("pie chart")
							|| (chartList.get(j).getTitle().getText().toLowerCase().equals("bar chart")
									&& chartList.get(j).getCategoryPlot().getDataset().getColumnCount() <= 7)) {
//						System.out.println("pie bar");
						if (chartList.get(i).getTitle().getText().toLowerCase().equals("pie chart")
								&& !chartList.get(j).getTitle().getText().toLowerCase().equals("bar chart")) {
							iTextImageis.scaleAbsolute(500, 400);
							iTextImageis.setFixedPosition(100f, 200f);
							doc.add(iTextImageis);
							doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
							count = 0;
						} else {
							iTextImageis.scaleAbsolute(360, 250);
							if (count == 0) {
								iTextImageis.setFixedPosition(30f, 250f);
								count++;
								doc.add(iTextImageis);
							} else if (count == 1) {
								iTextImageis.setFixedPosition(370f, 250f);
								count++;
								doc.add(iTextImageis);
							}
							if (count == 2) {
								doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
								count = 0;
							}
						}
					} else if ((chartList.get(i).getTitle().getText().toLowerCase().equals("bar chart")
							&& chartList.get(i).getCategoryPlot().getDataset().getColumnCount() <= 7)
							|| chartList.get(j).getTitle().getText().toLowerCase().equals("pie chart")) {
//						System.out.println("bar pie");
						if (chartList.get(i).getTitle().getText().toLowerCase().equals("bar chart")
								&& (!chartList.get(j).getTitle().getText().toLowerCase().equals("pie chart")
										|| chartList.get(j).getTitle().getText().toLowerCase().equals("bar chart"))
								&& count == 0) {
							iTextImageis.scaleAbsolute(500, 400);
							iTextImageis.setFixedPosition(100f, 200f);
							doc.add(iTextImageis);
							doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
							count = 0;
						} else if (chartList.get(i).getCategoryPlot().getDataset().getColumnCount() <= 7) {
							iTextImageis.scaleAbsolute(360, 250);
							if (count == 0) {
								iTextImageis.setFixedPosition(30f, 250f);
								count++;
								doc.add(iTextImageis);
							} else if (count == 1) {
								iTextImageis.setFixedPosition(370f, 250f);
								count++;
								doc.add(iTextImageis);
							}
							if (count == 2) {
								doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
								count = 0;
							}
						} else {
							iTextImageis.scaleAbsolute(500, 400);
							iTextImageis.setFixedPosition(100f, 200f);
							doc.add(iTextImageis);
							doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
							count = 0;
						}
					} else {
						iTextImageis.scaleAbsolute(500, 400);
						iTextImageis.setFixedPosition(100f, 200f);
						doc.add(iTextImageis);
						doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
						count = 0;
					}

				} else {
					iTextImageis.scaleAbsolute(500, 400);
					iTextImageis.setFixedPosition(100f, 200f);
					doc.add(iTextImageis);
					doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
				}
			}
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Image getImage(JFreeChart jFreeChart) throws IOException {
		// TODO Auto-generated method stub
		// JFreeChart chart = chartList.get(i);
		BufferedImage image1 = jFreeChart.createBufferedImage(500, 400);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image1, "png", baos);
		ImageData data = ImageDataFactory.create(baos.toByteArray());
		Image iTextImageis = new Image(data);
		return iTextImageis;
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
		chart.setTitle("Pie Chart");

		PiePlot plot = (PiePlot) chart.getPlot();

		plot.setLegendItemShape(new java.awt.Rectangle(50, 15));
		LegendTitle legend = chart.getLegend();
		legend.setFrame(BlockBorder.NONE);
		legend.setPosition(RectangleEdge.TOP);

		plot.setSimpleLabels(true);
		plot.setOutlinePaint(null);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"),
				new DecimalFormat("0%")));

		System.out.println("pie chart value : " + dataSet.getKey(0));
		// plot.setSectionPaint(dataSet.getKey(0), Color.DARK_GRAY);
		Random rand = new Random();
		for (int i = 0; i < dataSet.getItemCount(); i++) {
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			plot.setSectionPaint(dataSet.getKey(i), new Color(r, g, b));
			// renderer.setSeriesPaint(dataSet.getRowIndex(dataSet.getRowKey(i)), new
			// Color(r, g, b));
		}

		return chart;
	}

	@SuppressWarnings("unused")
	public static JFreeChart generateBarChart() throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(191, "1750 AD", "1750 AD");
		dataSet.setValue(978, "1800 AD", "1800 AD");
		dataSet.setValue(1262, "1850 AD", "1850 AD");
		dataSet.setValue(1650, "1900 AD", "1900 AD");
		dataSet.setValue(2519, "1950 AD", "1950 AD");
		dataSet.setValue(4070, "2000 AD", "2000 AD");
		dataSet.setValue(6070, "2050 AD", "2050 AD");
//		dataSet.setValue(2519, "2100 AD", "2100 AD");

//		dataSet.setValue(4070, "2150 AD", "2150 AD");
//		dataSet.setValue(6070, "2200 AD", "2200 AD");
//		dataSet.setValue(2519, "2250 AD", "2250 AD");
//		dataSet.setValue(4070, "2300 AD", "2300 AD");
//		dataSet.setValue(6070, "2350 AD", "2350 AD");
//		dataSet.setValue(2519, "2400 AD", "2400 AD");
//		dataSet.setValue(4070, "2450 AD", "2450 AD");
//		dataSet.setValue(6070, "2500 AD", "2500 AD");
//		dataSet.setValue(2519, "2550 AD", "2550 AD");
//		dataSet.setValue(4070, "2600 AD", "2600 AD");
//		dataSet.setValue(6070, "2650 AD", "2650 AD");

		dsLength = dataSet.getColumnCount();

		JFreeChart chart = ChartFactory.createBarChart("", "Year", "Population in millions", dataSet,
				PlotOrientation.VERTICAL, true, true, false);

		// System.out.println("plot type : ");
		chart.getPlot().setBackgroundPaint(Color.WHITE);
		chart.setTitle("Bar Chart");

		ChartFrame cf = new ChartFrame("Bar chart", chart, true);

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

		BarRenderer bar = (BarRenderer) plot.getRenderer();
		bar.setItemMargin(-5);
//		bar.setMaximumBarWidth(30.05);

		bar.setShadowVisible(false);
		bar.setDrawBarOutline(false);
		bar.setShadowVisible(false);

		java.util.List<Color> convertedColor = new java.util.ArrayList<>();
		java.util.List<String> colorCode = java.util.List.of("#77194E", "#95F23A", "#D93876", "#95CA99", "#bc6b24",
				"#4059FB", "#A21B28", "#5C0E93", "#F4C555", "#3FE025", "#95F23A", "#D93876", "#95CA99", "#bc6b24",
				"#4059FB", "#A21B28", "#5C0E93", "#F4C555", "#3FE025");
		// System.out.println(dataSet.getColumnIndex(dataSet.getColumnKey(1)));
		CategoryItemRenderer renderer = plot.getRenderer();
		if (colorCode != null && !colorCode.isEmpty()) {
			Paint[] colorPaint = new Paint[colorCode.size()];
			for (int color = 0; color < dataSet.getRowCount(); color++) {
//				convertedColor.add(hexToColor(colorCode.get(color)));
//				colorPaint[color] = convertedColor.get(color);
//				bar.setSeriesFillPaint(dataSet.getRowIndex(dataSet.getRowKey(color)), hexToColor(colorCode.get(color)));
				renderer.setSeriesPaint(dataSet.getRowIndex(dataSet.getRowKey(color)),
						hexToColor(colorCode.get(color)));
			}
//			renderer = new ChartAdjustmentLogic(colorPaint);
//			plot.setRenderer(renderer);
		} else {
			renderer = new ChartAdjustmentLogic(
					new Paint[] { Color.red, Color.blue, Color.green, Color.yellow, Color.pink, Color.cyan });
			plot.setRenderer(renderer);
		}

		/*
		 * final CategoryItemRenderer renderer = new ChartAdjustmentLogic( new Paint[] {
		 * Color.red, Color.blue, Color.green, Color.yellow, Color.pink, Color.cyan });
		 * 
		 * plot.setRenderer(renderer);
		 */

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
		bar.setBarPainter(new StandardBarPainter());
//		final BarRenderer barRenderer = (BarRenderer) plot.getRenderer();

//		barRenderer.setMaximumBarWidth(0.30);
//		// remove white line from shadow
//		barRenderer.setBarPainter(new StandardBarPainter());
//		// remove shadow from bar
//		barRenderer.setShadowVisible(false);

		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();

		ciRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		ciRenderer.setBaseItemLabelsVisible(true);
		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		ciRenderer.setBaseItemLabelFont(new Font("Arial", Font.PLAIN, 8));
		ciRenderer.setBasePositiveItemLabelPosition(position1);

		return chart;
	}

	private static Color hexToColor(String value) {
		// TODO Auto-generated method stub
		String digits;
		if (value.startsWith("#")) {
			digits = value.substring(1, Math.min(value.length(), 7));
		} else {
			digits = value;
		}
		String hstr = "0x" + digits;
		Color c;
		try {
			c = Color.decode(hstr);
		} catch (NumberFormatException nfe) {
			c = null;
		}

		return c;
	}

	public static JFreeChart generateBarChart2() throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(191, "Population", "1750 AD");
		dataSet.setValue(978, "Population1", "1800 AD");
		dataSet.setValue(1262, "Population2", "1850 AD");
		dataSet.setValue(1650, "Population3", "1900 AD");
		dataSet.setValue(2519, "Population4", "1950 AD");
		dataSet.setValue(4070, "Population5", "2000 AD");
		dataSet.setValue(6070, "Population6", "2050 AD");
		dataSet.setValue(2519, "Population7", "2100 AD");

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
				PlotOrientation.VERTICAL, true, true, false);

		chart.getPlot().setBackgroundPaint(Color.WHITE);
		chart.setTitle("Bar Chart");

		ChartFrame cf = new ChartFrame("Bar chart", chart, true);

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

		Random rand = new Random();
		CategoryItemRenderer renderer = plot.getRenderer();
		Paint[] colorPaint = new Paint[dataSet.getColumnCount()];
		for (int i = 0; i < dataSet.getColumnCount(); i++) {
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			colorPaint[i] = new Color(r, g, b);
		}
		renderer = new ChartAdjustmentLogic(colorPaint);
		plot.setRenderer(renderer);
//		final CategoryItemRenderer renderer = new ChartAdjustmentLogic(
//				new Paint[] { Color.red, Color.blue, Color.green, Color.yellow, Color.pink, Color.cyan });
//
//		plot.setRenderer(renderer);

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
		// barRenderer.setMaximumBarWidth(30.05);
		barRenderer.setItemMargin(-6);
		// barRenderer.setMaximumBarWidth(0.30);
		// remove white line from shadow
		barRenderer.setBarPainter(new StandardBarPainter());
		// remove shadow from bar
		barRenderer.setShadowVisible(false);

//		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();
//
//		ciRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//		ciRenderer.setBaseItemLabelsVisible(true);
//		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
//		ciRenderer.setBasePositiveItemLabelPosition(position1);

		CategoryItemRenderer ciRenderer = ((CategoryPlot) chart.getPlot()).getRenderer();
		ciRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		ciRenderer.setBaseItemLabelsVisible(true);
		String choise = "center";
		ItemLabelPosition position1 = null;
		switch (choise) {
		case "end":
			position1 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT);
			break;
		case "center":
			position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
			break;
		case "start":
			position1 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE9, TextAnchor.CENTER_LEFT);
			break;
		}
		ciRenderer.setBaseItemLabelFont(new Font("Arial", Font.PLAIN, 7));
		ciRenderer.setBasePositiveItemLabelPosition(position1);

		return chart;
	}

	private static JFreeChart createTimeSeries() throws IOException {

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
		String choise = "start";
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

		switch (choise) {
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

	private static TimeSeriesCollection createDataset() {

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

	private static JFreeChart createStackedBarChart() throws IOException {
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
		dataSet.addValue(15.4, "Product 1 (Asia)", "Jan 08");
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
		dataSet.addValue(23.9, "Product 1 (Asia)", "Jan 08");
		dataSet.addValue(19.0, "Product 3 (Asia)", "Feb 08");
		dataSet.addValue(10.1, "Product 3 (Asia)", "Mar 08");
		dataSet.addValue(18.1, "Product 3 (Asia)", "Apr 08");

		JFreeChart chart = ChartFactory.createStackedBarChart("Stacked Bar Chart", "Category", "Value", dataSet,
				PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(new Color(249, 231, 236));
		chart.setTitle("Stacked BarChart");
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

		java.util.List<String> colorCode = null;
//				java.util.List.of("#77194E", "#a0f3a4", "#E61480", "#FA530F", "#ff2a00",
//				"#6F7054", "#66ff00");
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		System.out.println(dataSet.getRowIndex("Product 1 (Europe)"));
		if (colorCode != null && !colorCode.isEmpty()) {
			for (int color = 0; color < colorCode.size(); color++) {
				renderer.setSeriesPaint(dataSet.getRowIndex(dataSet.getRowKey(color)),
						hexToColor(colorCode.get(color)));
			}
		} else {
			Random rand = new Random();
			for (int i = 0; i < dataSet.getRowCount(); i++) {
				float r = rand.nextFloat();
				float g = rand.nextFloat();
				float b = rand.nextFloat();
				renderer.setSeriesPaint(dataSet.getRowIndex(dataSet.getRowKey(i)), new Color(r, g, b));
			}
//			Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(16, 89, 172), 0.0f, 0.0f, new Color(201, 201, 244));
//			renderer.setSeriesPaint(0, p1);
//			renderer.setSeriesPaint(3, p1);
//			renderer.setSeriesPaint(6, p1);
//
//			Paint p2 = new GradientPaint(0.0f, 0.0f, new Color(10, 144, 40), 0.0f, 0.0f, new Color(160, 240, 180));
//			renderer.setSeriesPaint(1, p2);
//			renderer.setSeriesPaint(4, p2);
//			renderer.setSeriesPaint(7, p2);
//
//			Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(255, 35, 35), 0.0f, 0.0f, new Color(255, 180, 180));
//			renderer.setSeriesPaint(2, p3);
//			renderer.setSeriesPaint(5, p3);
//			renderer.setSeriesPaint(8, p3);
		}
//		renderer.setGradientPaintTransformer(
//				new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));

		SubCategoryAxis domainAxis = new SubCategoryAxis("Product / Month");
		domainAxis.setCategoryMargin(0.05);
		domainAxis.addSubCategory("Product 1");
		domainAxis.addSubCategory("Product 2");
		domainAxis.addSubCategory("Product 3");

		plot.setDomainAxis(domainAxis);
		plot.setRenderer(renderer);
		// plot.setFixedLegendItems(createLegendItems());

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
		// END
//		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER);
		// CENTER
		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		// start
//		ItemLabelPosition position1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE6,TextAnchor.BOTTOM_CENTER);
		ciRenderer.setBasePositiveItemLabelPosition(position1);

		return chart;

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