package pku.yang.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import pku.yang.model.User;

public interface IUploadUserService {
	List<?> saveExcelData(String fileName,String type) throws IOException;
}
