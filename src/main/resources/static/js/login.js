$("#submit_b").click(function () {
    var username = $("#username").val(); 
    var password = $("#password").val();

    var form = new FormData();
        form.append("username", username);
        form.append("password", password);

        $.ajax({
            method: "POST",
            url: "/login",
            data: form,
            processData: false,
            contentType: false
        }).done(function (msg) {
            if (msg.status == 1) {
                location.replace("/");
            } else
                console.log(msg);
        }).fail(function (msg) {
            
        });
});