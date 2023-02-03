console.log("login.js in action");

document.addEventListener("click", function (event) {
  // Checking if the button was clicked
  if (event.target.matches("#Submit")){
  console.log("Event matched");

  /*
  Here we send a request to the Joke API
  Then process the response into JSON
  Then pass the data to our renderJoke function.
  */
  const response = fetch("http://localhost:8080/blog/login", {
  method: 'POST',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
  body: `{
     "email": document.getElementById("email"),
     "password": document.getElementById("password")
    }`,
  });

  response.json().then(data => {
    verifyAuthentication(data);
  });
  }
  });

function verifyAuthentication(data) {
  const email = document.getElementById("email");
  const password = document.getElementById("password");
  const error = document.getElementById("errorMessage");
  const success = document.getElementById("successMessage");

  if(data.success == "true"){
    success.innerHTML = "Login Successful";
  }
}

function renderError() {
  const error = document.getElementById("error");
  error.innerHTML = "Whoops, something went wrong. Please try again later!";
}