package com.tkt.biz.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tkt.biz.servlets.pojo.GoodInfo;
import com.tkt.biz.servlets.pojo.ShopGoodInfo;

/**
 * Servlet implementation class MakeOrderServlet
 */
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static List<GoodInfo> goodslist = new ArrayList<GoodInfo>();
	private static List<ShopGoodInfo> shopGoodInfoList = new ArrayList<ShopGoodInfo>();

	static {
		// Test data
		GoodInfo good = new GoodInfo();
		good.setCode("0001");
		good.setName("おいしい弁当");
		good.setImgurl("asserts/images/lunch.jpeg");

		GoodInfo good2 = new GoodInfo();
		good2.setCode("0002");
		good2.setName("food no2");
		good2.setImgurl("asserts/images/lunch2.jpeg");

		goodslist.add(good);
		goodslist.add(good2);

		ShopGoodInfo shopinfo = new ShopGoodInfo();
		shopinfo.setGoodCode("0001");
		shopinfo.setShopName("松本清川口店");
		shopinfo.setPrice("199");

		ShopGoodInfo shopinfo2 = new ShopGoodInfo();
		shopinfo2.setGoodCode("0001");
		shopinfo2.setShopName("shop2");
		shopinfo2.setPrice("299");

		ShopGoodInfo shopinfo3 = new ShopGoodInfo();
		shopinfo3.setGoodCode("0002");
		shopinfo3.setShopName("shop1");
		shopinfo3.setPrice("399");

		shopGoodInfoList.add(shopinfo);
		shopGoodInfoList.add(shopinfo2);
		shopGoodInfoList.add(shopinfo3);
	}

	/**
	 * Default constructor.
	 */
	public ListServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.list(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * ログイン処理
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String searchCode = request.getParameter("searchcode");
		JSONObject goodinfo = new JSONObject();
		for (GoodInfo good : goodslist) {
			if (good.getCode().equals(searchCode)) {
				goodinfo.put("code", good.getCode());
				goodinfo.put("imgurl", good.getImgurl());
				break;
			}
		}

		JSONArray shopinfoList = new JSONArray();
		int i = 0;
		for (ShopGoodInfo shop : shopGoodInfoList) {
			if (shop.getGoodCode().equals(searchCode)) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", shop.getShopName());
				jsonObject.put("price", shop.getPrice());

				shopinfoList.put(i, jsonObject);
				i++;
			}
		}

		// 最終結果
		JSONObject result = new JSONObject();
		result.put("goodinfo", goodinfo);
		result.put("shoplist", shopinfoList);

		response.getWriter().write(result.toString());

	}

}
