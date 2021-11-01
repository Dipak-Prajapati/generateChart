package com.dips;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
//import java.awt.Image;
import java.awt.Paint;
//import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

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
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;


public class PieChartDemo7 extends BarRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Paint[] colors;

	public PieChartDemo7(final Paint[] colors) {
		this.colors = colors;
	}

	public Paint getItemPaint(final int row, final int column) {
		return this.colors[column % this.colors.length];
	}

	public static void main(String[] args) throws IOException {
		writeChartToPDF( 50, 40, "barchart.pdf");
		// writeChartToPDF(generatePieChart(), 500, 400, "piechart.pdf");
	}

	public static void writeChartToPDF(int width, int height, String fileName) throws FileNotFoundException {
		PdfWriter writer = new PdfWriter(fileName);
	
		PdfDocument pdfDoc = new PdfDocument(writer);
	

		try {
			 writer = new PdfWriter(fileName);
	
			Rectangle one = new Rectangle(750, 750);
			PageSize size = new PageSize(one);
			Document doc = new Document(pdfDoc, size);
			doc.setMargins(2,2,2,2);

			PdfPage pdfPage1 = pdfDoc.addNewPage();
			PdfCanvas canvas = new PdfCanvas(pdfPage1); 
			
			com.itextpdf.kernel.color.Color color = com.itextpdf.kernel.color.Color.BLACK;
			canvas.setColor(color, true); 
			canvas.rectangle(0, 0, 300, 750);
			canvas.setExtGState(new PdfExtGState().setFillOpacity(0.79f));
			canvas.fill();   		
			
			float x = 0;
			float y = 0;
			float side = 1200;
			PdfShading.Axial axial = new PdfShading.Axial(new PdfDeviceCs.Rgb(), x, y, com.itextpdf.kernel.color.Color.BLACK.getColorValue(),
			        x + side, y, com.itextpdf.kernel.color.Color.WHITE.getColorValue());
			PdfPattern.Shading shading = new PdfPattern.Shading(axial);
			canvas.setFillColorShading(shading);
			canvas.moveTo(0, 0);
			canvas.lineTo(x + (side/6), 0);
			canvas.lineTo(x , (float) (y + (side * Math.sin(Math.PI / 3))));
			com.itextpdf.kernel.color.Color color1 = com.itextpdf.kernel.color.Color.DARK_GRAY;
			canvas.setStrokeColor(color1);
			canvas.closePathFillStroke();
			
		
			
			Style normal = new Style();
			PdfFont font = PdfFontFactory.createFont(); 
			normal.setFont(font).setFontSize(30);
			Paragraph para = new Paragraph(new Text("Monthly Analysis Report").addStyle(normal).setFontColor(com.itextpdf.kernel.color.Color.BLACK)); 
			para.setFixedPosition(340, 400, 400);
			doc.add(para);
			
			
		
			PdfPage pdfPage2 = pdfDoc.addNewPage();
			PdfCanvas canvas1 = getCanvas(pdfPage2);
			doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			
			 List list = new List();  
		      
		      // Add elements to the list       
		      list.add("Country : India");       
		      list.add("Asset : Delhi, Punjab, Haryana");      
		      list.add("Status : In Progress, Approved");       
		      list.setSymbolIndent(12);
	          list.setListSymbol("-");
		      list.setFixedPosition(340, 400, 400);
		      list.setBold();
		      list.setFontSize(20);
	       //   list.setFont(font);
		      doc.add(list);
		      
		      Rectangle rect = new Rectangle(0,0); 
		     // rect.set
		      PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);
		      annotation.setBorder(new PdfArray());
		      PdfAction action = PdfAction.createURI("https://beta.mdoondemand.com/MDOSF/fuze/ngx-mdo/en/index.html#/home/report/dashboard/761650948893186853"); 
		      annotation.setAction(action);             
		      
		      // Creating a link       
		      Link link = new Link("Page link", annotation); 
		      link.setFontSize(30);
		      link.setFontColor(com.itextpdf.kernel.color.Color.BLUE);

		      Paragraph parag = new Paragraph(new Text("Here is the ").addStyle(normal).setFontColor(com.itextpdf.kernel.color.Color.BLACK));
		      parag.add(link.setUnderline()); 
		      parag.setFontSize(20);
		      parag.setFixedPosition(340,280,400);
		      parag.add(new Text(" to check out the live report.").addStyle(normal).setFontColor(com.itextpdf.kernel.color.Color.BLACK));
		      doc.add(parag);
		      
		      
		      Paragraph parags = new Paragraph(new Text("Filters applied on").addStyle(normal).setFontColor(com.itextpdf.kernel.color.Color.WHITE));
		      parags.setFixedPosition(50,400,400);
		      doc.add(parags);
		      
			PdfPage pdfPage3 = pdfDoc.addNewPage();
			doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));

		
			
			Image barchart = generateBarChart();
			barchart.scaleAbsolute(360, 250);
			barchart.setFixedPosition(370f, 250f);	
			doc.add(barchart);
			
