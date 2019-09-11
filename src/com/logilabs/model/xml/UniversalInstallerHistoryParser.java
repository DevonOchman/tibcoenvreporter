package com.logilabs.model.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UniversalInstallerHistoryParser {

	public static String getInstalledProductList(UniversalInstallerHistory history) {
		String result = "";
		List<lProduct> list = getProductsAlreadyInstalled(history);
		list = removeDuplicates(list);
		Collections.sort(list, new Comparator<lProduct>() {

			@Override
			public int compare(lProduct o1, lProduct o2) {
				if (o1.getName().equals(o2.getName()))
					return o1.getVersion().compareTo(o2.getVersion());
				else {
					return o1.getName().compareTo(o2.getName());
				}
			}

		});

		for (lProduct p : list) {
			result += "Name: " + p.getName() + ",\t\tversion: " + p.getVersion() + ",\t\tinstall date: " + p.getDate();
			result += "\n";
		}
		return result;
	}

	public static List<lProduct> getProductsAlreadyInstalled(UniversalInstallerHistory history) {
		ArrayList<lProduct> products = new ArrayList<lProduct>();
		for (Install i : history.install) {
			String installDate = i.timestamp;
			ProductsAlreadyInstalled pai = i.productsAlreadyInstalled;
			if (pai != null) {
				for (Product p : i.productsAlreadyInstalled.product) {
					products.add(new lProduct(p.getName(), p.getVersion(), installDate));
				}
			}
			ProductsInitialized pi = i.productsInitialized;
			if (pi != null) {
				for (Product p : i.productsInitialized.product) {
					products.add(new lProduct(p.getName(), p.getVersion(), installDate));
				}
			}
			ProductsSelected ps = i.productsSelected;
			if (ps != null) {
				for (Product p : i.productsSelected.product) {
					products.add(new lProduct(p.getName(), p.getVersion(), installDate));
				}
			}
		}
		return products;
	}

	private static List<lProduct> removeDuplicates(List<lProduct> list) {
		List<lProduct> newList = new ArrayList<lProduct>();
		for (lProduct p : list) {
			if(!containsProduct(newList, p)) {
				newList.add(p);
			}
		}
		return newList;
	}

	private static boolean containsProduct(List<lProduct> list, lProduct product) {
		for (lProduct p : list) {
			if (p.getName().equals(product.getName()) && p.getVersion().equals(product.getVersion()) && p.getDate().equals(product.getDate()))
				return true;
		}
		return false;
	}
}

class lProduct{
	public lProduct(String name, String version, String date) {
		super();
		this.name = name;
		this.version = version;
		this.date = date;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String version;
	private String date;
	
}