const form = document.getElementById("predict-form");
const errorBox = document.getElementById("error");
const resultBox = document.getElementById("result");
const priceEl = document.getElementById("price");
const historyBody = document.getElementById("history");
const emptyMsg = document.getElementById("empty");

const inr = new Intl.NumberFormat("en-IN", {
    style: "currency", currency: "INR", maximumFractionDigits: 0
});

function showError(msg) {
    errorBox.textContent = msg;
    errorBox.hidden = false;
    resultBox.hidden = true;
}

function renderHistory(items) {
    historyBody.innerHTML = "";
    emptyMsg.hidden = items.length > 0;
    for (const p of items) {
        const tr = document.createElement("tr");
        const cells = [
            new Date(p.createdAt).toLocaleString(),
            p.location,
            p.builtUpArea + " sq.ft",
            p.bedrooms,
            p.bathrooms,
            p.parking ? "Yes" : "No",
            inr.format(p.estimatedPrice)
        ];
        for (const c of cells) {
            const td = document.createElement("td");
            td.textContent = c;   // textContent avoids HTML injection
            tr.appendChild(td);
        }
        historyBody.appendChild(tr);
    }
}

async function loadHistory() {
    try {
        const res = await fetch("/api/predictions");
        renderHistory(await res.json());
    } catch (e) {
        showError("Could not load history.");
    }
}

form.addEventListener("submit", async (e) => {
    e.preventDefault();
    errorBox.hidden = true;

    const body = {
        location: document.getElementById("location").value,
        builtUpArea: parseFloat(document.getElementById("area").value),
        bedrooms: parseInt(document.getElementById("bedrooms").value, 10),
        bathrooms: parseInt(document.getElementById("bathrooms").value, 10),
        parking: document.getElementById("parking").checked
    };

    try {
        const res = await fetch("/api/predict", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });
        const data = await res.json();
        if (!res.ok) {
            showError(Object.values(data).join(" • ") || "Invalid input.");
            return;
        }
        priceEl.textContent = inr.format(data.estimatedPrice);
        resultBox.hidden = false;
        loadHistory();
    } catch (err) {
        showError("Could not reach the server.");
    }
});

document.getElementById("clear").addEventListener("click", async () => {
    await fetch("/api/predictions", { method: "DELETE" });
    loadHistory();
});

loadHistory();
