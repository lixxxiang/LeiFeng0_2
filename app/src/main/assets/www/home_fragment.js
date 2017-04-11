function load_data(){
    var url = "http://10.10.160.69:8888/";
     $.get(url + new Date().getTime(), function(data){
         $("#feeds").html(data);
         var dataObj=eval('('+data+')');
         $("#username").text(dataObj.username);
         document.getElementById('avatar').src = dataObj.avatar;
         document.getElementById('member_icon').src = dataObj.member_icon;
     });
}