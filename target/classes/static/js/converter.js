$("#submit_b").click(function () {
    var source_code = $("#source").val(); 
    var target_code = $("#target").val();
    var source_value = parseFloat($("#source_input").val());
    if (source_value == 0 || isNaN(source_value)) {
        $("#error").show();
        $("#error").text("Ошибка! Проверьте введенные параметры!");
    } else {
        var form = new FormData();
        form.append("source_code", source_code);
        form.append("target_code", target_code);
        form.append("source_value", source_value);

        $.ajax({
            method: "POST",
            url: "/converter",
            data: form,
            processData: false,
            contentType: false
        }).done(function (msg) {
            $("#error").hide();
            $("#source_input").val(msg.source);
            $("#target_input").val(msg.target);
        }).fail(function (msg) {
            $("#error").show();
            $("#error").text(msg.responseJSON.error+" "+msg.responseJSON.status);
        });
    }
});