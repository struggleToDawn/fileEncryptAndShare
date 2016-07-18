package pku.yang.service.imp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pku.yang.dao.IUserDao;
import pku.yang.model.Student;
import pku.yang.model.Teacher;
import pku.yang.model.User;
import pku.yang.model.UserType;
import pku.yang.service.IFolderService;
import pku.yang.service.ISpaceService;
import pku.yang.service.IUploadUserService;
import pku.yang.service.IUserService;

@Service
public class UploadUserService implements IUploadUserService {
	@Autowired
	private IUserDao userdao;
	
	@Autowired
	private ISpaceService spaceService;
	@Autowired
	private IFolderService folderService;

	/*
	 * (non-Javadoc)
	 * @see pku.yang.service.IUploadUserService#saveExcelData(java.lang.String, java.lang.String)
	 */
	@Override
	public List<?> saveExcelData(String fileName, String type)
			throws IOException {
		InputStream ins = new FileInputStream(fileName);
		List<User> users = new ArrayList<>();
		try{
			Workbook workbook = WorkbookFactory.create(ins);
			//suppose only one sheet in the workbook, and it's the first
			Sheet sheet = workbook.getSheetAt(0);
			if (type.equals(UserType.Student.toString())) {
				List<Student> students= analysisStuSheet(sheet,users);
			System.out.println(1);
				userdao.saveUsers(users);
				System.out.println(2);
				userdao.saveStudents(students);
				System.out.println(3);
				return students;
			} else if (type.equals(UserType.Teacher.toString())) {
				List<Teacher> teachers = analysisTeaSheet(sheet,users);
				userdao.saveUsers(users);
				userdao.saveTeachers(teachers);
				return teachers;
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			ins.close();
		}
		return null;
	}
	
	/**
	 * read teacher info from sheet 
	 * @param sheet	
	 * @param users	user list for save
	 * @return teacher list
	 */
	private List<Teacher> analysisTeaSheet(Sheet sheet, List<User> users){
		List<Teacher> teachers = new ArrayList<>();
		for(int rowNum = 1;rowNum <= sheet.getLastRowNum(); rowNum++){
			Row row = sheet.getRow(rowNum);
			Teacher teacher = new Teacher();
			teacher.setId(row.getCell(0).getStringCellValue());
			teacher.setName(row.getCell(1).getStringCellValue());
			teacher.setAge((int)row.getCell(2).getNumericCellValue());
//			teacher.setTitle(row.getCell(3).getStringCellValue());
			teacher.setDuty(row.getCell(3).getStringCellValue());
			teacher.setDepartment(row.getCell(4).getStringCellValue());
//			teacher.setStudyGroup(row.getCell(6).getStringCellValue());
			teacher.setCourses(row.getCell(5).getStringCellValue());
			String  uid = "1000";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ctime = df.format(new Date());
			String FolderId =  folderService.addRootFolder(uid, teacher.getName(), "3", ctime);
			String storage_id  = spaceService.addSpace(teacher.getName(), 23, FolderId);
			teachers.add(teacher);
			User user = new User(teacher);
			user.setStorageID(storage_id);
			users.add(user);
		}
		return teachers;
	}

	/**
	 * read student info from sheet 
	 * @param sheet	
	 * @param users	user list for save
	 * @return student list
	 */
	private List<Student> analysisStuSheet(Sheet sheet,
			List<User> users) {
		List<Student> students = new ArrayList<>();
		for(int rowNum = 1;rowNum <= sheet.getLastRowNum(); rowNum++){
			Row row = sheet.getRow(rowNum);
			Student student = new Student();
			student.setId(row.getCell(0).getStringCellValue());
			student.setName(row.getCell(1).getStringCellValue());
			student.setAge((int)row.getCell(2).getNumericCellValue());
//			student.setTeacherID(row.getCell(3).getStringCellValue());
			student.setDepartment(row.getCell(3).getStringCellValue());
			student.setAcademy(row.getCell(4).getStringCellValue());
			student.setStudygroup(row.getCell(5).getStringCellValue());
			student.setCourses(row.getCell(6).getStringCellValue());
			String  uid = "1000";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ctime = df.format(new Date());
			String FolderId =  folderService.addRootFolder(uid, student.getName(), "3", ctime);
			String storage_id  = spaceService.addSpace(student.getName(), 23, FolderId);
			students.add(student);
			User user = new User(student);
			user.setStorageID(storage_id);
			users.add(user);
		}
		return students;
	}
}
