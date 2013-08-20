package com.skula.androfees.definitions;

import java.util.Calendar;

public class Definitions {
	public final static int MODE_MOD = 1;
	public final static int MODE_CRE = 2;
	
	public final static String TIME_UNIT_DAY_LABEL = "Jour";
	public final static String TIME_UNIT_MONTH_LABEL = "Mois";
	public final static String TIME_UNIT_YEAR_LABEL = "Année";
	
	public final static String GRAPH_PERC_CAT_LABEL = "Pourcentage/catégorie";
	public final static String GRAPH_VAR_TOT_LABEL = "Variation total";
	public final static String GRAPH_VAR_CAT_LABEL = "Variation/catégorie";
	public final static String GRAPH_VAR_CUMU_LABEL = "Variation cummulée total";
	
	public final static int GRAPH_PERC_CAT_CODE = 1;
	public final static int GRAPH_VAR_TOT_CODE = 2;
	public final static int GRAPH_VAR_CAT_CODE = 3;
	public final static int GRAPH_VAR_CUMU_CODE = 4;
	
	public static int getCode(String label){
		if(label.equals(TIME_UNIT_DAY_LABEL)){
			return Calendar.DAY_OF_MONTH;
		}else if(label.equals(TIME_UNIT_MONTH_LABEL)){
			return Calendar.MONTH;
		}else if(label.equals(TIME_UNIT_YEAR_LABEL)){
			return Calendar.YEAR;
		}else if(label.equals(GRAPH_PERC_CAT_LABEL)){
			return GRAPH_PERC_CAT_CODE;
		}else if(label.equals(GRAPH_VAR_TOT_LABEL)){
			return GRAPH_VAR_TOT_CODE;
		}else if(label.equals(GRAPH_VAR_CAT_LABEL)){
			return GRAPH_VAR_CAT_CODE;
		}else if(label.equals(GRAPH_VAR_CUMU_LABEL)){
			return GRAPH_VAR_CUMU_CODE;
		}else{
			return -1;
		}
	}
}
