package com.logilabs.model.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UniversalInstallerHistoryParser {

	public static String getInstalledProductList(UniversalInstallerHistory history) {
		String result = "";
		List<Product> list = getProductsAlreadyInstalled(history);
//		list = removeDuplicates(list);
		Collections.sort(list, new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				if (o1.name.equals(o2.name))
					return o1.version.compareTo(o2.version);
				else {
					return o1.name.compareTo(o2.name);
				}
			}

		});

		for (Product p : list) {
			result += "Name: " + p.name + ",\tversion: " + p.version;
			result += "\n";
		}
		return result;
	}

	public static List<Product> getProductsAlreadyInstalled(UniversalInstallerHistory history) {
		ArrayList<Product> products = new ArrayList<Product>();
		for (Install i : history.install) {
			ProductsAlreadyInstalled pai = i.productsAlreadyInstalled;
			if (pai != null) {
				for (Product p : i.productsAlreadyInstalled.product) {
					products.add(p);
				}
			}
			ProductsInitialized pi = i.productsInitialized;
			if (pi != null) {
				for (Product p : i.productsInitialized.product) {
					products.add(p);
				}
			}
			ProductsSelected ps = i.productsSelected;
			if (ps != null) {
				for (Product p : i.productsSelected.product) {
					products.add(p);
				}
			}
		}
		return products;
	}

	private static List<Product> removeDuplicates(List<Product> list) {
		List<Product> newList = new ArrayList<Product>();
		for (Product p : list) {
			if(!containsProduct(newList, p)) {
				newList.add(p);
			}
		}
		return newList;
	}

	private static boolean containsProduct(List<Product> list, Product product) {
		for (Product p : list) {
			if (p.getName().equals(product.getName()) && p.getVersion().equals(product.getVersion()))
				return true;
		}
		return false;
	}
}