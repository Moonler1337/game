fetch("http://localhost:8080/sum", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        first: 10,
        second: 20
    })
})
    .then((response) => {
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }
        return response.json();
    })
    .then((data) => {
        console.log("sum =", data.result);
    })
    .catch((error) => {
        console.error("response failed:", error);
    });
