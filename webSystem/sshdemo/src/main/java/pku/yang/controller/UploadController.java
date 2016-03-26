package pku.yang.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value="/user")
public class UploadController {
	
	private String filePath;
	
	@RequestMapping(value="/import",method=RequestMethod.GET)
	public String getImport(){
		return "/usermanage/import_user";
	}
	
	@RequestMapping(value="/import",method=RequestMethod.POST)
	@ResponseBody
	public String postImport(MultipartFile file,Model model,HttpServletRequest request) throws ParseException{
		//TODO
		if(!file.isEmpty()){
			String originalName = file.getOriginalFilename();
			StringBuilder path = new StringBuilder(filePath);
			path.append(generateFileName());
			path.append(originalName.substring(originalName.lastIndexOf(".")));
			File tempFile = new File(path.toString());
			if(!tempFile.getParentFile().exists()){
				tempFile.getParentFile().mkdir();
			}
			while(tempFile.exists()){
				path = new StringBuilder(filePath);
				path.append(generateFileName());
				path.append(originalName.substring(originalName.lastIndexOf("."))); 
				tempFile = new File(path.toString());
			}
			try {
				tempFile.createNewFile();
				file.transferTo(tempFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		return "lala";
	}
	/**
	 * get timestamp and a random
	 * timestamp*1000+Random==>hex for new file name
	 * @return
	 */
	private String generateFileName(){
		
		Long timestamp = System.currentTimeMillis();
		int random = new Random().nextInt(1000);
		String fileName = Long.toHexString(timestamp*1000+random);
		return fileName;
	}
	

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
