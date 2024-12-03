document.addEventListener("DOMContentLoaded", function () {
    const panels = document.querySelectorAll(".panel");

    panels.forEach(panel => {
        const header = panel.querySelector(".panel-header");
        const content = panel.querySelector(".panel-content");

        // Ferme tous les panneaux sauf celui cliqué
        header.addEventListener("click", function () {
            const isVisible = content.style.display === "block";
            closeAllPanels();
            if (!isVisible) {
                content.style.display = "block";
            }
        });
    });

    function closeAllPanels() {
        panels.forEach(panel => {
            panel.querySelector(".panel-content").style.display = "none";
        });
    }
});

document.getElementById('loadCoursesButton').onclick = function () {
    fetch('/projetjee/courseServlet')
        .then(response => response.json())
        .then(data => {
            let courseListHtml = '<ul>';
            data.forEach(course => {
                courseListHtml += '<li>' + course.name + '</li>';
            });
            courseListHtml += '</ul>';
            document.getElementById('allCoursesList').innerHTML = courseListHtml;
        })
        .catch(error => {
            showError('Erreur : ' + error.message);
        });
};

document.getElementById('getCourseForm').onsubmit = function (e) {
    e.preventDefault();
    var courseId = document.getElementById('courseId').value;
    fetch('/projetjee/courseServlet?courseId=' + courseId)
        .then(response => response.json())
        .then(data => {
            let courseDetailsHtml = '<p>Nom: ' + data.name + '</p><p>Description: ' + data.description + '</p>';
            document.getElementById('courseDetails').innerHTML = courseDetailsHtml;
        })
        .catch(error => {
            showError('Erreur : ' + error.message);
        });
};

function showError(message) {
    const errorBar = document.getElementById('errorBar');
    errorBar.textContent = message;
    errorBar.style.display = 'block';
}

function goBack() {
    window.history.back();
}
