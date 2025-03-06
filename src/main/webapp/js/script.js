// Ouvrir la pop-up pour créer un objet
function openCreateObjectModal() {
    document.getElementById("createObjectModal").style.display = "block";
}

// Fermer la pop-up de création d'objet
function closeCreateObjectModal() {
    document.getElementById("createObjectModal").style.display = "none";
}

// Ouvrir la pop-up pour demander un échange
function openExchangeModal(objectName) {
    document.getElementById("selectedObject").textContent = objectName;
    document.getElementById("exchangeModal").style.display = "block";
}

// Fermer la pop-up de demande d'échange
function closeExchangeModal() {
    document.getElementById("exchangeModal").style.display = "none";
}

// Fermer la pop-up si on clique à l'extérieur
window.onclick = function(event) {
    let createObjectModal = document.getElementById("createObjectModal");
    let exchangeModal = document.getElementById("exchangeModal");

    if (event.target === createObjectModal) {
        createObjectModal.style.display = "none";
    }

    if (event.target === exchangeModal) {
        exchangeModal.style.display = "none";
    }
};
