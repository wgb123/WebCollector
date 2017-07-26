package Test.WebCollector;

import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * 本教程演示了如何自定义http请求
 *
 * 有些爬取任务中，可能只有部分URL需要使用POST请求，我们可以利用2.20版本中添 加的MetaData功能，来完成POST请求的定制。
 *
 * 使用MetaData除了可以标记URL是否需要使用POST，还可以存储POST所需的参数信息
 *
 * 教程中还演示了如何定制Cookie、User-Agent等http请求头信息
 *
 * WebCollector中已经包含了org.json的jar包
 *
 * @author hu
 */
public class Crawler58 extends BreadthCrawler {

	public Crawler58(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		this.addRegex(".*.58.com.*");
	}

	@Override
	public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
		HttpRequest request = new HttpRequest(crawlDatum.getUrl());

		// request.setMethod(crawlDatum.getMetaData("method"));
		// request.setCookie(".Cnblogs.AspNetCore.Cookies=CfDJ8PhlBN8IFxtHhqIV3s0LCDl3HbECXyKO9RsvcopgrkZaYSLH7XY2NqBbZESixd-1XIUAJHl_VuAGIhNoHxJ4Y0Kh_i65IKTrGNVhX4v58n_6funKWckyTto-5P_MzK41KT_eltKmu6owlhLwOThIXIq9aSMuhly2rYOOABn67vqX2i14kdbuG6pT_XE2-piofMyy9uzu_B8QQn9YumrVsG6D7nvCPEp8QzVoCAjGOrUSdgNCRSwKpXcym483iJMjnm6KJ1FmaaV6RlndOHfSsk8gVsncI9wye6D_jSrpUIC0");
		// String outputData = crawlDatum.getMetaData("outputData");
		// if (outputData != null) {
		// request.setOutputData(outputData.getBytes("utf-8"));
		// }
		return request.getResponse();
		/*
		 * //通过下面方式可以设置Cookie、User-Agent等http请求头信息
		 * request.setCookie("xxxxxxxxxxxxxx");
		 * request.setUserAgent("WebCollector"); request.addHeader("xxx",
		 * "xxxxxxxxx");
		 */
	}

	public void visit(Page page, CrawlDatums next) {
		// String jsonStr = page.getHtml();
		// String text = page.getHtml();
		// String img = page.select("img").first().text();
		String content = page.select("title").text();
		Document doc = page.getDoc();
		String url = page.getUrl();
		// JSONObject json = new JSONObject(jsonStr);
		// System.out.println(text);
		// System.out.println(img + "123");
		System.out.println(content);
		System.out.println(url);
		// System.out.println("JSON信息：" + json);
	}

	/**
	 * 假设我们要爬取三个链接 1)http://www.A.com/index.php 需要POST，并且需要附带数据id=a
	 * 2)http://www.B.com/index.php?id=b 需要POST，不需要附带数据 3)http://www.C.com/
	 * 需要GET
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Crawler58 crawler = new Crawler58("zz58", true);
		// crawler.addSeed(new
		// CrawlDatum("http://www.A.com/index.php").putMetaData("method",
		// "POST")
		// .putMetaData("outputData", "id=a"));
		// crawler.addSeed(new
		// CrawlDatum("http://www.B.com/index.php").putMetaData("method",
		// "POST"));
		// crawler.addSeed(new
		// CrawlDatum("http://www.C.com/index.php").putMetaData("method",
		// "GET"));
		crawler.addSeed(new CrawlDatum("http://zz.58.com/zufang/30802593625644x.shtml"));
		crawler.setTopN(20);
		crawler.start(100);
	}

}