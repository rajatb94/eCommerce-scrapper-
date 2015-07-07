/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hdp;
/**
 *
 * @author rajat
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
public class hdp {
	private static Date dt = new Date();
	private static int success = 0;
	private static String date;
	private static FileSystem hdfs;
	private static void copyFilesToDir(String srcDirPath, FileSystem fs, String destDirPath)
	throws IOException {
		File dir = new File(srcDirPath);
		for (File file: dir.listFiles()) {
			fs.copyFromLocalFile(new Path(file.getPath()),
			new Path(destDirPath, file.getName()));
		}
	}
	private static void saveFile(String pth) throws FileNotFoundException, IOException, URISyntaxException {
		try {
			copyFilesToDir("data/" + pth, hdfs, "hdfs://104.236.110.203:9000/data/" + pth);
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	private static void crawl(String query) throws IOException, FileNotFoundException, URISyntaxException {
		getDetails gd = new getDetails();
		try {
			if ("iphone".equals(query)) gd.get_costcoipn(query, date);
			else if ("ipad".equals(query)) gd.get_costcoipd(query, date);
			else if ("samsung phone".equals(query)) gd.get_costcospn(query, date);
			else if ("samsung tab".equals(query)) gd.get_costcostb(query, date);
			success++;

		} catch (Exception e) {
			System.out.println("Plz fix the bug under Costco code");
			System.out.println(e.getMessage());
		}
		saveFile("costco/" + query + "/");
		try {
			gd.get_bestbuy(query, date);
			success++;

		} catch (Exception e) {
			System.out.println("Plz fix the bug under BestBuy code");
			System.out.println(e.getMessage());
		}
		saveFile("bestbuy/" + query + "/");
		try {
			gd.get_homedepote(query, date);
			success++;

		} catch (Exception e) {
			System.out.println("Plz fix the bug under HomeDepote code");
			System.out.println(e.getMessage());
		}
		saveFile("homedepote/" + query + "/");
		try {
			gd.get_walmart(query, date);
			success++;

		} catch (Exception e) {
			System.out.println("Plz fix the bug under Walmart code");
			System.out.println(e.getMessage());
		}
		saveFile("walmart/" + query + "/");
		try {
			gd.get_amazon(query, date);
			success++;

		} catch (Exception e) {
			System.out.println("Plz fix the bug under Amazon code");
			System.out.println(e.getMessage());
		}
		saveFile("amazon/" + query + "/");


	}
	public static void main(String[] args) throws IOException, FileNotFoundException, URISyntaxException {
		Configuration configuration = new Configuration();
		configuration.set("fs.hdfs.impl",
		org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		configuration.set("fs.file.impl",
		org.apache.hadoop.fs.LocalFileSystem.class.getName());
		hdfs = FileSystem.get(new URI("hdfs://104.236.110.203:9000"), configuration);
		date = dt.toString().replaceAll(" ", "_").replaceAll(":", "-");
		crawl("iphone");
		crawl("ipad");
		crawl("samsung phone");
		crawl("samsung tab");
		if (success > 0) {
			try {
				FileUtils.deleteDirectory(new File("data"));
				System.out.println("DONE");

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Something Went Wrong Try to Fix the Error:::::");
		}

	}

}