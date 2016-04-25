(function($) {
    var editor = jQuery("#poster-detail")
                    .xheditor(
                                    {
                                                    tools: 'Separator,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,'
                                                                    + 'Removeformat,Separator,Align,List,Outdent,Indent,Link,Unlink,Img,Separator,Table,Source,Fullscreen',
                                                    inlineScript: false,// 清理js
                                                    internalScript: false,
                                                    linkTag: false,
                                                    cleanPaste: 2,
                                                    inlineClass: false,// 去除class样式、style中的定位、url
                                                    internalImage: true,
				                                    upBtnText : '选择',
				                                    html5Upload : false,
				                                    upLinkUrl : '',
				                                    upLinkExt : '',
				                                    upImgUrl : '/publishupload/upload.do',
				                                    upImgExt : 'jpg,jpeg,png'
                                    });
    jQuery(editor.doc).bind('keyup', function(e) {
        var readLength = jQuery('#poster-detail').val().length;
        jQuery('#poster-detail-max-length').text(readLength);
        if (readLength > 10000) {
            $("table.xheLayout").css("border-color", "red").addClass("error");
            $("#poster-detail-empty").html('<span class="help-block form-error">商品说明源码最长为10000字符</span>');
        } else if ($("#poster-detail-empty").text()) {
            $("table.xheLayout").css("border-color", "").removeClass("error");
            $("#poster-detail-emptys").empty();
        }
    });
})(jQuery);