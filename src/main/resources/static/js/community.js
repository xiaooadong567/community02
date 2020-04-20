function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (data) {
            if (data.code == 200) {
                $("#comment_section").hide();
            } else {
                if (data.code == 2003) {
                    var isAccepted = confirm(data.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=413362246bb7c57dbb5b&redirect_uri=http://localhost:8886/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                } else {
                    alert(data.message);
                }
            }
        },
        dataType: "json"
    });

    console.log(questionId);
    console.log(content);


}