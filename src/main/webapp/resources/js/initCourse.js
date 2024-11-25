window.onload = function () {
    const courseSelect = document.getElementById('courseId');
    const loadingOption = document.createElement('option');
    loadingOption.value = "";
    loadingOption.textContent = "Chargement...";
    courseSelect.appendChild(loadingOption);

    console.log("Démarrage du chargement des cours depuis le serveur.");

    fetch(`${contextPath}/enrollment?action=getCoursesAndTeachers`)
        .then(response => {
            console.log("Réponse reçue avec le code : " + response.status);
            if (!response.ok) {
                throw new Error('Échec de la récupération des cours.');
            }
            return response.json();
        })
        .then(data => {
            console.log("Données reçues :", data);
            courseSelect.innerHTML = "";
            if (data.length > 0) {
                data.forEach(item => {
                    const option = document.createElement('option');
                    option.value = item.id;
                    option.textContent = `${item.name} (Prof: ${item.teacherId})`;
                    courseSelect.appendChild(option);
                });
            } else {
                const noCoursesOption = document.createElement('option');
                noCoursesOption.value = "";
                noCoursesOption.textContent = "Aucun cours disponible";
                courseSelect.appendChild(noCoursesOption);
            }
        })
        .catch(error => {
            console.error('Erreur lors du chargement des cours :', error);
            courseSelect.innerHTML = "";
            const errorOption = document.createElement('option');
            errorOption.value = "";
            errorOption.textContent = "Erreur de chargement";
            courseSelect.appendChild(errorOption);
        });
};
