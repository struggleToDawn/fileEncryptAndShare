package pku.yang.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttrExpressToSql {
	
	private String attributeKeyAndValueTrans(String str,String type){
	
		if(str.startsWith("user_pid")) 	return "u."+str;
		if(str.startsWith("userID")) 	return "u."+str;
		if(str.startsWith("username")) 	return "u."+str;
		if(str.startsWith("password")) 	return "u."+str;
		if(str.startsWith("storageID")) return "u."+str;
		if(str.startsWith("roleNum"))   return "u."+str;
		if(str.startsWith("type"))		return "u."+str;
		if("1".equals(type)){
			if(str.startsWith("id"))    	return "t."+str;
			if(str.startsWith("name"))		return "t."+str;
			if(str.startsWith("department")) return "t."+str;
			if(str.startsWith("courses"))	return "t."+str;
			if(str.startsWith("teacherID")) return "t."+str;
			if(str.startsWith("studygroup")) return "t."+str;
			if(str.startsWith("academy"))	return "t."+str;
			if(str.startsWith("age"))		return "t."+str;
		}else{
			if(str.startsWith("id")) 		return "s."+str;
			if(str.startsWith("name"))		return "s."+str;
			if(str.startsWith("department")) return "s."+str;
			if(str.startsWith("title")) 	return "s."+str;
			if(str.startsWith("duty"))		return "s."+str;
			if(str.startsWith("studyGroup")) return "s."+str;
			if(str.startsWith("courses")) 	return "s."+str;
			if(str.startsWith("age"))		return "s."+str;
		}
		if(str.startsWith("u.") || str.startsWith("s.") || str.startsWith("t.")){
			return str;
		}
		return "";
	}
		
	
	public AttrExpressToSql() {}
	
	public Map<String,StringBuffer> getSql(String attributeExpression){
		
		ArrayList<String> expressionList = getStringList(attributeExpression);
		
		Map<String,StringBuffer> hqlMap = new HashMap<String, StringBuffer>();
		
		StringBuffer studentHql = new StringBuffer("select u From User u,Student s where (");
		StringBuffer teacherHql = new StringBuffer("select u From User u,Teacher t where (");
		
		String studentStrTmp = "";
		String teacherStrTmp = "";
		
		for (String str :expressionList ){ 
			switch(str){
				case "#" : break;
				case "$" : studentHql.append(" or"); teacherHql.append(" or"); break;
				case "&" : studentHql.append(" and"); teacherHql.append(" and"); break;
				case "!" : studentHql.append(" not"); teacherHql.append(" not"); break;
				case "(" : studentHql.append(" ("); teacherHql.append(" ("); break;
				case ")" : studentHql.append(" )");  teacherHql.append(" )"); break;
				default:
	
					studentStrTmp = attributeKeyAndValueTrans(str,"0");
					if("".endsWith(studentStrTmp)){
						studentStrTmp = ("s.id = '-1'") ;
					}
					studentHql.append(" ("+ studentStrTmp+")");
					
					teacherStrTmp = attributeKeyAndValueTrans(str,"1");
					if("".endsWith(teacherStrTmp)){
						teacherStrTmp = ("t.id = '-1'") ;
					}
					teacherHql.append(" ("+ teacherStrTmp+")"); 
			}			
		}
		
		hqlMap.put("0", studentHql.append(" ) and u.userID = s.id and u.type = '0'"));
		hqlMap.put("1", teacherHql.append(" ) and u.userID = t.id and u.type = '1'"));
		
		return hqlMap;
	}
	
	private ArrayList<String> getStringList(String attributeExpression){
		
		attributeExpression = attributeExpression.replaceAll("\\s", ""); 
		
		ArrayList<String> result = new ArrayList<String>();
		String keyValue = "";
		for (int i = 0; i < attributeExpression.length(); i++) {
			
			char ch = attributeExpression.charAt(i);
			
			switch(ch){
				case '$': 
				case '&':
				case '!':
				case '(':
				case ')':
				case '#':
					if(!"".equals(keyValue)) result.add(keyValue);
					result.add(String.valueOf(ch));keyValue="";
					break;
				default : keyValue += attributeExpression.charAt(i);
			}
		}
		return result;
	}
}
