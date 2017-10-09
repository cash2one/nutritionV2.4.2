/**
 * Created by Administrator on 2017/5/9 0009.
 */
/*--------------------   表单验证   ---------------------- --*/
$(function () {
    var validator=$("#inf_form").validate({
        errorPlacement: function(error, element) {
            // Append error within linked label
            $( element )
                .closest( "form" )
                .find( "label[for='" + element.attr( "id" ) + "']" )
                .siblings('.errorP')
                .append( error );
        },
        errorElement: "span",
        debug:true,
        rules: {
            phone:{
                required:true,
                digits:true
            },
            yourname:{
                maxlength:30,
                required:true,
                mameV:true
            },
            height:{rangelength:[1,3]},
            weight:{rangelength:[1,3]},
            weight2:{rangelength:[1,3]},
            birthday:{dateISO:true},
            modate:{dateISO:true},
            yudate:{dateISO:true},
            mendate:{dateISO:true},
            kaNum:{
                required:true,
                maxlength:30,
                numEn:true
            },
            muscle:{num1_100:true},
            fat:{num1_100:true},
            water:{num1_100:true},
            metabolism:{num1_100:true}
        },
        messages: {
            yourname:{maxlength:"超出30个字符"},
            weight:{rangelength:"输入1-3位数字"},
            weight2:{rangelength:"输入1-3位数字"},
            height:{rangelength:"输入1-3位数字"},
            kaNum:{
                required:"输入就诊卡号",
                maxlength:"最多可输入30个字符"
            }
        }
    });
    jQuery.validator.addMethod('num1_100', function(value, element) {
        var length = value.length;
        var mobile =/^(?:\d?\d|100)$/;
        return this.optional(element) || (length > 0 && mobile.test(value));
    },'输入1-100的数字');
    jQuery.validator.addMethod('numEn', function(value, element) {
        var length = value.length;
        var mobile =/(?=^.*?\d)(?=^.*?[a-zA-Z])^[0-9a-zA-Z]{4,23}$/;
        return this.optional(element) || (length > 0 && mobile.test(value));
    },'只能输入数字和英文字母');
    jQuery.validator.addMethod('mameV', function(value, element) {
        var length = value.length;
        var mobile =/^([\u4e00-\u9fa5]+|[a-zA-Z0-9]+)$/;
        return this.optional(element) || (length > 0 && mobile.test(value));
    },'不能输入特殊字符');
});