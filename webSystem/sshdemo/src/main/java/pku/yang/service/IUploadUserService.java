package pku.yang.service;

import java.io.IOException;
import java.util.List;

import pku.yang.model.User;

public interface IUploadUserService {
	List<User> analysisExcel(String fileName) throws IOException;
}
