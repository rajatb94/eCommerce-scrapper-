/* This class is to crawl data fro the predefined websites and to store that data to temporary folder */

package com.mycompany.hdp;

/**
 *
 * @author rajat
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author rajat
 */
public class getDetails {

	private static String qr;


	//############################################################################ COSCOTO ipad
	public void get_costcoipd(String query, String date) throws IOException {
		qr = "ipad";

		Document doc = Jsoup.connect("http://www.costco.com/" + qr + ".html").userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Elements d = doc.getElementsByAttributeValue("class", "rfp-container").get(0).getElementsByAttributeValue("class", "rfp-cat-feature");
		int s = d.size() - 1;
		if (s > 100) s = 100;
		for (int i = 1; i < s; i++) {
			String ur = d.get(i).getElementsByTag("a").get(0).attr("href");
			get_all_costcospc("http://www.costco.com/" + ur, date);

		}
	}
	//############################################################################ END - COSCOTO ipad

	//############################################################################ COSCOTO iphone
	public void get_costcoipn(String query, String date) throws IOException {
		qr = "iphone";

		get_all_costcoipn("http://membershipwireless.com/index.cfm/go/search/do/search/?q=iphone", date);


	}
	public void get_costcospn(String query, String date) throws IOException {
		qr = "samsung phone";

		get_all_costcoipn("http://membershipwireless.com/index.cfm/go/search/do/search/?q=samsung+phone", date);


	}
	public void get_costcostb(String query, String date) throws IOException {
		qr = "samsung tab";

		get_all_costcospc("http://www.costco.com/CatalogSearch?storeId=10301&catalogId=10701&langId=-1&refine=&keyword=samsung+tab", date);


	}
	public static void get_all_costcoipn(String url, String date) throws IOException {

		Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Elements d = doc.getElementsByAttributeValue("id", "results_phone").get(0).getElementsByAttributeValue("class", "prodItem");
		for (int i = 0; i < d.size(); i++) {
			int nameS = d.get(i).getElementsByAttributeValue("class", "prodTitle").size();
			int priceS = d.get(i).getElementsByAttributeValue("class", "prodPrice").size();

			if (nameS == 1 && priceS == 1) {
				String name = d.get(i).getElementsByAttributeValue("class", "prodTitle").get(0).text();
				String price = d.get(i).getElementsByAttributeValue("class", "prodPrice").get(0).text();

				try {
					//File file = new File("data/" + date + "/" + qr + "/costco_" + qr + "__" + date + ".csv");
					File file = new File("data/costco/" + qr + "/" + date + ".csv");
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
					file.getParentFile().mkdirs();
					FileWriter fw = new FileWriter(file, true);
					fw.write("\"" + name + "\",\"" + price + "\"\n");

					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		}
	}
	//############################################################################ END - COSCOTO iphone

	//############################################################################ COSCOTO
	public void get_costco(String query, String date) throws IOException {
		qr = query;
		Document doc = Jsoup.connect("http://www.costco.com/CatalogSearch?storeId=10301&catalogId=10701&langId=-1&refine=&keyword=" + query).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Elements d = doc.getElementsByAttributeValue("class", "pagination").get(0).getElementsByTag("li");
		int s = Integer.parseInt(d.get(d.size() - 1).text());
		if (s > 100) s = 100;
		for (int i = 1; i <= s; i++) {

			get_all_costco("http://www.costco.com/CatalogSearch?pageSize=96&catalogId=10701&currentPage=" + i + "&langId=-1&keyword=" + query + "&storeId=10301", date);

		}
	}
	public static void get_all_costco(String url, String date) throws IOException {

		Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Elements d = doc.getElementsByAttributeValue("class", "product-tile comparable");

		for (int i = 0; i < d.size(); i++) {
			int nameS = d.get(i).getElementsByAttributeValue("class", "short-desc").size();
			int priceS = d.get(i).getElementsByAttributeValue("class", "currency ").size();
			if (nameS == 1 && priceS == 1) {
				String name = d.get(i).getElementsByAttributeValue("class", "short-desc").get(0).text();
				String price = d.get(i).getElementsByAttributeValue("class", "currency").get(0).text();
				int ratingC = d.get(i).getElementsByAttributeValue("class", "product-rating").size();
				String rating;
				if (ratingC > 0) {
					rating = d.get(i).getElementsByAttributeValue("class", "product-rating").get(0).text();
				} else {
					rating = "Not Rated";
				}
				try {
					// file = new File("data/" + date + "/" + qr + "/costco_" + qr + "__" + date + ".csv");
					File file = new File("data/costco/" + qr + "/" + date + ".csv");
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
					file.getParentFile().mkdirs();
					FileWriter fw = new FileWriter(file, true);
					fw.write("\"" + name + "\",\"" + price + "\",\"" + rating + "\"\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		}
	}

	public static void get_all_costcospc(String url, String date) throws IOException {

		Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Elements d = doc.getElementsByAttributeValue("class", "product-tile comparable");

		for (int i = 0; i < d.size(); i++) {
			int nameS = d.get(i).getElementsByAttributeValue("class", "short-desc").size();
			if (nameS == 1) {
				String name = d.get(i).getElementsByAttributeValue("class", "short-desc").get(0).text();
				int ratingC = d.get(i).getElementsByAttributeValue("class", "product-rating").size();
				String rating;
				if (ratingC > 0) {
					rating = d.get(i).getElementsByAttributeValue("class", "product-rating").get(0).text();
				} else {
					rating = "Not Rated";
				}
				try {
					//File file = new File("data/" + date + "/" + qr + "/costco_" + qr + "__" + date + ".csv");
					File file = new File("data/costco/" + qr + "/" + date + ".csv");
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
					file.getParentFile().mkdirs();
					FileWriter fw = new FileWriter(file, true);
					fw.write("\"" + name + "\",\"" + rating + "\"\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		}
	}
	//############################################################################ END - COSCOTO


	//############################################################################ BESTBUY
	public void get_bestbuy(String query, String date) throws IOException {
		qr = query;
		Document doc = Jsoup.connect("http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=25&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=" + query).timeout(10 * 2000).get();
		Elements d = doc.getElementsByAttributeValue("class", "pagination-section").get(0).getElementsByTag("li");
		int s = Integer.parseInt(d.get(d.size() - 2).text());
		if (s > 100) s = 100;
		for (int i = 1; i <= s; i++) {

			get_all_bestbuy("http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=" + i + "&nrp=25&sp=&qp=&list=n&iht=y&usc=All%20Categories&ks=960&fs=saas&saas=saas&keys=keys&st=" + query, date);

		}
	}
	public static void get_all_bestbuy(String url, String date) throws IOException {

		Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Elements d = doc.getElementsByAttributeValue("class", "list-item");
		for (int i = 0; i < d.size(); i++) {
			int nameS = d.get(i).getElementsByAttributeValue("class", "sku-title").size();
			int priceS = d.get(i).getElementsByAttributeValue("class", "medium-item-price").size();
			int modelS = d.get(i).getElementsByAttributeValue("itemprop", "model").size();
			int specsS = d.get(i).getElementsByAttributeValue("class", "short-description").size();
			int offersS = d.get(i).getElementsByAttributeValue("class", "special-offers").size();
			int ratingS = d.get(i).getElementsByAttributeValue("class", "average-score").size();
			if (nameS > 0 && priceS > 0 && ratingS > 0 && specsS > 0 && offersS > 0 && ratingS > 0) {
				String str = d.get(i).getElementsByAttributeValue("class", "sku-title").get(0).text();
				int ps1 = str.indexOf("-");
				int ps2 = str.lastIndexOf("-");
				String company = str.substring(0, ps1);

				String name = str.substring(ps1 + 1, ps2);
				String color = str.substring(ps2 + 1, str.length());
				String price = d.get(i).getElementsByAttributeValue("class", "medium-item-price").get(0).text();
				String model;
				if (modelS == 0) model = "";
				else model = d.get(i).getElementsByAttributeValue("itemprop", "model").get(0).text();
				String specs = d.get(i).getElementsByAttributeValue("class", "short-description").get(0).text();
				String offers = d.get(i).getElementsByAttributeValue("class", "special-offers").get(0).text();
				String rating = d.get(i).getElementsByAttributeValue("class", "average-score").text();




				try {
					//File file = new File("data/" + date + "/" + qr + "/bestbuy_" + qr + "__" + date + ".csv");
					File file = new File("data/bestbuy/" + qr + "/" + date + ".csv");
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
					file.getParentFile().mkdirs();
					FileWriter fw = new FileWriter(file, true); //the true will append the new data
					fw.write("\"" + name + "\",\"" + price + "\",\"" + model + "\",\"" + company + "\",\"" + color + "\",\"" + specs + "\",\"" + offers + "\",\"" + rating + "\"\n"); //appends the string to the file
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}


		}
	}
	//############################################################################ END - BESTBUY

	//############################################################################ HOMEDEPOTE
	public void get_homedepote(String query, String date) throws IOException {
		qr = query;

		Document doc = Jsoup.connect("http://www.homedepot.com/s/" + query).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Elements d = doc.getElementsByAttributeValue("class", "pagination-wrapper").get(0).getElementsByTag("li");
		int s;
		if (d.size() > 2) s = Integer.parseInt(d.get(d.size() - 2).text().trim());
		else s = 1;
		if (s > 100) s = 100;
		for (int i = 1; i <= s; i++) {
			if (i == 1) {
				get_all_homedepote("http://www.homedepot.com/s/" + query, date);
			} else {
				get_all_homedepote("http://www.homedepot.com/b/N-5yc1v/Ntk-SemanticSearch/Ntt-" + query + "?Nao=" + (i - 1) * 24 + "&Ntx=mode%20matchall", date);
			}

		}
	}


	public static void get_all_homedepote(String url, String date) throws IOException {

		Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();

		Elements d = doc.getElementsByClass("product");

		for (int i = 0; i < d.size(); i++) {
			int nameS = d.get(i).getElementsByAttributeValue("class", "item_description position_tracking_btn").size();
			int priceS = d.get(i).getElementsByAttributeValue("class", "xlarge item_price").size();
			int modelS = d.get(i).getElementsByAttributeValue("class", "model_container").size();
			int shippingS = d.get(i).getElementsByAttributeValue("class", "shippingItems").size();
			int ratingsS = d.get(i).getElementsByAttributeValue("class", "ratings").size();
			if (nameS > 0 && priceS > 0 && modelS > 0 && shippingS > 0 && ratingsS > 0) {
				String name = d.get(i).getElementsByAttributeValue("class", "item_description position_tracking_btn").get(0).text();
				String price = d.get(i).getElementsByAttributeValue("class", "xlarge item_price").get(0).text();
				String model = d.get(i).getElementsByAttributeValue("class", "model_container").get(0).getElementsByTag("span").get(1).text();
				String shd1 = d.get(i).getElementsByAttributeValue("class", "shippingItems").get(0).getElementsByTag("li").get(0).text();
				String shd2 = d.get(i).getElementsByAttributeValue("class", "shippingItems").get(0).getElementsByTag("li").get(1).text();
				String ratings = d.get(i).getElementsByAttributeValue("class", "ratings").get(0).getElementsByTag("span").get(0).attr("rel");

				try {
					//File file = new File("data/" + date + "/" + qr + "/homedepote_" + qr + "__" + date + ".csv");
					File file = new File("data/homedepote/" + qr + "/" + date + ".csv");
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
					file.getParentFile().mkdirs();
					FileWriter fw = new FileWriter(file, true); //the true will append the new data
					fw.write("\"" + name + "\",\"" + price + "\",\"" + model + "\",\"" + shd1 + "\",\"" + shd2 + "\",\"" + ratings + "\"\n"); //appends the string to the file  product-rating
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		}
	}
	//############################################################################ END - HOMEDEPOTE

	//############################################################################ WALMART
	public void get_walmart(String query, String date) throws IOException {
		qr = query;
		Document doc = Jsoup.connect("http://www.walmart.com/search/?query=" + query).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Elements d = doc.getElementsByAttributeValue("class", "paginator-list").get(0).getElementsByTag("li");
		int s = Integer.parseInt(d.get(d.size() - 1).text().trim());
		if (s > 100) s = 100;
		for (int i = 1; i <= s; i++) {
			get_all_walmart("http://www.walmart.com/search/?query=" + query + "&page=" + i + "&cat_id=0", date);


		}
	}


	public static void get_all_walmart(String url, String date) throws IOException {

		Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();

		Elements d = doc.getElementsByClass("tile-content");

		for (int i = 0; i < d.size(); i++) {
			int nameS = d.get(i).getElementsByAttributeValue("class", "tile-heading").size();
			int priceS = d.get(i).getElementsByAttributeValue("class", "price price-display").size();
			int specsS = d.get(i).getElementsByAttributeValue("class", "quick-specs module").size();
			int vendorS = d.get(i).getElementsByAttributeValue("class", "tile-aside-content").size();
			if (nameS > 0 && priceS > 0 && specsS > 0 && vendorS > 0) {
				String name = d.get(i).getElementsByAttributeValue("class", "tile-heading").get(0).text();
				String price = d.get(i).getElementsByAttributeValue("class", "price price-display").get(0).text();
				String specs = d.get(i).getElementsByAttributeValue("class", "quick-specs module").get(0).text();
				String vendor = d.get(i).getElementsByAttributeValue("class", "tile-aside-content").get(0).text();
				float star = d.get(i).getElementsByAttributeValue("class", "star star-rated").size();
				int partial = d.get(i).getElementsByAttributeValue("class", "star star-partial").size();
				if (partial == 1) {
					star += 0.5;
				}

				try {
					//File file = new File("data/" + date + "/" + qr + "/walmart_" + qr + "__" + date + ".csv");
					File file = new File("data/walmart/" + qr + "/" + date + ".csv");
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
					file.getParentFile().mkdirs();
					FileWriter fw = new FileWriter(file, true);
					fw.write("\"" + name + "\",\"" + price + "\",\"" + specs + "\",\"" + vendor + "\",\"" + star + "\"\n"); //appends the string to the file  product-rating
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		}

	}
	//############################################################################ END - WALMART

	//############################################################################ AMAZON
	public void get_amazon(String query, String date) throws IOException {

		qr = query;

		Document doc = Jsoup.connect("http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=ipad&rh=i%3Aaps%2Ck%3A" + qr).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();
		Element d = doc.getElementById("bottomBar");
		int s = Integer.parseInt(d.getElementsByAttributeValue("class", "pagnDisabled").text());

		if (s > 100) s = 100;
		for (int i = 1; i <= s; i++) {

			get_all_amazon("http://www.amazon.com/s/ref=sr_pg_2?rh=i%3Aaps%2Ck%3A" + qr + "&page=" + i + "&keywords=" + qr + "&ie=UTF8&qid=1435646618&spIA=B00TGPEI44,B00TGPE0ZG", date);



		}
	}


	public static void get_all_amazon(String url, String date) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 8.0; windows NT 6.0)").timeout(10 * 2000).followRedirects(true).get();

		Elements d = doc.getElementsByClass("s-item-container");
		for (int i = 0; i < d.size(); i++) {
			int nameS = d.get(i).getElementsByClass("s-access-title").size();
			int priceS = d.get(i).getElementsByAttributeValue("class", "a-size-base a-color-price s-price a-text-bold").size();
			int vendorS = d.get(i).getElementsByAttributeValue("class", "a-size-small a-color-secondary").size();
			int ratingS = d.get(i).getElementsByAttributeValue("class", "a-popover-trigger a-declarative").size();
			int subpriceS = d.get(i).getElementsByAttributeValue("class", "a-size-base a-color-price a-text-bold").size();
			if (nameS > 0 && priceS > 0 && vendorS > 0 && ratingS > 0) {

				String name = d.get(i).getElementsByClass("s-access-title").get(0).text();
				String price = d.get(i).getElementsByAttributeValue("class", "a-size-base a-color-price s-price a-text-bold").get(0).text();
				String vendor = d.get(i).getElementsByAttributeValue("class", "a-size-small a-color-secondary").get(1).text();
				String rating = d.get(i).getElementsByAttributeValue("class", "a-popover-trigger a-declarative").get(0).text();
				String newprice = "";
				String oldprice = "";

				if (subpriceS == 1) {
					newprice = d.get(i).getElementsByAttributeValue("class", "a-size-base a-color-price a-text-bold").get(0).text();
				} else if (subpriceS == 2) {
					newprice = d.get(i).getElementsByAttributeValue("class", "a-size-base a-color-price a-text-bold").get(0).text();
					oldprice = d.get(i).getElementsByAttributeValue("class", "a-size-base a-color-price a-text-bold").get(1).text();
				}


				try {
					//File file = new File("data/" + date + "/" + qr + "/amazon_" + qr + "__" + date + ".csv");
					File file = new File("data/amazon/" + qr + "/" + date + ".csv");
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
					file.getParentFile().mkdirs();
					FileWriter fw = new FileWriter(file, true);
					fw.write("\"" + name + "\",\"" + price + "\",\"" + newprice + "\",\"" + oldprice + "\",\"" + vendor + "\",\"" + rating + "\"\n");
					fw.close();
				} catch (IOException ioe) {
					System.err.println("IOException: " + ioe.getMessage());
				}
			}
		}

	}
	//############################################################################ END - Amazon

}