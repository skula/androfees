package com.skula.androfees;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.skula.androfees.models.Fee;

public class FeeAdapter extends BaseAdapter {
	public final Map<String, Adapter> sections = new LinkedHashMap<String, Adapter>();
	public final ArrayAdapter<String> headers;
	public final static int TYPE_SECTION_HEADER = 0;
	
	public FeeAdapter(Context context, Map<String, List<Fee>> map) {
		headers = new ArrayAdapter<String>(context,
				R.layout.feelistheaderlayout);
		for (String category : map.keySet()) {
			if (!map.get(category).isEmpty()) {
				List<Map<String, ?>> section = new LinkedList<Map<String, ?>>();
				for (Fee fee : map.get(category)) {
					section.add(createItem(fee));
				}
				addSection(category, new SimpleAdapter(context, section, R.layout.feelayout, new String[] { "id", "label",
						"date", "amount", "location" }, new int[] { R.id.id, R.id.label, R.id.date, R.id.amount, R.id.location }));
			}
		}
	}
	
	private Map<String, String> createItem(Fee fee) {
		Map<String, String> item = new HashMap<String, String>();
		item.put("id", fee.getId());
		item.put("label", fee.getLabel());
		item.put("date", fee.getDate());
		item.put("amount", fee.getAmount().replaceAll("\\.",",") + "€");
		item.put("location", fee.getLocation());
		return item;
	}

	private void addSection(String section, Adapter adapter) {
		this.headers.add(section);
		this.sections.put(section, adapter);
	}

	public Object getItem(int position) {
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			if (position == 0)
				return section;
			if (position < size)
				return adapter.getItem(position - 1);

			position -= size;
		}
		return null;
	}

	public int getCount() {
		int total = 0;
		for (Adapter adapter : this.sections.values())
			total += adapter.getCount() + 1;
		return total;
	}

	public int getViewTypeCount() {
		int total = 1;
		for (Adapter adapter : this.sections.values())
			total += adapter.getViewTypeCount();
		return total;
	}

	public int getItemViewType(int position) {
		int type = 1;
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// Récupération de la position dans la section
			if (position == 0)
				return TYPE_SECTION_HEADER;
			if (position < size)
				return type + adapter.getItemViewType(position - 1);

			// passe a la section suivante moins un par l'entête
			position -= size;
			type += adapter.getViewTypeCount();
		}
		return -1;
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isEnabled(int position) {
		return (getItemViewType(position) != TYPE_SECTION_HEADER);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		int sectionnum = 0;
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// Récupération de la position dans la section
			if (position == 0)
				return headers.getView(sectionnum, convertView, parent);
			if (position < size)
				return adapter.getView(position - 1, convertView, parent);

			// otherwise jump into next section
			position -= size;
			sectionnum++;
		}
		return null;
	}

	public long getItemId(int position) {
		return position;
	}
}
