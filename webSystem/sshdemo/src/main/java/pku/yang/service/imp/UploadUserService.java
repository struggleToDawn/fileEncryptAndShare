package pku.yang.service.imp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import pku.yang.model.User;
import pku.yang.service.IUploadUserService;

public class UploadUserService implements IUploadUserService {

	@Override
	public List<User> analysisExcel(String fileName) throws IOException {
		InputStream ins = new FileInputStream(fileName);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(ins);
		
		return null;
	}

}
