/**
 * Created by My on 2020/2/22.
 */
//时间转换函数
function showTime(tempDate){
    //  把距离标准时间的   到现在的毫秒数  转化成date 对象


    var d=new Date(tempDate);
    var  year = d.getFullYear();
    var month = d.getMonth();
    month++;
    var  day = d.getDate();
    // var houre = d.getHours();
    // var   minutes = d.getMinutes();
    // var secondes= d.getSeconds();
    //  把小于 月份变成双数
    month = month<10?"0"+month:month;
    day = day<10?"0"+day:day;
    // houre = houre<10?"0"+houre:houre;
    // minutes =  minutes<10?"0"+ minutes: minutes;
    // secondes = secondes<10?"0"+secondes:secondes;
    var time = year+"-"+month+"-"+day;
        // +" "+houre+":"+minutes+":"+secondes;
    return time;

}