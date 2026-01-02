const user = document.getElementById("user");

function authenticated() {
    document.getElementsByName("authenticated").forEach(function(element) {
        element.style.visibility = 'visible';
    });
    document.getElementsByName("unauthenticated").forEach(function(element) {
        element.style.visibility = 'hidden';
    });    
}

function unauthenticated() {
    document.getElementsByName("authenticated").forEach(function(element) {
        element.style.visibility = 'hidden';
    });    
    document.getElementsByName("unauthenticated").forEach(function(element) {
        element.style.visibility = 'visible';
    });
}

async function getUserData() {
    try {
        const response = await fetch('/user');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const result =  await response.json();
        user.append(JSON.stringify(result));
        authenticated();
        console.log('Fetched user data:', result);
        return result;
    } catch (error) {
        unauthenticated();
        console.error('There has been a problem with your fetch operation:', error);
    }
}

/*
document.addEventListener("DOMContentLoaded", (event) => {
    console.log("DOM fully loaded and parsed");
    this.getUserData().then((data) => {
        console.log('Authenticated', data);
    }).catch((error) => {
        console.log('Unauthenticated', error);
    });
});
*/

//$.ajaxSetup({
//  beforeSend : function(xhr, settings) {
//    if (settings.type == 'POST' || settings.type == 'PUT'
//        || settings.type == 'DELETE') {
//      if (!(/^http:.*/.test(settings.url) || /^https:.*/
//        .test(settings.url))) {
//        xhr.setRequestHeader("X-XSRF-TOKEN",
//          Cookies.get('XSRF-TOKEN'));
//      }
//    }
//  }
//});

/*    
var logout = function() {
    $.post("/logout", function() {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
    });
    return true;
}
*/
