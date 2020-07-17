
layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider','form'], function() {
    var laydate = layui.laydate //日期
        , laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , carousel = layui.carousel //轮播
        , upload = layui.upload //上传
        , element = layui.element //元素操作
        , slider = layui.slider //滑块
    //jquery $
    var $ = layui.jquery;
    var ajax=layui.ajax;

    //执行一个 table 实例
    var  tableIns=table.render({
        elem: '#demo'
        , height: 420
        , url: "/cartest_war_exploded/car/show" //数据接口
        ,parseData: function(res) { //res 即为原始返回的数据
            console.log(res);
            return {
                "code": res.status, //解析接口状态 要求是"0"
                "msg": res.message, //解析提示文本
                "count": res.total, //解析数据长度
                "data": res.item //解析数据列表
            };
        }
        , title: '汽车表'
        , page: true //开启分页
        , toolbar: '#bar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , totalRow: true //开启合计行
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID', width: 150, sort: true, align: "center"}
            , {field: 'type', title: '品牌', align: "center"}
            ,{field: 'imgPath', title: '图片', align:'center',
                templet:function (e) {
                    return "<img src='../"+e.img+"'style='height:150px;width:150px;'/>"
                }
            }
            , {field: 'color', title: '颜色', align: "center"}
            , {field: 'seat', title: '座位数', align: 'center'}
            , {field: 'rent', title: '日租金', align: 'center'}
            , {field: 'gas', title: '百公里耗油', align: 'center'}
            ,{field: 'outdate', title: '出厂日期·',  sort: true, align:'center',edit:"text",
                templet:function (e) {
                    return showTime(e.adddate);
                }
            }
            ,{field: 'adddate', title: '生产日期',  sort: true, align:'center',edit:"text",
                templet:function (e) {
                    return showTime(e.outdate);
                }
            }
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo',width: 100}//操作
        ]]
    });

    //监听单元格编辑
    // table.on('edit(newsTable)', function(obj){ //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
    //     console.log(obj.value); //得到修改后的值
    //     console.log(obj.field); //当前编辑的字段名
    //     console.log(obj.data); //所在行的所有相关数据
    //
    //     $.ajax({
    //         type:"post",
    //         url:"/virusData/news/update",
    //         data:{
    //             "id":obj.data.id,
    //             "field":obj.field,
    //             "value":obj.value
    //         },
    //         dataType:"json",
    //         success:function (res) {
    //             //弹出消息
    //             layer.msg(res.message);
    //         }
    //
    //     })
    // });
    //
    //监听头工具栏事件
    table.on('toolbar(newsTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        // 用来群删除的数组
        var ids=[];
        for(var i=0;i<data.length;i++) {
            ids[i] = data[i].id;
        }
        switch(obj.event){
            case 'add':
                layer.msg('添加');
                //打开视频添加窗口 弹出一个层
                layer.open({
                    type:2,
                    title:"添加车辆",//iframe 框架窗口
                    shade:0.8, //窗口外部阴影配置
                    shadeClose:true,
                    area:['60%','90%'],
                    content:"../templates/add_news.html"
                })
                break;
            // case 'update':
            //     if(data.length === 0){
            //         layer.msg('请选择一行');
            //     } else if(data.length > 1){
            //         layer.msg('只能同时编辑一个');
            //     } else {
            //         layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
            //     }
            //     break;
            case 'del':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                    $.ajax({
                        type:"post",
                        url:"../car/delete",
                        data:{
                            "ids":ids
                        },
                        dataType:"json",
                        traditional:true,
                        success:function (res) {
                            var message=res.message;
                            var code = res.status;
                            if(code=="200"){
                                layer.alert(message,{icon:1},function (index) {
                                    layer.close(index);
                                    tableIns.reload();
                                })
                            }
                        }
                    })
                }
                break;
        };
    });
    table.on('tool(newsTable)', function(obj) {
        var data = obj.data;
        if (obj.event === 'detail') {

            layer.open({
                type:2,
                title:"车辆信息",//iframe 框架窗口
                shade:0.8, //窗口外部阴影配置
                shadeClose:true,
                area:['40%','90%'],
                content:"../templates/carInfo.html",
                success: function (layero, index) {
                    // 获取子页面的iframe
                    var iframe = window['layui-layer-iframe' + index];
                    // 向子页面的全局函数child传参
                    iframe.child(data)

                }
            })
        }
        else if(obj.event === 'edit'){
            layer.open({
                type:2,
                title:"编辑",//iframe 框架窗口
                shade:0.8, //窗口外部阴影配置
                shadeClose:true,
                area:['40%','90%'],
                content:"../templates/up_car.html",
                success: function (layero, index) {
                    // 获取子页面的iframe
                    var iframe = window['layui-layer-iframe' + index];
                    // 向子页面的全局函数child传参
                    iframe.child(data)

                }
            })
        }
    });
    //
    //
    //
    //
    $("#submit").on("click",function () {
        //获取表单的原生对象 指的是javascript 节点对象
        var fd1 =document.getElementById("form");
       // var fd2 = $("#form")[0];

        var formdata=new FormData(fd1);



       var imgs = document.getElementById("img").value;
       formdata.append("img",imgs);


        // ajax 与后台传值
        $.ajax({
            url:"../car/add",
            type:"post",
            data:formdata,
            dataType:'json',
            async: false,
            cache: false,
            processData: false,
            contentType: false,
            success:function(res){
                parent.layer.alert(res.message,{icon:1},function(){
                    window.parent.location.reload();
                });
            },
            error:function(res){

            }
        });
    });
    //
    // //点击搜索按钮时 会进行表格的重载
    $("#re").on("click",function () {
        //表格重载函数 搜索

        table.reload('demo', {
            url:"../car/query" ,//会把page和limit （在页面的分页参数里）加到路径后面
            where: { //设定异步数据接口的额外参数，任意设
                type: $("#titleLike").val()

            }
            , parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": 0, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count":100, //解析数据长度
                    "data": res.item //解析数据列表
                }
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        }); //只重载数据

    });

    // 文件上传  的实例
    var uploadInst=  upload.render({
        elem: '#chooseimg'
        ,url: '../car/up'
        ,auto: false //选择文件后不自动上传
        ,bindAction: '#sure' //指向一个按钮触发上传
        ,size:5000000
        ,accept:'img'
        ,done: function(res){
            // layer.closeAll('loading'); //关闭loading
            console.log(res.message)
            // 文件上传之后  input 框显示 文件名
            $("#img").val(res.message);
            layer.msg("上传成功");
        }
        ,error: function(index){
            layer.closeAll('loading'); //关闭loading
        }
    });

    // // 对上传文件进行重载
    // upload.render({
    //     elem: '#shangchuan'
    //     , url: '/nanda/videos/sc'
    //     , auto: false //选择文件后不自动上传
    //     , bindAction: '#myupload' //指向一个按钮触发上传
    //     , accept: 'file'
    //     , size: 5000000
    //     , done: function (res) {
    //         if (res.message = "success") {
    //             layer.msg("上传成功");
    //         }
    //
    //     }
    //     , error: function (index) {
    //         layer.closeAll('loading'); //关闭loading
    //     }
    // });
});