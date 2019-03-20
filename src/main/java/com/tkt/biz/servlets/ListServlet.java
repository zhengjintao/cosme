package com.tkt.biz.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tkt.biz.servlets.common.JdbcUtil;
import com.tkt.biz.servlets.pojo.GoodInfo;
import com.tkt.biz.servlets.pojo.ShopGoodInfo;

/**
 * Servlet implementation class MakeOrderServlet
 */
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List<GoodInfo> goodslist = new ArrayList<GoodInfo>();
	private List<ShopGoodInfo> shopGoodInfoList = new ArrayList<ShopGoodInfo>();

	/**
	 * Default constructor.
	 */
	public ListServlet() {
	}
	
	private void initdata(){
		String sql = "select * from cmstr_goods";
		List<Object> listgoods = JdbcUtil.getInstance().excuteQuery(sql, null);

		goodslist = new ArrayList<GoodInfo>();
		
		for (Object data : listgoods) {
			Map<String, Object> row = (Map<String, Object>) data;
			GoodInfo good = new GoodInfo();
			if (row.get("goodscode") == null){
				continue;
			}
			good.setCode(row.get("goodscode").toString());
			good.setName(row.get("goodsname").toString());
			good.setImgurl(row.get("imgurl").toString());
			goodslist.add(good);
		}
		
		sql = "select * from cdata_goodsinfo info left join cmstr_goods goods on info.goodscode=goods.goodscode"
				+ " left join cmstr_shop shop on info.shopcode=shop.shopcode";
		List<Object> listgoodsinfo = JdbcUtil.getInstance().excuteQuery(sql, null);
		shopGoodInfoList = new ArrayList<ShopGoodInfo>();
		
		for (Object data : listgoodsinfo) {
			Map<String, Object> row = (Map<String, Object>) data;
			ShopGoodInfo goodinfo = new ShopGoodInfo();
			goodinfo.setGoodCode(row.get("goodscode").toString());
			goodinfo.setShopName(row.get("shopname").toString());
			goodinfo.setPrice(row.get("price").toString());
			shopGoodInfoList.add(goodinfo);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if("search".equals(mode)){
			initdata();
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
		
		if(goodinfo.length() >0 && searchCode.length() >0){
			String sql = "select * from cdata_good_add where goodscode=?";
			Object[] params = new Object[1];
			params[0] = searchCode;
			List<Object> listgoods = JdbcUtil.getInstance().excuteQuery(sql, params);
			
			if(listgoods.size() > 0){
				Map<String, Object> row = (Map<String, Object>) listgoods.get(0);
				int count = (Integer)row.get("visitcount");
				count++;
				sql = "update cdata_good_add set visitcount=? where goodscode=?";
				params = new Object[2];
				params[0] = count;
				params[1] = searchCode;
				
				JdbcUtil.getInstance().executeUpdate(sql, params);
			}else{
				sql = "insert into cdata_good_add values(?,?)";
				params = new Object[2];
				params[0] = searchCode;
				params[1] = 0;
				JdbcUtil.getInstance().executeUpdate(sql, params);
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
		String sql = "select * from cdata_good_add ad left join cmstr_goods mstr on ad.goodscode = mstr.goodscode order by ad.visitcount desc";
		List<Object> listgoods = JdbcUtil.getInstance().excuteQuery(sql, null);
		
		JSONArray goodinfoList = new JSONArray();
		int i = 0;
		for (Object data : listgoods) {
			Map<String, Object> row = (Map<String, Object>) data;
			JSONObject jsonObject = new JSONObject();
			if (row.get("goodscode") == null){
				continue;
			}
			jsonObject.put("code", row.get("goodscode").toString());
			jsonObject.put("name", row.get("goodsname").toString());

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
