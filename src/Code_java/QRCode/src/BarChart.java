

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.category.CategoryDataset;

import org.jfree.ui.ApplicationFrame;

public class BarChart extends ApplicationFrame {

	    /**
	 * 
	 */
	private static final long serialVersionUID = -1629831730713495617L;

		/**
	     * Creates a new demo instance.
	     *
	     * @param title  the frame title.
	     */
	public BarChart(final String title, CategoryDataset dataset) {

	        super(title);

	        JFreeChart barChart = ChartFactory.createBarChart(
	                title,           
	                "couleur",            
	                "nb_pixel",            
	                dataset,          
	                PlotOrientation.VERTICAL,           
	                true, true, false);
	                
	             ChartPanel chartPanel = new ChartPanel( barChart );        
	             chartPanel.setPreferredSize(new java.awt.Dimension( 800 , 350 ) );        
	             setContentPane( chartPanel ); 
	         }
}