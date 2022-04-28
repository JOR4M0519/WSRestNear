const form = document.querySelector("form");

form.onsubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    try {
        let response = await fetch(`./api/users/${localStorage.getItem("username")}/fcoins?role=${localStorage.getItem("role")}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams(formData),

        });
        let result = await response.json();
        console.log(result)
        window.location.href = "./index.html";

    } catch (err) {
        console.log(err);

    }
}