function filterTable() {
    let filter = document.getElementById("filter").value;

    console.log("Rôle sélectionné : ", filter);

    fetch(`../adminServlet?action=modify&role=${encodeURIComponent(filter)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erreur lors de la récupération des données");
            }
            return response.json();
        })
        .then(data => {
            // Réinitialiser le tableau
            var tableBody = document.querySelector("#userTable tbody");
            tableBody.innerHTML = "";

            // Ajouter les nouvelles lignes
            data.forEach(user => {
                var row = document.createElement("tr");
                row.setAttribute("data-role", filter);

                var lastNameCell = document.createElement("td");
                lastNameCell.textContent = user.lastName;

                var firstNameCell = document.createElement("td");
                firstNameCell.textContent = user.firstName;
                console.log("test")

                var roleCell = document.createElement("td");
                roleCell.textContent = filter === "student" ? "Élève" : "Professeur";

                console.log("Filter : ", encodeURIComponent(filter));
                console.log("user.id : ", encodeURIComponent(user.id));
                console.log("URL générée : ", `../adminServlet?action=modify&role=${encodeURIComponent(filter)}&id=${encodeURIComponent(user.id)}`);

                var actionsCell = document.createElement("td");
                actionsCell.innerHTML = `
                    <form action="../adminServlet" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="modify">
                        <input type="hidden" name="role" value="${encodeURIComponent(filter)}">
                        <input type="hidden" name="id" value="${encodeURIComponent(user.id)}">
                        <button type="submit" class="button">Modifier</button>
                    </form>
                    <form action="../adminServlet" method="POST" style="display:inline;" onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="role" value="${encodeURIComponent(filter)}">
                        <input type="hidden" name="id" value="${encodeURIComponent(user.id)}">
                        <button type="submit" class="button delete">Supprimer</button>
                    </form>
                `;

                row.appendChild(lastNameCell);
                row.appendChild(firstNameCell);
                row.appendChild(roleCell);
                row.appendChild(actionsCell);

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Erreur:", error);
        });
}
