document.addEventListener('DOMContentLoaded', function () {
    // Panneaux cliquables
    const panels = document.querySelectorAll('.panel');
    panels.forEach(panel => {
        panel.addEventListener('click', function () {
            const content = this.querySelector('.panel-content');
            if (content) {
                content.style.display = content.style.display === 'block' ? 'none' : 'block';
            }
        });
    });

    // Charger la liste des cours
    document.getElementById('loadCoursesButton').onclick = function () {
        fetch('/projetjee/courseServlet')
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Erreur inconnue lors de la récupération des cours');
                    });
                }
                return response.json();
            })
            .then(data => {
                let courseListHtml = '<h3>Liste des Cours</h3><ul>';
                data.forEach(course => {
                    courseListHtml += '<li>' + course.name + '</li>';
                });
                courseListHtml += '</ul>';
                document.getElementById('allCoursesList').innerHTML = courseListHtml;
            })
            .catch(error => {
                showError(`Erreur : ${error.message}`);
                console.error('Erreur:', error);
            });
    };

    // Rechercher un cours par ID
    document.getElementById('getCourseForm').onsubmit = function (e) {
        e.preventDefault();
        var courseId = document.getElementById('courseId').value;
        fetch('/projetjee/courseServlet?courseId=' + courseId)
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.message || 'Erreur inconnue lors de la récupération des détails du cours');
                    });
                }
                return response.json();
            })
            .then(data => {
                let courseDetailsHtml = '<h3>Détails du Cours</h3>';
                if (data.name) {
                    courseDetailsHtml += '<p>Nom: ' + data.name + '</p>';
                    courseDetailsHtml += '<p>Description: ' + data.description + '</p>';
                } else {
                    showError('Erreur : Aucun cours trouvé.');
                }
                document.getElementById('courseDetails').innerHTML = courseDetailsHtml;
            })
            .catch(error => {
                showError(`Erreur : ${error.message}`);
                console.error('Erreur:', error);
            });
    };

    function showError(message) {
        const errorBar = document.getElementById('errorBar');
        errorBar.innerText = message;
        errorBar.style.display = 'block';
        setTimeout(() => errorBar.style.display = 'none', 5000);
    }
});