//			// Pie chart
			Image piechart = generatePieChart();
			piechart.scaleAbsolute(360, 250);
			piechart.setFixedPosition(30f, 250f);
			doc.add(piechart);

			doc.add(new AreaBreak(AreaBreakType.LAST_PAGE));
//			
			// TimeSeries

			Image iTextImageis = createTimeSeries();
			iTextImageis.scaleAbsolute(500, 400);
			iTextImageis.setFixedPosition(100f, 200f);
			
			doc.add(iTextImageis);
			
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
		PdfShading.Axial axial = new PdfShading.Axial(new PdfDeviceCs.Rgb(), x, y, com.itextpdf.kernel.color.Color.BLACK.getColorValue(),
		        x + side, y, com.itextpdf.kernel.color.Color.WHITE.getColorValue());
		PdfPattern.Shading shading = new PdfPattern.Shading(axial);
		canvas.setFillColorShading(shading);
		canvas.moveTo(0, 0);
		canvas.lineTo(x + (side/6), 0);
		canvas.lineTo(x , (float) (y + (side * Math.sin(Math.PI / 3))));
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

		System.out.println(dataSet.getItemCount());
		System.out.println(dataSet.getValue(0));
		System.out.println(dataSet.getKey(0));
		
		JFreeChart chart = ChartFactory.createPieChart("", dataSet, true, false, false);

		System.out.println("Pie chart DataSet : "+((PiePlot) chart.getPlot()).getDataset().getKeys());
		
		chart.getPlot().setBackgroundPaint(Color.WHITE);
		PiePlot plot = (PiePlot) chart.getPlot();
		
		plot.setLegendItemShape(new java.awt.Rectangle(50, 15));
		LegendTitle legend = chart.getLegend();
//		legend.setFrame(BlockBorder.NONE);
		legend.setPosition(RectangleEdge.TOP);

		plot.setSimpleLabels(true);
		plot.setOutlinePaint(null);
	
		BufferedImage images = chart.createBufferedImage(500, 400);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(images, "png", baos);
		 ImageData data = ImageDataFactory.create(baos.toByteArray());  
		 Image image = new Image(data);
		return image;
	}

	public static Image generateBarChart() throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(791, "Population", "1750 AD");
		dataSet.setValue(978, "Population", "1800 AD");
		dataSet.setValue(1262, "Population", "1850 AD");
		dataSet.setValue(1650, "Population", "1900 AD");
		dataSet.setValue(2519, "Population", "1950 AD");
		dataSet.setValue(6070, "Population", "2000 AD");

		
		JFreeChart chart = ChartFactory.createBarChart("", "Year", "Population in millions", dataSet,
				PlotOrientation.VERTICAL, true, true, false);
		System.out.println("::::::::::$$$::::"+chart.getCategoryPlot().getDataset().getColumnKeys());
		// chart.getCategoryPlot().getRangeAxis().setLowerBound(20.0);
		chart.getPlot().setBackgroundPaint(Color.WHITE);
		CategoryPlot plot = chart.getCategoryPlot();
		Stroke solid = new BasicStroke(0);
		plot.setRangeGridlineStroke(solid);
		plot.setDomainGridlineStroke(solid);
		plot.setRangeGridlinesVisible(true);
		
		ChartPanel chartpanel = new ChartPanel(chart);
		 chartpanel.setDomainZoomable(true);
//		 JPanel jPanel4 = new JPanel();
//	        jPanel4.setLayout(new BorderLayout());
//	        jPanel4.add(chartpanel, BorderLayout.NORTH);
//
//	        JFrame frame = new JFrame();
//	        frame.add(jPanel4);
//	        frame.pack();
//	        frame.setVisible(true);
		    plot.setRangePannable(true);
		
		plot.getDomainAxis().setCategoryLabelPositions(
			    CategoryLabelPositions.UP_45);
		
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.gray);
		
		plot.setRangeGridlinePaint(Color.gray);
		plot.setOutlinePaint(Color.gray);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		
	
		 
		final CategoryItemRenderer renderer = new PieChartDemo7(
				new Paint[] { Color.red, Color.blue, Color.green, Color.yellow, Color.pink, Color.cyan });

		plot.setRenderer(renderer);

		final BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
		barRenderer.setMaximumBarWidth(0.30);
		//remove white line from shadow
		barRenderer.setBarPainter(new StandardBarPainter());
		//remove shadow from bar
		barRenderer.setShadowVisible(false);
		
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

		// Shape shape = ShapeUtilities.c
		// Shape cross = ShapeUtilities.createTranslatedShape(shape, transX, transY);
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

}

//	Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
//	Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);

//	chart.draw(graphics2d, rectangle2d);

//	ChartPanel chartPanel = new ChartPanel(chart, false);
//	chartPanel.setBackground(Color.RED);

//		graphics2d.dispose();
//contentByte.addTemplate(template, 0, 0);
//	PdfTemplate template = contentByte.createTemplate(width, height);
//PdfContentByte contentByte = writer.getDirectContent();