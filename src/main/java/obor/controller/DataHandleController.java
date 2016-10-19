package obor.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import obor.Util.OborUtil;

@Controller
public class DataHandleController {
	
	Logger logger = LoggerFactory.getLogger(DataHandleController.class);

	@RequestMapping(value = "/main")
	public String main(){
		return "pages/test";
	}
	
	@RequestMapping(value = "/upload")
	public void upload(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res) {
		logger.info("开始上传文件");
		res.setHeader("X-Frame-Options", "SAMEORIGIN");
		String oriFileName = "";
		try {
//			String rootPath = req.getSession().getServletContext().getRealPath("/");
			if (!file.isEmpty()) {
				//接收上传文件
				String path = "E:\\xa";//Constant.UPLOAD_PATH;
				File temp = new File(path);
				if (!temp.exists()) {
					temp.mkdir();
				}
				oriFileName = file.getOriginalFilename();
				String notTime = OborUtil.getNowTime();
				String[] str = oriFileName.split("[.]");
				String fileType = str[str.length-1];
				int index = oriFileName.indexOf(str[str.length-1]);
				String fileName = oriFileName;
				if(fileType.equals("xml")){//xml格式
					fileName = fileName.substring(0, index-1) + notTime + ".xml";
				}else if(fileType.equals("xls")||fileType.equals("xlsx")){//excel格式
					fileName = fileName.substring(0, index-1) + notTime + "." + fileType;
				}
				//文件上传服务器
				String filePath = path + File.separator + fileName;
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(filePath)));
				stream.write(bytes);
				stream.close();
				
				res.setContentType("text/html; charset=UTF-8"); // 转码
				PrintWriter out = res.getWriter();
				out.flush();		
				out.print("asdfasdfasd");
				logger.info("文件已上传服务器："+filePath);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
