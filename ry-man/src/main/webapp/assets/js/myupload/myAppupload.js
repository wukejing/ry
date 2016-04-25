$(function(){
    var $tbody = $('tbody[class="files"]');

    // Initialize the jQuery File Upload plugin
    $('#upload').fileupload({
    	paramName:'myfile',//文件参数名称
    	dataType:'json',
    	formAcceptCharset:'utf-8',
        // This function is called when a file is added to the queue
        add: function (e, data) {
        	//验证文件类型
        	if (!(/(\.|\/)(gif|jpe?g|png)$/.test(data.files[0].name.toLowerCase()))) {
                return false;
            }
            
			var html = 		'<tr class="template-upload fade in" >';
				html	 += '	<td style="vertical-align:bottom">'
				html	 += '		<p class="name"></p>'
				html	 += '		<strong class="error text-danger"></strong>'
				html	 += '	</td>'
				html	 += '	<td style="vertical-align:bottom" class="col-xs-6 col-sm-2">'
				html	 += '		<p class="size"></p>'
				html	 += '		<div aria-valuenow="0" aria-valuemax="100" aria-valuemin="0" role="progressbar" class="progress progress-striped active">'
				html	 += '			<div style="width:0%;" class="progress-bar progress-bar-success" id="progressbar"></div>'
				html	 += '		</div>'
				html	 += '	</td>'
				html	 +='	<td style="vertical-align:bottom">'
				html	 += '		<button class="btn btn-danger delete smaller-90" type="button">'
				html	 += '			<i class="icon-trash icon-2x icon-only btn-sm"></i>'
				html	 += '			<span>删除</span>'
				html	 += '		</button>'
				html	 += '	</td>'
				html	 += '</tr>';
		        
			var tpl2 = $(html);
			
			// Append the file name and file size
		    tpl2.find('p[class="name"]').text(data.files[0].name);
		    tpl2.find('p[class="size"]').text(formatFileSize(data.files[0].size));
		    
		     // Add the HTML to the UL element
		    data.context = tpl2.appendTo($tbody);
		    
		    // Initialize the knob plugin
		    //tpl2.find('div').knob();
		    
		    // Listen for clicks on the cancel icon    
		    tpl2.find('button').click(function(){
                jqXHR.abort();

                tpl2.fadeOut(function(){
                    tpl2.remove();
                });

            });

            // Automatically upload the file once it is added to the queue
            var jqXHR = data.submit();    
        },

        progress: function(e, data){
            // Calculate the completion percentage of the upload
            var progress = parseInt(data.loaded / data.total * 100, 10);
            data.context.find('#progressbar').css('width',progress+'%');
        },
		// Callback for failed (abort or error) uploads
        fail:function(e, data){
            // Something has gone wrong!
        	data.context.find('#progressbar').html('文件类型错误！');
            data.context.find('#progressbar').removeClass().addClass('progress-bar progress-bar-pink');
        },
        // Callback for successful uploads
        done:function(e, data){
        	//data.context.find('#progressbar').removeClass().addClass('progress-bar progress-bar-pink');
        }
    });

    // Helper function that formats the file sizes
    function formatFileSize(bytes) {
        if (typeof bytes !== 'number') {
            return '';
        }

        if (bytes >= 1000000000) {
            return (bytes / 1000000000).toFixed(2) + ' GB';
        }

        if (bytes >= 1000000) {
            return (bytes / 1000000).toFixed(2) + ' MB';
        }

        return (bytes / 1000).toFixed(2) + ' KB';
    }

});