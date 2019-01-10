function logout(){
    swal("确认要退出登录吗?", {
        icon: "warning",
        buttons: ["先不急","是的,退出"],
        dangerMode: true
    }).then(function (value) {
        if (value) {
            document.location="/logout";
        }
    });
}