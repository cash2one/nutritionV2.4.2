var tool={
	getWindowHeight:function(){
		return $(window).height();
	},
	getWindowWidth:function(){
		return $(window).width();
	},
	time:function(){
		var t=new Date();
		var time = t.getFullYear()+"-"+this.twoString((t.getMonth()+1))+"-"+this.twoString(t.getDate())+" "+this.twoString(t.getHours())+":"+this.twoString(t.getMinutes())+":"+this.twoString(t.getSeconds());
		return time;
	},
	twoString:function(stringN){
		var tt="";
		if(isNaN(parseInt(stringN))){
			return;
		};
		if(parseInt(stringN)<10){
			tt = "0"+ stringN;
		}else{
			tt=stringN;
		};
		return tt;
	},
	trimStr:function(str){
		 if(!str) return '';
         str=str.toString();
         return str.replace(/(^\s*)|(\s*$)/g,"");
	},
	trimTtszf:function(str){
		var re=/&|%|\+/g;
		var test=str.replace(re,function(aStr){
			  		for(var i=0;i<aStr.length;i++){
			  			switch(aStr[i]){
			  				case "%" : return aStr[i]="%25"; break;
			  				case "&" : return aStr[i]="%26"; break;
			  				case "+" : return aStr[i]="%2B"; break;
			  				default:  
			  					return "转换错误";
			    }
			}   

			})
		return test;
	},
	trimQb:function(str){
	    var st=str.toString();
	    st = st.replace(/%/g, "%25");
	    st = st.replace(/&/g, "%26");
	    st = st.replace(/\+/g, "%2B");
	    st = st.replace(/\\/g,"%5C%5C");
	    st = st.replace(/"/g, "\\%22");
	    var text = st;
	    return text;
	},
	trimT1:function(str){
		var re=/%/g;
		var text = str.replace(re,"%25");
		return text;
	},
	trimT2:function(str){
		var re=/&/g;
		var text = str.replace(re,"%26");
		return text;
	},
	trimT3:function(str){
		var re=/\+/g;
		var text = str.replace(re,"%2B");
		return text;
	}
}
