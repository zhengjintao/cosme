package com.tkt.biz.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

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
		String shopname = request.getParameter("shopname");
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
