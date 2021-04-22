$("#signup").click(function() {
    $("#first").fadeOut("fast", function() {
        $("#second").fadeIn("fast");
    });
});

$("#signin").click(function() {
    $("#second").fadeOut("fast", function() {
        $("#first").fadeIn("fast");
    });
});



$(function() {
    $("form[name='login']").validate({
        rules: {

            username: {
                required: true,
            },
            password: {
                required: true,

            }
        },
        messages: {
            username: "Please enter a username",

            password: {
                required: "Please enter password",

            }

        },
        submitHandler: function(form) {
            form.submit();
        }
    });
});



$(function() {

    $("form[name='registration']").validate({
        rules: {
            firstname:{
                required:true,
                minlength: 2
            },
            lastname: {
                required:true,
                minlength: 2
            } ,
            username: {
                required: true,
                username: true
            },
            password: {
                required: true,
                minlength: 8
            }
        },

        messages: {
            firstname: "Please enter your firstname",
            lastname: "Please enter your lastname",
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 8 characters long"
            },
            username: "Please enter a username"
        },

        submitHandler: function(form) {
            form.submit();
        }
    });
});

