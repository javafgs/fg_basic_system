;jQuery( function() {
    // 停止
    $("body").on('click','[data-stopPropagation]',function (e) {
        e.stopPropagation();
    });
    
    // 滚动条
    if($('.lyear-scroll')[0]) {
        $('.lyear-scroll').each(function(){
            new PerfectScrollbar(this, {
	        	swipeEasing: false,
	        	suppressScrollX: true
	        });
        });
    }
  
    // 侧边栏
    $(document).on('click', '.lyear-aside-toggler', function() {
        $('.lyear-layout-sidebar').toggleClass('lyear-aside-open');
        $("body").toggleClass('lyear-layout-sidebar-close');
        
        if ($('.lyear-mask-modal').length == 0) {
            $('<div class="lyear-mask-modal"></div>').prependTo('body');
        } else {
            $( '.lyear-mask-modal' ).remove();
        }
    });
    
    // 遮罩层
    $(document).on('click', '.lyear-mask-modal', function() {
        $( this ).remove();
    	$('.lyear-layout-sidebar').toggleClass('lyear-aside-open');
        $('body').toggleClass('lyear-layout-sidebar-close');
    });
    
	// 侧边栏导航
    $(document).on('click', '.nav-item-has-subnav > a', function() {
		$subnavToggle = jQuery( this );
		$navHasSubnav = $subnavToggle.parent();
        $topHasSubNav = $subnavToggle.parents('.nav-item-has-subnav').last();
		$subnav       = $navHasSubnav.find('.nav-subnav').first();
        $viSubHeight  = $navHasSubnav.siblings().find('.nav-subnav:visible').outerHeight();
        $scrollBox    = $('.lyear-layout-sidebar-info');
		$navHasSubnav.siblings().find('.nav-subnav:visible').slideUp(500).parent().removeClass('open');
		$subnav.slideToggle( 300, function() {
			$navHasSubnav.toggleClass( 'open' );
			
			// 新增滚动条处理
			var scrollHeight  = 0;
			    pervTotal     = $topHasSubNav.prevAll().length,
			    boxHeight     = $scrollBox.outerHeight(),
		        innerHeight   = $('.sidebar-main').outerHeight(),
                thisScroll    = $scrollBox.scrollTop(),
                thisSubHeight = $(this).outerHeight(),
                footHeight    = 121;
			
			if (footHeight + innerHeight - boxHeight >= (pervTotal * 48)) {
			    scrollHeight = pervTotal * 48;
			}
            if ($subnavToggle.parents('.nav-item-has-subnav').length == 1) {
                $scrollBox.animate({scrollTop: scrollHeight}, 300);
            } else {
                // 子菜单操作
                if (typeof($viSubHeight) != 'undefined' && $viSubHeight != null) {
                    scrollHeight = thisScroll + thisSubHeight - $viSubHeight;
                    $scrollBox.animate({scrollTop: scrollHeight}, 300);
                } else {
                    if ((thisScroll + boxHeight - $scrollBox[0].scrollHeight) == 0) {
                        scrollHeight = thisScroll - thisSubHeight;
                        $scrollBox.animate({scrollTop: scrollHeight}, 300);
                    }
                }
            }
		});
	});
    
    // 读取cookie中的主题设置
	var the_logo_bg    = $.cookie('the_logo_bg'),
	    the_header_bg  = $.cookie('the_header_bg'),
	    the_sidebar_bg = $.cookie('the_sidebar_bg');
	
	if (the_logo_bg) $('body').attr('data-logobg', the_logo_bg);
	if (the_header_bg) $('body').attr('data-headerbg', the_header_bg);
	if (the_sidebar_bg) $('body').attr('data-sidebarbg', the_sidebar_bg);

    // 处理主题配色下拉选中
    $(".dropdown-skin :radio").each(function(){
        var $this = $(this),
            radioName = $this.attr('name');
        switch (radioName) {
            case 'logo_bg':
                $this.val() == the_logo_bg && $this.prop("checked", true);
                break;
            case 'header_bg':
                $this.val() == the_header_bg && $this.prop("checked", true);
                break;
            case 'sidebar_bg':
                $this.val() == the_sidebar_bg && $this.prop("checked", true);
        }
    });
	
	// 设置主题配色
	setTheme = function(input_name, data_name) {
	    $("input[name='"+input_name+"']").click(function(){
	        $('body').attr(data_name, $(this).val());
	        $.cookie('the_'+input_name, $(this).val());
	    });
	}
	setTheme('sidebar_bg', 'data-sidebarbg');
	setTheme('logo_bg', 'data-logobg');
	setTheme('header_bg', 'data-headerbg');
    
    // 选项卡
    $('#iframe-content').multitabs({
        iframe : true,
        refresh : 'no',  // iframe中页面是否刷新，'no'：'从不刷新'，'nav'：'点击菜单刷新'，'all'：'菜单和tab点击都刷新'
        nav: {
            backgroundColor: '#ffffff',
            maxTabs : 35, // 选项卡最大值
        },
        init : [{
            type : 'main',
            title : '首页',
            url : '/member/profile'
        }]
    });
    
    $(document).on('click', '.nav-item .multitabs', function() {
        $('.nav-item').removeClass('active');
        $('.nav-subnav li').removeClass('active');
        $(this).parent('li').addClass('active');
        $(this).parents('.nav-item-has-subnav').addClass('open').first().addClass('active');
    });
});