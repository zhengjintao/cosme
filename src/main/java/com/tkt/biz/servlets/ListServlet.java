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
		good2.setCode("4936201100941");
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
		shopinfo3.setGoodCode("4936201100941");
		shopinfo3.setShopName("松本清上野店");
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
		String mode = request.getParameter("mode");
		if("search".equals(mode)){
			this.list(request, response);
		}else if("listhot".equals(mode)){
			this.listhot(request, response);
		}else if("init".equals(mode)){
			this.init(request, response);
		}
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
	 * 検索
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
				goodinfo.put("name", good.getName());
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
	
	/**
	 * 検索
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void listhot(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		JSONArray goodinfoList = new JSONArray();
		int i = 0;
		for (GoodInfo good : goodslist) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", good.getCode());
			jsonObject.put("name", good.getName());

			goodinfoList.put(i, jsonObject);
			i++;
		}

		// 最終結果
		JSONObject result = new JSONObject();
		result.put("hotgoods", goodinfoList);

		response.getWriter().write(result.toString());

	}
	
	private void init(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String goodscode = request.getParameter("goodscode");
		if(goodscode==null || goodscode.length()==0){
			goodscode="";
		}
		
		JSONObject initdata = new JSONObject();
		initdata.put("goodscode", goodscode);
		
		request.setAttribute("initdata", initdata);
		request.getRequestDispatcher("list.jsp").forward(request, response);
	}

}
