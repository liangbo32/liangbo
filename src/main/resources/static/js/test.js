$(document).ready(function() {
	$(function() {
		 $('#file_upload').uploadify({
			'swf' : 'js/uploadify/3/uploadify.swf',
			'uploader' : '/liangbo/upload',
			'height' : 25,
			'whith' : 120,
			'auto' : false,
	        'fileObjName' : 'file',
			'buttonText' : '选择图片...',
			'fileTypeExts' : '*.gif; *.jpg; *.png',
			'multi' : false,
			'method' : 'post',
			'debug' : false,
			'onUploadStart' : function(file) {
				alert(1);
			},
			'onUploadSuccess' : function(file, data, response) {
//				file.name
//				data
			},
			'onUploadError' : function(file, errorCode, errorMsg, errorString) {
				alert(3);
			}

		});
	});
});