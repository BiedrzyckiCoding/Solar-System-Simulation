package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class EarthMoonSimulationGui extends JFrame {
    public EarthMoonSimulationGui(List<Double> earthX,
                                  List<Double> earthY,
                                  List<Double> moonX,
                                  List<Double> moonY,
                                  List<Double> moonRelX,
                                  List<Double> moonRelY,
                                  List<Double> time) {
        setTitle("Earth & Moon Simulation");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(1, 2));

        XYSeriesCollection absDataset = new XYSeriesCollection();

        XYSeries earthSeries = new XYSeries("Earth (abs)");
        for (int i = 0; i < earthX.size(); i++) {
            earthSeries.add(earthX.get(i), earthY.get(i));
        }

        XYSeries moonSeries = new XYSeries("Moon (abs)");
        for (int i = 0; i < moonX.size(); i++) {
            moonSeries.add(moonX.get(i), moonY.get(i));
        }

        absDataset.addSeries(earthSeries);
        absDataset.addSeries(moonSeries);

        JFreeChart absChart = ChartFactory.createScatterPlot(
                "Earth & Moon (Absolute Positions)",
                "X [scaled]",
                "Y [scaled]",
                absDataset,
                PlotOrientation.VERTICAL,
                true, // legend
                true, // tooltips
                false // urls
        );

        XYPlot absPlot = absChart.getXYPlot();
        XYLineAndShapeRenderer absRenderer = new XYLineAndShapeRenderer(false, true);
        Shape bigCircle = new Ellipse2D.Double(-3, -3, 6, 6);
        absRenderer.setSeriesShape(0, bigCircle);
        Shape smallCircle = new Ellipse2D.Double(-2, -2, 4, 4);
        absRenderer.setSeriesShape(1, smallCircle);
        absPlot.setRenderer(absRenderer);

        ChartPanel absChartPanel = new ChartPanel(absChart);

        XYSeriesCollection relDataset = new XYSeriesCollection();

        XYSeries moonRelSeries = new XYSeries("Moon (relative)");
        for (int i = 0; i < moonRelX.size(); i++) {
            moonRelSeries.add(moonRelX.get(i), moonRelY.get(i));
        }

        relDataset.addSeries(moonRelSeries);

        JFreeChart relChart = ChartFactory.createScatterPlot(
                "Moon Relative Position (xM,yM)",
                "xM [scaled]",
                "yM [scaled]",
                relDataset,
                PlotOrientation.VERTICAL,
                true, // legend
                true, // tooltips
                false // urls
        );

        XYPlot relPlot = relChart.getXYPlot();
        XYLineAndShapeRenderer relRenderer = new XYLineAndShapeRenderer(false, true);
        Shape relMoonCircle = new Ellipse2D.Double(-2, -2, 4, 4);
        relRenderer.setSeriesShape(0, relMoonCircle);
        relPlot.setRenderer(relRenderer);

        ChartPanel relChartPanel = new ChartPanel(relChart);
        add(absChartPanel);
        add(relChartPanel);
    }
}
