$(function(){
	$("#queryaccessbypost").click(function(){
		var url = "queryaccess";
		var args = {
			"userId":"1",
			"groupId":"123",
			"fileFolderId":"24325435435344f",
			"privilege":"allowShareFloder"
		}
		$.post(url,args,function(data){
			alert(data);
		})
		return false;
	});
})

$(function(){
	$("#querypolicy").click(function(){
		var url = "querypolicy";
		var args = {
			"userId":"1",
			"groupId":"123",
			"fileFolderId":"24325435435344f",
		};
		$.post(url,args,function(data){
			alert(data);
		});
		return false;
	});
})



$(function(){
	$("#insertpolicy").click(function(){
		var url = "insertpolicy";
		var args = {
			"userId":"1",
			"groupId":"124",
			"fileFolderId":"24354395343446f",
			"allowCreateFloder":"0",
			"allowShareFloder":"1",
			"allowDeleteFloder":"0",
			"allowUploadFile":"1",
			"allowDownloadFile":"0",
			"allowDeleteFile":"1",
			"operateWays":"1",
			"integrity":"1",
			"propertyExpression":"#username='a'&(password='12' $ ty pe='1')$!userID = '1'#",
		};
		$.post(url,args,function(data){
			
		});
		//return false;
	});
})





$(function(){
	$("#deletepolicy").click(function(){
		var url = "deletepolicy";
		var args = {
			"policyId":"12"
		};
		$.post(url,args,function(data){
			alert(data);
		});
		return false;
	});
})


$(function(){
	$("#conflictDetection").click(function(){
		
		var url = "conflictdetection";
		var args = {
				"groupId":"123",
				"fileFolderId":"24325435435344f",
				"propertyExpression":"#username=\'a\'#",
				"allowCreateFloder": 1	,
				"allowShareFloder":0,
				"allowDeleteFloder":1,
				"allowUploadFile":0,
				"allowDownloadFile":1,
				"allowDeleteFile":1
		};
		$.post(url,args,function(data){
			alert(data);
		});
		return false;
	});
})


