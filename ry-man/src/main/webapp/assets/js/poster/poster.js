/*
 * Turn.js responsive book
 */

/*globals window, document, $*/

(function () {
    'use strict';

    var module = {
        ratio: 0.69, //1.38,
        init: function (id) {
            var me = this;

            // if older browser then don't run javascript
            if (document.addEventListener) {
                this.el = document.getElementById(id);
                this.resize();
                this.plugins();

                // on window resize, update the plugin size
                window.addEventListener('resize', function (e) {
                    var size = me.resize();
                    $(me.el).turn('size', size.width, size.height);
                });
            }
        },
        resize: function () {
            // reset the width and height to the css defaults
            this.el.style.width = '';
            this.el.style.height = '';

            var width = this.el.clientWidth,
                height = Math.round(width / this.ratio),
                padded = Math.round(document.body.clientHeight * 0.9);

            // if the height is too big for the window, constrain it
            if (height > padded) {
                height = padded;
                width = Math.round(height * this.ratio);
            }

            // set the width and height matching the aspect ratio
            this.el.style.width = width + 'px';
            this.el.style.height = height + 'px';
			
	        $("img").each(function(){
            	$(this).attr('style','max-width:'+width+'px;height:auto;');
            });
            return {
                width: width,
                height: height
            };
        },
        plugins: function () {
            // run the plugin
            $(this.el).turn({
                display: "single",
                gradients: true,
                acceleration: true
            });
            // hide the body overflow
            document.body.className = 'hide-overflow';
        }
    };

    //module.init('book');
    /*
    $('#book').turn({
        display: "single",
        gradients: true,
        acceleration: true,
        turnCorners: "br"
    });
    */
    var wmswiper = $('.swiper-container').swiper();
    $('.arrow-left').click(function(e){
        e.preventDefault();
        wmswiper.swipePrev();
    });
    $('.arrow-right').click(function(e){
        e.preventDefault();
        wmswiper.swipeNext();
    });
}());
