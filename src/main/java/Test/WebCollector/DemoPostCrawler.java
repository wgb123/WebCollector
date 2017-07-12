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
public class DemoPostCrawler extends BreadthCrawler {

	public DemoPostCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		this.addRegex(".*cnblog.*");
	}

	@Override
	public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
		HttpRequest request = new HttpRequest(crawlDatum.getUrl());

		// request.setMethod(crawlDatum.getMetaData("method"));
		request.setCookie(
				".Cnblogs.AspNetCore.Cookies=CfDJ8PhlBN8IFxtHhqIV3s0LCDm5qI2aU9fK8DNIBztOP8zupoWRKPK-uZIG7frP__hxNmQoSj8CIb_U8ATt5zhwNsNZrhk6wlbDKauIyQ158p76pKcE4IgHWlYArctfR9h5BGaJJjVk7VARcmJnvm5zQIahNRhDxAULcikHkrIw76GpuUTPIHwMrZJWyQq3nxVuOJ9oTl_sSofuXERgLtBhABig26feRhNmPm5RbqcavTN8EXxAWtLZlf_mcXrU7U2L2qdamytj4qbUOaa9CEeF7TMkC5FoJ3TUiKbSAOfCchDsDjHR4Lp9D79pxH1sqHOrxw");
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
		String img = page.select("img").first().text();
		String content = page.select("title").text();
		Document doc = page.getDoc();
		String url = page.getUrl();
		// JSONObject json = new JSONObject(jsonStr);
		// System.out.println(text);
		System.out.println(img + "123");
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

		DemoPostCrawler crawler = new DemoPostCrawler("cnblogs", true);
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
		crawler.addSeed(new CrawlDatum("https://home.cnblogs.com/u/wgb123/"));
		crawler.setTopN(10);
		crawler.start(2);
	}

}