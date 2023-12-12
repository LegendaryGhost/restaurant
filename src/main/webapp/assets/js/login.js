/****************  Functions ****************/
const send_login_form = event => {
    event.preventDefault();

    const formData = new FormData(event.target);

    sendXHRRequest("login-servlet", "POST", formData).then(
        response => {
            try {
                response = JSON.parse(response);
                if (response.connected) {
                    window.location.href = 'pages/home.html';
                } else {
                    alert(response.message);
                }
            } catch (e) {
                console.error(e.toString());
                console.log(response);
            }
        }
    );
};

// HTML Elements
let login_form;

window.addEventListener("load", () => {
    login_form = document.getElementById("login-form");

    login_form.addEventListener("submit", send_login_form);
});