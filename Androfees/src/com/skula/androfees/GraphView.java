package com.skula.androfees;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skula.androfees.models.GraphItem;
import com.skula.androfees.services.DatabaseService;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class GraphView extends View {
	private final static int xAXE_LENGTH = 1180;
	private final static int yAXE_LENGTH = 480;
	private final static int Y0 = 580;
	private final static int X0 = 50;

	private int typeGraph;
	private int countPeriod;
	private int timeUnit;
	private int typePeriod;
	
	private Paint paint;
	private DatabaseService dbService;
	
	public GraphView(Context context) {
		super(context);
		this.paint = new Paint();
		this.dbService = new DatabaseService(context);
	}
	
	@Override
	public void onDraw(Canvas canvas) {		
		/*Map<String, Double> map = new HashMap<String, Double>();
		map.put("Courses",47.5);
		map.put("Bar",15.85);
		map.put("Resto",108.20);
		map.put("Divers",200.0);
		map.put("ete",200.0);
		map.put("eteeee",200.0);
		map.put("etettt",200.0);
		drawGraphPrcCat(map, Color.RED, canvas);*/
		
		/*List<GraphItem> list = new ArrayList<GraphItem>();
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		list.add(new GraphItem(31,10,2012,1.0));
		list.add(new GraphItem(31,10,2012,2.0));
		list.add(new GraphItem(30,10,2012,5.0));
		list.add(new GraphItem(29,10,2012,8.0));
		//drawGraphCumTot(list, Color.RED, canvas);
		drawGraphVar(list, Color.RED, canvas);*/
		
		Map<String, List<GraphItem>> map = new HashMap<String,List<GraphItem>>();
		List<GraphItem> list1 = new ArrayList<GraphItem>();
		list1.add(new GraphItem(31,10,2012,1.0));
		list1.add(new GraphItem(31,10,2012,2.0));
		list1.add(new GraphItem(30,10,2012,5.0));
		list1.add(new GraphItem(29,10,2012,8.0));
		map.put("Course",list1);
		List<GraphItem> list2 = new ArrayList<GraphItem>();
		list2.add(new GraphItem(31,10,2012,10.0));
		list2.add(new GraphItem(31,10,2012,20.0));
		list2.add(new GraphItem(30,10,2012,50.0));
		list2.add(new GraphItem(29,10,2012,80.0));
		map.put("Bars",list2);
		drawGraphVarCat(map, Color.RED, canvas);
		
		/*Calendar cal = CalendarUtils.getStringFrom(countPeriod, typePeriod);
		String dateFrom = CalendarUtils.cal2String(cal);
		switch(typeGraph){
			case Definitions.GRAPH_PERC_CAT_CODE:
				Map<String, Double> map = dbService.graphPrcCat(dateFrom);
				drawGraphPrcCat(map, Color.RED, canvas);
				break;
			case Definitions.GRAPH_VAR_TOT_CODE:
				List<GraphItem> list = dbService.graphVarTot(dateFrom, timeUnit);
				drawGraphVar(list, Color.RED, canvas);
				break;
			case Definitions.GRAPH_VAR_CAT_CODE:
				Map<String, List<GraphItem>> map = dbService.graphVarCat(dateFrom, timeUnit);
				drawGraphVarCat(map, Color.RED, canvas);
				break;
			case Definitions.GRAPH_VAR_CUMU_CODE:
				List<GraphItem> list = dbService.graphVarTot(dateFrom, timeUnit);
				drawGraphCumTot(list, Color.RED, canvas);
				break;
			default:
				break;
		}*/
	}
	
	public void update(int typeGraph, int countPeriod, int typePeriod, int timeUnit){
		this.typeGraph= typeGraph;
		this.countPeriod = countPeriod;
		this.typePeriod = typePeriod;
		this.timeUnit = timeUnit;
	}

	// DESSIN GRAPH
	public void drawGraphPrcCat(Map<String, Double> map, int color, Canvas canvas) {
		this.drawXaxe(canvas);
		paint.setColor(color);
		double sum = 0.0;
		for(String cat : map.keySet()){
			sum+=map.get(cat);
		}
		
		int columnLenght = (xAXE_LENGTH-10*(map.size()-1))/map.size();
		
		int pos = X0;
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		for(String cat : map.keySet()){
			// dessiner les colonnes
			paint.setColor(color);
			int y = (int) ((map.get(cat) * yAXE_LENGTH) / sum);
			Rect xAxe = new Rect(pos , Y0, pos + columnLenght, Y0 -y);
			
			canvas.drawRect(xAxe, paint);		
			// ecrire les %
			paint.setColor(Color.BLACK);
			paint.setTextSize(22f);
			String percent = numberFormat.format((100*map.get(cat))/sum);
			canvas.drawText(percent+"%", pos + columnLenght/3, Y0 -y-40,paint);
			canvas.drawText("("+numberFormat.format(map.get(cat))+"€)", pos +columnLenght/3, Y0 -y-10,paint);
			// ecrire les libellés des catégories
			canvas.drawText(cat, pos + columnLenght/3, Y0 +30,paint);
			
			pos+=columnLenght+10;
		}
	}

	// DESSIN GRAPH
	public void drawGraphCumTot(List<GraphItem> list, int color, Canvas canvas) {
		this.drawAxes(canvas);
		paint.setColor(color);
		int stepLength = xAXE_LENGTH / (list.size()-1);
		double sum = 0.0;
		for (GraphItem g : list) {
			sum += g.getValue();
		}
		double val = 0.0;

		List<Point> points = new ArrayList<Point>();
		for (int i = 0; i < list.size(); i++) {
			val += list.get(i).getValue();
			int y = (int) ((val * yAXE_LENGTH) / sum);
			points.add(new Point(X0 + stepLength * i, Y0 - y));
		}

		// dissiner la courbe
		drawCurve(points, canvas);
		
		// dessiner les pas
		paint.setColor(Color.RED);
		for(int j = 0; j<=list.size(); j++){
			canvas.drawRect(new Rect(j*stepLength+X0,Y0-5,j*stepLength+X0+2,Y0+5), paint);
		}		
		
		// dessiner les valeurs de l'axe des ordonnées
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		paint.setColor(Color.BLACK);
		paint.setTextSize(20f);
		int pos = Y0;
		double r = 0.0;
		for(int i=0; i<5; i++){
			if(r!=0.0){
				paint.setColor(Color.BLACK);
				canvas.drawText(numberFormat.format(r), X0+10, pos+5, paint);
			}
			paint.setColor(Color.RED);
			canvas.drawRect(new Rect(X0-5,pos,X0+5,pos-2), paint);
			pos-=yAXE_LENGTH/4;
			r+=sum/4;
		}
	}

	// DESSIN GRAPH
	public void drawGraphVar(List<GraphItem> list, int color, Canvas canvas) {
		this.drawAxes(canvas);
		paint.setColor(color);
		
		double max = 0.0;
		for (GraphItem g : list) {
			if (g.getValue() > max) {
				max = g.getValue();
			}
		}
		int stepLength = xAXE_LENGTH / (list.size()-1);

		List<Point> points = new ArrayList<Point>();
		for (int i = 0; i < list.size(); i++) {
			double val = list.get(i).getValue();
			int y = (int) ((val * yAXE_LENGTH) / max);
			points.add(new Point(X0 + stepLength * i, Y0 - y));
		}
		
		// dessiner la courbe
		drawCurve(points, canvas);
		
		// dessiner les pas
		paint.setColor(Color.RED);
		for(int j = 0; j<=list.size(); j++){
			canvas.drawRect(new Rect(j*stepLength+X0,Y0-5,j*stepLength+X0+2,Y0+5), paint);
		}
		
		// dessiner les valeurs de l'axe des ordonnées
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		paint.setColor(Color.BLACK);
		paint.setTextSize(20f);
		int pos = Y0;
		double r = 0.0;
		for(int i=0; i<5; i++){
			if(r!=0.0){
				paint.setColor(Color.BLACK);
				canvas.drawText(numberFormat.format(r), X0+10, pos+5, paint);
			}
			paint.setColor(Color.RED);
			canvas.drawRect(new Rect(X0-5,pos,X0+5,pos-2), paint);
			pos-=yAXE_LENGTH/4;
			r+=max/4;
		}
	}

	// DESSIN GRAPH
	public void drawGraphVarCat(Map<String, List<GraphItem>> map, int color, Canvas canvas) {
		this.drawAxes(canvas);
		paint.setColor(color);
		double max = 0.0;
		int cpt=0;
		for(String cat : map.keySet()){
			for(GraphItem g : map.get(cat)){
				cpt++;
			}
			break;
		}
		
		for(String cat : map.keySet()){
			for(GraphItem g : map.get(cat)){
				if(g.getValue()>max){
					max=g.getValue();
				}
				
			}
		}
		int stepLength = xAXE_LENGTH / (cpt-1);
		for(String cat : map.keySet()){
			List<GraphItem> l = map.get(cat);
			List<Point> points = new ArrayList<Point>();
			for (int i = 0; i < l.size(); i++) {
				double val = l.get(i).getValue();
				int y = (int) ((val * yAXE_LENGTH) / max);
				points.add(new Point(X0 + stepLength * i, Y0 - y));
			}
			
			drawCurve(points, canvas);
		}
		
		// dessiner les pas
		paint.setColor(Color.RED);
		for(int j = 0; j<=cpt; j++){
			canvas.drawRect(new Rect(j*stepLength+X0,Y0-5,j*stepLength+X0+2,Y0+5), paint);
		}
		
		// dessiner les valeurs de l'axe des ordonnées
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(2);
		paint.setColor(Color.BLACK);
		paint.setTextSize(20f);
		int pos = Y0;
		double r = 0.0;
		for(int i=0; i<5; i++){
			if(r!=0.0){
				paint.setColor(Color.BLACK);
				canvas.drawText(numberFormat.format(r), X0+10, pos+5, paint);
			}
			paint.setColor(Color.RED);
			canvas.drawRect(new Rect(X0-5,pos,X0+5,pos-2), paint);
			pos-=yAXE_LENGTH/4;
			r+=max/4;
		}
	}

	private void drawCurve(List<Point> points, Canvas canvas) {
		Point p1 = null;
		for (Point p2 : points) {
			if (p1 != null) {
				paint.setColor(Color.RED);
				canvas.drawLine(p1.x, p1.y + 1, p2.x, p2.y + 1, this.paint);
				canvas.drawLine(p1.x, p1.y, p2.x, p2.y, this.paint);
				canvas.drawLine(p1.x, p1.y - 1, p2.x, p2.y - 1, this.paint);
				paint.setColor(Color.parseColor("#6a6a6a"));
				canvas.drawCircle(p1.x, p1.y, 4.0f, paint);
			}
			p1 = p2;
		}
		paint.setColor(Color.parseColor("#6a6a6a"));
		canvas.drawCircle(p1.x, p1.y, 4.0f, paint);
	}

	private void drawXaxe(Canvas canvas) {
		paint.setColor(Color.parseColor("#6a6a6a"));
		canvas.drawRect(new Rect(X0, Y0, xAXE_LENGTH + X0, Y0 + 2), paint);
	}
	
	private void drawAxes(Canvas canvas) {
		paint.setColor(Color.parseColor("#6a6a6a"));
		canvas.drawRect(new Rect(X0, Y0, xAXE_LENGTH + X0, Y0 + 2), paint);
		canvas.drawRect(new Rect(X0, Y0, X0 - 2, Y0 - yAXE_LENGTH), paint);
	}
}
