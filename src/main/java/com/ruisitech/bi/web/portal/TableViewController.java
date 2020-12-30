package com.ruisitech.bi.web.portal;

import com.rsbi.ext.engine.view.context.ExtContext;
import com.rsbi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.portal.PortalTableQuery;
import com.ruisitech.bi.service.portal.PortalTableService;
import com.ruisitech.bi.util.CompPreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Scope("prototype")
@RequestMapping(value = "/portal")
public class TableViewController {

	@Autowired
	private PortalTableService serivce;

	@RequestMapping(value="/TableView.action", method = RequestMethod.POST)
	public @ResponseBody
    Object tableView(@RequestBody PortalTableQuery table, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ExtContext.getInstance().removeMV(PortalTableService.deftMvId);
		MVContext mv = serivce.json2MV(table);
		
		CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
		ser.setParams(serivce.getMvParams());
		ser.initPreview();
		String ret = ser.buildMV(mv, req.getServletContext());
		return ret;
	}
}
