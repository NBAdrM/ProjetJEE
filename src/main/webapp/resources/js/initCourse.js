window.onload = function () {
    fetch(`${contextPath}/enrollment?action=getCoursesAndTeachers`)
        .then(response => response.json())
        .then(data => {
            const courseSelect = document.getElementById('courseId');
            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.courseId;
                option.textContent = `${item.courseName} (Prof: ${item.teacherName})`;
                courseSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erreur lors du chargement des cours :', error);
        });
};
