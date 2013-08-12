package com.verago.screentime;

import java.util.ArrayList;

public class TimeValue extends ArrayList<TimeElement>{
	private static final long serialVersionUID = 8600315616807725720L;

	public TimeValue(){
		new ArrayList<TimeElement>();
		this.add(new TimeElement(30000, "30s", R.drawable.t30s));
		this.add(new TimeElement(60000, "1m", R.drawable.t1m));
		this.add(new TimeElement(600000, "10m", R.drawable.t10m));
		this.add(new TimeElement(-1, "inf", R.drawable.tinf));
		this.add(new TimeElement(15000, "15s", R.drawable.t15s));
	}
	

}
