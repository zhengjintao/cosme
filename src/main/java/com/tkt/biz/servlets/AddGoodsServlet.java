package com.tkt.biz.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import com.tkt.biz.servlets.common.JdbcUtil;
import com.tkt.biz.servlets.common.StringUtil;

/**
 * 店铺商品追加
 */
public class AddGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if("save".equals(mode)){
			this.save(request, response);
		}else if("init".equals(mode)){
			this.init(request, response);
		}
	}

	/**
	 * 初期化处理
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void init(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String goodscode = request.getParameter("goodscode");
		String imgurl = request.getParameter("imgurl");
		
		goodscode = (goodscode==null || goodscode.length()==0) ? "" : goodscode;
		imgurl = (imgurl==null || imgurl.length()==0) ? "" : imgurl;
		
		JSONObject initdata = new JSONObject();
		initdata.put("goodscode", goodscode);
		initdata.put("imgurl", imgurl);
		
		request.setAttribute("initdata", initdata);
		request.getRequestDispatcher("addgoods.jsp").forward(request, response);
	}
	
	/**
	 * 商品追加
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String goodscode = request.getParameter("goodscode");
		String imgurl = request.getParameter("imgurl");
		String shopcode = request.getParameter("shopcode");
		String shopname = request.getParameter("shopname");
		String price = request.getParameter("price");
		
		// 商品追加
		addGoodsMastr(goodscode,"",imgurl);
		
		// 店铺追加
		shopcode = addShopMastr(shopcode,shopname);
		
		// 店铺商品追加
		String sql = "insert into cdata_goodsinfo values(?,?,?)";
		Object[] params = new Object[3];
		params[0] = goodscode;
		params[1] = shopcode;
		params[2] = price;
		JdbcUtil.getInstance().executeUpdate(sql, params);
	}
	
	/**
	 * 商品追加
	 * @param goodscode
	 * @param goodsname
	 * @param imgurl
	 */
	private void addGoodsMastr(String goodscode,String  goodsname, String imgurl){
		String sql1 = "select * from cmstr_goods where goodscode = ?";
		Object[] params1 = new Object[1];
		params1[0] = goodscode;
		List<Object> goods = JdbcUtil.getInstance().excuteQuery(sql1, params1);
		if(goods.size()==0){
			String sql = "insert into cmstr_goods values(?,?,?)";
			Object[] params = new Object[3];
			params[0] = goodscode;
			params[1] = goodsname;
			params[2] = imgurl;
			JdbcUtil.getInstance().executeUpdate(sql, params);
		}
	}
	
	/**
	 * 店铺追加
	 * @param shopscode
	 * @param shopname
	 */
	private String addShopMastr(String shopcode,String shopname){
		if(shopcode == "" || shopcode == null){
			
			// 新店铺CODE取得 MAX+1
			String sql = "SELECT max(shopcode) as code FROM cmstr_shop";
			List<Object> maxshopcode = JdbcUtil.getInstance().excuteQuery(sql, null);
			
			for (Object code : maxshopcode) {
				Map<String, Object> row = (Map<String, Object>) code;
				shopcode = row.get("code").toString();
			}
			int maxcode = Integer.parseInt(shopcode);
			maxcode = maxcode +1;
			shopcode = StringUtil.padLeft(String.valueOf(maxcode), 10, '0');
			
			// 新店铺追加
			String sql2 = "insert into cmstr_shop values(?,?)";
			Object[] params2 = new Object[2];
			params2[0] = shopcode;
			params2[1] = shopname;
			JdbcUtil.getInstance().executeUpdate(sql2, params2);	
		}
		return shopcode;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
