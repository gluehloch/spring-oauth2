let user;

function authenticated() {
    const authenticated = document.getElementsByClassName("authenticated");
    Array.from(authenticated).forEach(function(element) {
        element.style.visibility = 'visible';
    });
    const unauthenticated = document.getElementsByClassName("unauthenticated");
    Array.from(unauthenticated).forEach(function(element) {
        element.style.visibility = 'hidden';
    });
}

function unauthenticated() {
    const authenticated = document.getElementsByClassName("authenticated");
    Array.from(authenticated).forEach(function(element) {
        element.style.visibility = 'hidden';
    });
    const unauthenticated = document.getElementsByClassName("unauthenticated");
    Array.from(unauthenticated).forEach(function(element) {
        element.style.visibility = 'visible';
    });
}

function getUserData() {
    const token = getTokenHeaderParam();
    return fetch('/api/user', { method: 'GET', headers: token }).then(response => {
        console.log('/api/user - Response:', response);
        if (response.status === 401) {
            return Promise.reject('Unauthorized');
        }     

        response.json().then((data) => {
            user.textContent = JSON.stringify(data);
            console.log('Fetched user data:', data);
            return data;
        });
    }).catch(error => {
        return Promise.reject(error);
    });
}

document.addEventListener("DOMContentLoaded", (event) => {
    user = document.getElementById("user");
    console.log("DOM fully loaded and parsed");
    this.getUserData().then((data) => {
        console.log('Authenticated', data);
        authenticated();
    }).catch((error) => {
        console.log('Unauthenticated', error);
        unauthenticated();
    });
});

function getCookieByName(name) {
    const cookieString = document.cookie;
    const cookies = cookieString.split(';');
    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (cookie.startsWith(name + '=')) {
            return cookie.substring(name.length + 1);
        }
    }
    return null;
}

function getTokenHeaderParam() {
    const xsrfToken = getCookieByName('XSRF-TOKEN');
    return { 'X-XSRF-TOKEN': xsrfToken };
}

function logout() {
    const xsrf = getCookieByName('XSRF-TOKEN');
    console.log('XSRF-TOKEN cookie:', xsrf);
	
	const headers = new Headers();
	headers.append('X-XSRF-TOKEN', xsrf);
	headers.append('Content-Type', 'application/x-www-form-urlencoded');

    const token = getTokenHeaderParam();
    fetch('/logout', {
        method: 'POST',
		credentials: 'same-origin',
        headers: headers,
		body: new URLSearchParams({"_csrf": xsrf})
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        unauthenticated();
    }).catch((error) =>{
        console.error('There has been a problem with your fetch operation:', error);
    });
}
