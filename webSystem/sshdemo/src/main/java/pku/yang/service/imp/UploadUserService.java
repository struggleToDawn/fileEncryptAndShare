package pku.yang.service.imp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pku.yang.dao.IUserDao;
import pku.yang.model.Student;
import pku.yang.model.User;
import pku.yang.model.UserType;
import pku.yang.service.IUploadUserService;
import pku.yang.service.IUserService;

@Service
public class UploadUserService implements IUploadUserService {
	@Autowired
	private IUserDao userdao;

	@Override
	public List<?> saveExcelData(String fileName, String type)
			throws IOException {
		InputStream ins = new FileInputStream(fileName);
		List<User> users = new ArrayList<>();
		try{
			Workbook workbook = WorkbookFactory.create(ins);
			
			//TODO fix
			//suppose only one sheet in the workbook, and it's the first
			Sheet sheet = workbook.getSheetAt(0);
			if (type.equals(UserType.Student.toString())) {
				List<Student> students= analysisStuSheet(sheet,users);
				userdao.saveUsers(users);
				userdao.saveStudents(students);
				return students;
			} else if (type.equals(UserType.Teacher.toString())) {
				//TODO
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private List<Student> analysisStuSheet(Sheet sheet,
			List<User> users) {
		List<Student> students = new ArrayList<>();
		for(int rowNum = 1;rowNum < sheet.getLastRowNum(); rowNum++){
			Row row = sheet.getRow(rowNum);
			Student student = new Student();
			student.setId(row.getCell(0).getStringCellValue());
			student.setName(row.getCell(1).getStringCellValue());
			student.setAge((int)row.getCell(2).getNumericCellValue());
			student.setTeacherID(row.getCell(3).getStringCellValue());
			student.setDepartment(row.getCell(4).getStringCellValue());
			student.setAcademy(row.getCell(5).getStringCellValue());
			student.setStudygroup(row.getCell(6).getStringCellValue());
			student.setCouses(row.getCell(7).getStringCellValue());
			students.add(student);
			users.add(new User(student));
		}
		return students;
	}
	

}
